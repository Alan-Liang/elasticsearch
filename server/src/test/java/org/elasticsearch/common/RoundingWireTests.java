/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.common;

import org.elasticsearch.common.Rounding.DateTimeUnit;
import org.elasticsearch.common.io.stream.Writeable.Reader;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.test.AbstractWireSerializingTestCase;

public class RoundingWireTests extends AbstractWireSerializingTestCase<Rounding> {
    @Override
    protected Rounding createTestInstance() {
        Rounding.Builder builder;
        if (randomBoolean()) {
            builder = Rounding.builder(randomFrom(DateTimeUnit.values()));
        } else {
            // The time value's millisecond component must be > 0 so we're limited in the suffixes we can use.
            final var tv = randomTimeValue(1, 1000, "d", "h", "ms", "s", "m");
            builder = Rounding.builder(TimeValue.parseTimeValue(tv, "test"));
        }
        if (randomBoolean()) {
            builder.timeZone(randomZone());
        }
        if (randomBoolean()) {
            builder.offset(randomLong());
        }
        return builder.build();
    }

    @Override
    protected Reader<Rounding> instanceReader() {
        return Rounding::read;
    }
}
