/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.search.aggregations.bucket.filter;

import org.elasticsearch.search.aggregations.bucket.SingleBucketAggregation;

/**
 * A {@code filter} aggregation. Defines a single bucket that holds all documents that match a specific filter.
 */
public interface Filter extends SingleBucketAggregation {
}
