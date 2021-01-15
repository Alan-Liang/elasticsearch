/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.common.xcontent.support.filtering;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

public class SmileFilteringGeneratorTests extends AbstractXContentFilteringTestCase {

    @Override
    protected XContentType getXContentType() {
        return XContentType.SMILE;
    }

    @Override
    protected void assertFilterResult(XContentBuilder expected, XContentBuilder actual) {
        assertXContentBuilderAsBytes(expected, actual);
    }
}
