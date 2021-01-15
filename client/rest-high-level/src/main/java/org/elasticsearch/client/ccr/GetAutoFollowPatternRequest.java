/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.client.ccr;

import org.elasticsearch.client.Validatable;

import java.util.Objects;

/**
 * Request class for get auto follow pattern api.
 */
public final class GetAutoFollowPatternRequest implements Validatable {

    private final String name;

    /**
     * Get all auto follow patterns
     */
    public GetAutoFollowPatternRequest() {
        this.name = null;
    }

    /**
     * Get auto follow pattern with the specified name
     *
     * @param name The name of the auto follow pattern to get
     */
    public GetAutoFollowPatternRequest(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }
}
