/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.analysis.common;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.miscellaneous.ScandinavianFoldingFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.analysis.NormalizingTokenFilterFactory;

/**
 * Factory for {@link ScandinavianFoldingFilter}
 */
public class ScandinavianFoldingFilterFactory extends AbstractTokenFilterFactory implements NormalizingTokenFilterFactory {

    ScandinavianFoldingFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new ScandinavianFoldingFilter(tokenStream);
    }

}
