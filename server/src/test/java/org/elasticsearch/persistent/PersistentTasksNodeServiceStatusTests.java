/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.persistent;

import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.test.AbstractWireSerializingTestCase;
import org.elasticsearch.persistent.PersistentTasksNodeService.Status;

import static org.hamcrest.Matchers.containsString;

public class PersistentTasksNodeServiceStatusTests extends AbstractWireSerializingTestCase<Status> {

    @Override
    protected Status createTestInstance() {
        return new Status(randomFrom(AllocatedPersistentTask.State.values()));
    }

    @Override
    protected Writeable.Reader<Status> instanceReader() {
        return Status::new;
    }

    public void testToString() {
        assertThat(createTestInstance().toString(), containsString("state"));
    }
}
