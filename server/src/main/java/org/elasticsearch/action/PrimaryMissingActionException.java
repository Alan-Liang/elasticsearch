/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.io.stream.StreamInput;

import java.io.IOException;

public class PrimaryMissingActionException extends ElasticsearchException {

    public PrimaryMissingActionException(String message) {
        super(message);
    }

    public PrimaryMissingActionException(StreamInput in) throws IOException {
        super(in);
    }
}
