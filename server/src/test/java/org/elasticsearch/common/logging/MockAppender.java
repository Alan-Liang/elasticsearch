/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.common.logging;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.filter.RegexFilter;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class MockAppender extends AbstractAppender {
    public LogEvent lastEvent;

    public MockAppender(final String name) throws IllegalAccessException {
        super(name, RegexFilter.createFilter(".*(\n.*)*", new String[0], false, null, null), null, false);
    }

    @Override
    public void append(LogEvent event) {
        lastEvent = event.toImmutable();
    }

    ParameterizedMessage lastParameterizedMessage() {
        return (ParameterizedMessage) lastEvent.getMessage();
    }

    public LogEvent getLastEventAndReset() {
        LogEvent toReturn = lastEvent;
        lastEvent = null;
        return toReturn;
    }
}
