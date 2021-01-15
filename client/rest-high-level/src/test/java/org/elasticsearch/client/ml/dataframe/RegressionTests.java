/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.client.ml.dataframe;

import org.elasticsearch.client.ml.inference.MlInferenceNamedXContentProvider;
import org.elasticsearch.client.ml.inference.preprocessing.FrequencyEncodingTests;
import org.elasticsearch.client.ml.inference.preprocessing.OneHotEncodingTests;
import org.elasticsearch.client.ml.inference.preprocessing.TargetMeanEncodingTests;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractXContentTestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegressionTests extends AbstractXContentTestCase<Regression> {

    public static Regression randomRegression() {
        return Regression.builder(randomAlphaOfLength(10))
            .setLambda(randomBoolean() ? null : randomDoubleBetween(0.0, Double.MAX_VALUE, true))
            .setGamma(randomBoolean() ? null : randomDoubleBetween(0.0, Double.MAX_VALUE, true))
            .setEta(randomBoolean() ? null : randomDoubleBetween(0.001, 1.0, true))
            .setMaxTrees(randomBoolean() ? null : randomIntBetween(1, 2000))
            .setFeatureBagFraction(randomBoolean() ? null : randomDoubleBetween(0.0, 1.0, false))
            .setNumTopFeatureImportanceValues(randomBoolean() ? null : randomIntBetween(0, Integer.MAX_VALUE))
            .setPredictionFieldName(randomBoolean() ? null : randomAlphaOfLength(10))
            .setTrainingPercent(randomBoolean() ? null : randomDoubleBetween(1.0, 100.0, true))
            .setLossFunction(randomBoolean() ? null : randomFrom(Regression.LossFunction.values()))
            .setLossFunctionParameter(randomBoolean() ? null : randomDoubleBetween(1.0, Double.MAX_VALUE, true))
            .setFeatureProcessors(randomBoolean() ? null :
                Stream.generate(() -> randomFrom(FrequencyEncodingTests.createRandom(),
                    OneHotEncodingTests.createRandom(),
                    TargetMeanEncodingTests.createRandom()))
                    .limit(randomIntBetween(1, 10))
                    .collect(Collectors.toList()))
            .build();
    }

    @Override
    protected Predicate<String> getRandomFieldsExcludeFilter() {
        return field -> field.startsWith("feature_processors");
    }

    @Override
    protected Regression createTestInstance() {
        return randomRegression();
    }

    @Override
    protected Regression doParseInstance(XContentParser parser) throws IOException {
        return Regression.fromXContent(parser);
    }

    @Override
    protected boolean supportsUnknownFields() {
        return true;
    }

    @Override
    protected NamedXContentRegistry xContentRegistry() {
        List<NamedXContentRegistry.Entry> namedXContent = new ArrayList<>();
        namedXContent.addAll(new MlInferenceNamedXContentProvider().getNamedXContentParsers());
        return new NamedXContentRegistry(namedXContent);
    }
}
