/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.painless.spi.annotation;

import java.util.Map;

public class NonDeterministicAnnotationParser implements WhitelistAnnotationParser {

    public static final NonDeterministicAnnotationParser INSTANCE = new NonDeterministicAnnotationParser();

    private NonDeterministicAnnotationParser() {}

    @Override
    public Object parse(Map<String, String> arguments) {
        if (arguments.isEmpty() == false) {
            throw new IllegalArgumentException(
                "unexpected parameters for [@" + NonDeterministicAnnotation.NAME + "] annotation, found " + arguments
            );
        }

        return NonDeterministicAnnotation.INSTANCE;
    }
}
