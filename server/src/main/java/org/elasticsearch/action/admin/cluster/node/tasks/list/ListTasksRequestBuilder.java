/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.node.tasks.list;

import org.elasticsearch.action.support.tasks.TasksRequestBuilder;
import org.elasticsearch.client.ElasticsearchClient;

/**
 * Builder for the request to retrieve the list of tasks running on the specified nodes
 */
public class ListTasksRequestBuilder extends TasksRequestBuilder<ListTasksRequest, ListTasksResponse, ListTasksRequestBuilder> {

    public ListTasksRequestBuilder(ElasticsearchClient client, ListTasksAction action) {
        super(client, action, new ListTasksRequest());
    }

    /**
     * Should detailed task information be returned.
     */
    public ListTasksRequestBuilder setDetailed(boolean detailed) {
        request.setDetailed(detailed);
        return this;
    }

    /**
     * Should this request wait for all found tasks to complete?
     */
    public final ListTasksRequestBuilder setWaitForCompletion(boolean waitForCompletion) {
        request.setWaitForCompletion(waitForCompletion);
        return this;
    }
}
