/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.client.ml.dataframe.evaluation.regression;

import org.elasticsearch.client.ml.dataframe.evaluation.MlEvaluationNamedXContentProvider;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractXContentTestCase;

import java.io.IOException;

public class RSquaredMetricTests extends AbstractXContentTestCase<RSquaredMetric> {

    @Override
    protected NamedXContentRegistry xContentRegistry() {
        return new NamedXContentRegistry(new MlEvaluationNamedXContentProvider().getNamedXContentParsers());
    }

    @Override
    protected RSquaredMetric createTestInstance() {
        return new RSquaredMetric();
    }

    @Override
    protected RSquaredMetric doParseInstance(XContentParser parser) throws IOException {
        return RSquaredMetric.fromXContent(parser);
    }

    @Override
    protected boolean supportsUnknownFields() {
        return true;
    }
}
