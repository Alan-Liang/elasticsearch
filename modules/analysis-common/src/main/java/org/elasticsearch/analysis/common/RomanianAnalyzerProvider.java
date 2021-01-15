/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.analysis.common;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.analysis.Analysis;

public class RomanianAnalyzerProvider extends AbstractIndexAnalyzerProvider<RomanianAnalyzer> {

    private final RomanianAnalyzer analyzer;

    RomanianAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new RomanianAnalyzer(
            Analysis.parseStopWords(env, settings, RomanianAnalyzer.getDefaultStopSet()),
            Analysis.parseStemExclusion(settings, CharArraySet.EMPTY_SET)
        );
        analyzer.setVersion(version);
    }

    @Override
    public RomanianAnalyzer get() {
        return this.analyzer;
    }
}
