/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.rest.action.admin.indices;

import org.elasticsearch.action.admin.indices.shrink.ResizeRequest;
import org.elasticsearch.action.admin.indices.shrink.ResizeType;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestToXContentListener;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.rest.RestRequest.Method.POST;
import static org.elasticsearch.rest.RestRequest.Method.PUT;

public abstract class RestResizeHandler extends BaseRestHandler {

    RestResizeHandler() {
    }

    @Override
    public abstract String getName();

    abstract ResizeType getResizeType();

    @Override
    public final RestChannelConsumer prepareRequest(final RestRequest request, final NodeClient client) throws IOException {
        final ResizeRequest resizeRequest = new ResizeRequest(request.param("target"), request.param("index"));
        resizeRequest.setResizeType(getResizeType());
        request.applyContentParser(resizeRequest::fromXContent);
        resizeRequest.timeout(request.paramAsTime("timeout", resizeRequest.timeout()));
        resizeRequest.masterNodeTimeout(request.paramAsTime("master_timeout", resizeRequest.masterNodeTimeout()));
        resizeRequest.setWaitForActiveShards(ActiveShardCount.parseString(request.param("wait_for_active_shards")));
        return channel -> client.admin().indices().resizeIndex(resizeRequest, new RestToXContentListener<>(channel));
    }

    public static class RestShrinkIndexAction extends RestResizeHandler {

        @Override
        public List<Route> routes() {
            return List.of(
                new Route(POST, "/{index}/_shrink/{target}"),
                new Route(PUT, "/{index}/_shrink/{target}"));
        }

        @Override
        public String getName() {
            return "shrink_index_action";
        }

        @Override
        protected ResizeType getResizeType() {
            return ResizeType.SHRINK;
        }

    }

    public static class RestSplitIndexAction extends RestResizeHandler {

        @Override
        public List<Route> routes() {
            return List.of(
                new Route(POST, "/{index}/_split/{target}"),
                new Route(PUT, "/{index}/_split/{target}"));
        }

        @Override
        public String getName() {
            return "split_index_action";
        }

        @Override
        protected ResizeType getResizeType() {
            return ResizeType.SPLIT;
        }

    }

    public static class RestCloneIndexAction extends RestResizeHandler {

        @Override
        public List<Route> routes() {
            return List.of(
                new Route(POST, "/{index}/_clone/{target}"),
                new Route(PUT, "/{index}/_clone/{target}"));
        }

        @Override
        public String getName() {
            return "clone_index_action";
        }

        @Override
        protected ResizeType getResizeType() {
            return ResizeType.CLONE;
        }

    }

}
