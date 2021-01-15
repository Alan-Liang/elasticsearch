/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.index.seqno;

import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractXContentTestCase;

import java.io.IOException;

public class RetentionLeaseXContentTests extends AbstractXContentTestCase<RetentionLease> {

    @Override
    protected RetentionLease createTestInstance() {
        final String id = randomAlphaOfLength(8);
        final long retainingSequenceNumber = randomNonNegativeLong();
        final long timestamp = randomNonNegativeLong();
        final String source = randomAlphaOfLength(8);
        return new RetentionLease(id, retainingSequenceNumber, timestamp, source);
    }

    @Override
    protected RetentionLease doParseInstance(final XContentParser parser) throws IOException {
        return RetentionLease.fromXContent(parser);
    }

    @Override
    protected boolean supportsUnknownFields() {
        return false;
    }

}
