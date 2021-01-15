/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.indices.breaker;

import org.elasticsearch.common.breaker.CircuitBreaker;
import org.elasticsearch.common.component.AbstractLifecycleComponent;

/**
 * Interface for Circuit Breaker services, which provide breakers to classes
 * that load field data.
 */
public abstract class CircuitBreakerService extends AbstractLifecycleComponent {

    protected CircuitBreakerService() {
    }

    /**
     * @return the breaker that can be used to register estimates against
     */
    public abstract CircuitBreaker getBreaker(String name);

    /**
     * @return stats about all breakers
     */
    public abstract AllCircuitBreakerStats stats();

    /**
     * @return stats about a specific breaker
     */
    public abstract CircuitBreakerStats stats(String name);

    @Override
    protected void doStart() {
    }

    @Override
    protected void doStop() {
    }

    @Override
    protected void doClose() {
    }

}
