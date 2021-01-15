/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.cli;

/**
 * A multi-command that is aware of logging. This class should be preferred over the base {@link MultiCommand} class for any CLI tools that
 * depend on core Elasticsearch as they could directly or indirectly touch classes that touch logging and as such logging needs to be
 * configured.
 */
public abstract class LoggingAwareMultiCommand extends MultiCommand {

    /**
     * Construct the command with the specified command description. This command will have logging configured without reading Elasticsearch
     * configuration files.
     *
     * @param description the command description
     */
    public LoggingAwareMultiCommand(final String description) {
        super(description, CommandLoggingConfigurator::configureLoggingWithoutConfig);
    }

}
