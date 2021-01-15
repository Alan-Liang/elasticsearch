/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.rest;

import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.io.stream.BytesStreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * A channel used to construct bytes / builder based outputs, and send responses.
 */
public interface RestChannel {

    XContentBuilder newBuilder() throws IOException;

    XContentBuilder newErrorBuilder() throws IOException;

    XContentBuilder newBuilder(@Nullable XContentType xContentType, boolean useFiltering) throws IOException;

    XContentBuilder newBuilder(@Nullable XContentType xContentType, @Nullable XContentType responseContentType,
            boolean useFiltering) throws IOException;

    BytesStreamOutput bytesOutput();

    RestRequest request();

    /**
     * @return true iff an error response should contain additional details like exception traces.
     */
    boolean detailedErrorsEnabled();

    void sendResponse(RestResponse response);
}
