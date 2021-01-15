/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.search.aggregations.bucket.terms;

import org.elasticsearch.search.DocValueFormat;
import org.elasticsearch.search.aggregations.Aggregator;
import org.elasticsearch.search.aggregations.AggregatorFactories;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.CardinalityUpperBound;
import org.elasticsearch.search.aggregations.support.AggregationContext;
import org.elasticsearch.search.aggregations.support.ValuesSource;

import java.io.IOException;
import java.util.Map;

interface TermsAggregatorSupplier {
    Aggregator build(String name,
                     AggregatorFactories factories,
                     ValuesSource valuesSource,
                     BucketOrder order,
                     DocValueFormat format,
                     TermsAggregator.BucketCountThresholds bucketCountThresholds,
                     IncludeExclude includeExclude,
                     String executionHint,
                     AggregationContext context,
                     Aggregator parent,
                     Aggregator.SubAggCollectionMode subAggCollectMode,
                     boolean showTermDocCountError,
                     CardinalityUpperBound cardinality,
                     Map<String, Object> metadata) throws IOException;
}
