/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.admin.indices.cache.clear;

import org.elasticsearch.action.support.DefaultShardOperationFailedException;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractBroadcastResponseTestCase;

import java.util.List;

public class ClearIndicesCacheResponseTests extends AbstractBroadcastResponseTestCase<ClearIndicesCacheResponse> {

    @Override
    protected ClearIndicesCacheResponse createTestInstance(int totalShards, int successfulShards, int failedShards,
                                                           List<DefaultShardOperationFailedException> failures) {
        return new ClearIndicesCacheResponse(totalShards, successfulShards, failedShards, failures);
    }

    @Override
    protected ClearIndicesCacheResponse doParseInstance(XContentParser parser) {
        return ClearIndicesCacheResponse.fromXContent(parser);
    }
}
