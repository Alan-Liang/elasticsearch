/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.storedscripts;

import org.elasticsearch.common.io.stream.BytesStreamOutput;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.test.ESTestCase;

import java.io.IOException;

import static org.elasticsearch.test.VersionUtils.randomVersion;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetStoredScriptRequestTests extends ESTestCase {
    public void testGetIndexedScriptRequestSerialization() throws IOException {
        GetStoredScriptRequest request = new GetStoredScriptRequest("id");

        BytesStreamOutput out = new BytesStreamOutput();
        out.setVersion(randomVersion(random()));
        request.writeTo(out);

        StreamInput in = out.bytes().streamInput();
        in.setVersion(out.getVersion());
        GetStoredScriptRequest request2 = new GetStoredScriptRequest(in);

        assertThat(request2.id(), equalTo(request.id()));
    }
}
