/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.gradle;

import org.gradle.process.CommandLineArgumentProvider;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link CommandLineArgumentProvider} implementation that simply returns the given list. This implementation does not track any
 * arguments as inputs, so this is useful for passing arguments that should not be used for the purposes of input snapshotting.
 */
public class SimpleCommandLineArgumentProvider implements CommandLineArgumentProvider {
    private final List<String> arguments;

    public SimpleCommandLineArgumentProvider(String... arguments) {
        this.arguments = Arrays.asList(arguments);
    }

    @Override
    public Iterable<String> asArguments() {
        return arguments;
    }
}
