/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.http;

public interface HttpPipelinedMessage extends Comparable<HttpPipelinedMessage> {

    /**
     * Get the sequence number for this message.
     *
     * @return the sequence number
     */
    int getSequence();

    @Override
    default int compareTo(HttpPipelinedMessage o) {
        return Integer.compare(getSequence(), o.getSequence());
    }
}
