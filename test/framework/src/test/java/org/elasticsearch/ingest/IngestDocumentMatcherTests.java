/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.ingest;

import org.elasticsearch.test.ESTestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.ingest.IngestDocumentMatcher.assertIngestDocument;

public class IngestDocumentMatcherTests extends ESTestCase {

    public void testDifferentMapData() {
        Map<String, Object> sourceAndMetadata1 = new HashMap<>();
        sourceAndMetadata1.put("foo", "bar");
        IngestDocument document1 = new IngestDocument(sourceAndMetadata1, new HashMap<>());
        IngestDocument document2 = new IngestDocument(new HashMap<>(), new HashMap<>());
        assertThrowsOnComparision(document1, document2);
    }

    public void testDifferentLengthListData() {
        String rootKey = "foo";
        IngestDocument document1 =
            new IngestDocument(Collections.singletonMap(rootKey, Arrays.asList("bar", "baz")), new HashMap<>());
        IngestDocument document2 =
            new IngestDocument(Collections.singletonMap(rootKey, Collections.emptyList()), new HashMap<>());
        assertThrowsOnComparision(document1, document2);
    }

    public void testDifferentNestedListFieldData() {
        String rootKey = "foo";
        IngestDocument document1 =
            new IngestDocument(Collections.singletonMap(rootKey, Arrays.asList("bar", "baz")), new HashMap<>());
        IngestDocument document2 =
            new IngestDocument(Collections.singletonMap(rootKey, Arrays.asList("bar", "blub")), new HashMap<>());
        assertThrowsOnComparision(document1, document2);
    }

    public void testDifferentNestedMapFieldData() {
        String rootKey = "foo";
        IngestDocument document1 =
            new IngestDocument(Collections.singletonMap(rootKey, Collections.singletonMap("bar", "baz")), new HashMap<>());
        IngestDocument document2 =
            new IngestDocument(Collections.singletonMap(rootKey, Collections.singletonMap("bar", "blub")), new HashMap<>());
        assertThrowsOnComparision(document1, document2);
    }

    public void testOnTypeConflict() {
        String rootKey = "foo";
        IngestDocument document1 =
            new IngestDocument(Collections.singletonMap(rootKey, Collections.singletonList("baz")), new HashMap<>());
        IngestDocument document2 = new IngestDocument(
            Collections.singletonMap(rootKey, Collections.singletonMap("blub", "blab")), new HashMap<>()
        );
        assertThrowsOnComparision(document1, document2);
    }

    private static void assertThrowsOnComparision(IngestDocument document1, IngestDocument document2) {
        expectThrows(AssertionError.class, () -> assertIngestDocument(document1, document2));
        expectThrows(AssertionError.class, () -> assertIngestDocument(document2, document1));
    }
}
