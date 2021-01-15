/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.search.aggregations.metrics;

public class AvgTests extends AbstractNumericMetricTestCase<AvgAggregationBuilder> {

    @Override
    protected AvgAggregationBuilder doCreateTestAggregatorFactory() {
        return new AvgAggregationBuilder(randomAlphaOfLengthBetween(3, 10));
    }

}
