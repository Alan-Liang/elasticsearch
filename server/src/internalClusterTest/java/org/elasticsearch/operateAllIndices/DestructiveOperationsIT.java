/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.operateAllIndices;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.action.support.DestructiveOperations;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.After;

import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertAcked;
import static org.hamcrest.Matchers.equalTo;

public class DestructiveOperationsIT extends ESIntegTestCase {

    @After
    public void afterTest() {
        Settings settings = Settings.builder().put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), (String)null).build();
        assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));
    }

    public void testDeleteIndexIsRejected() throws Exception {
        Settings settings = Settings.builder()
                .put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), true)
                .build();
        assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));

        createIndex("index1", "1index");

        // Should succeed, since no wildcards
        assertAcked(client().admin().indices().prepareDelete("1index").get());

        expectThrows(IllegalArgumentException.class, () -> client().admin().indices().prepareDelete("i*").get());
        expectThrows(IllegalArgumentException.class, () -> client().admin().indices().prepareDelete("_all").get());
    }

    public void testDeleteIndexDefaultBehaviour() throws Exception {
        if (randomBoolean()) {
            Settings settings = Settings.builder()
                    .put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), false)
                    .build();
            assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));
        }

        createIndex("index1", "1index");

        if (randomBoolean()) {
            assertAcked(client().admin().indices().prepareDelete("_all").get());
        } else {
            assertAcked(client().admin().indices().prepareDelete("*").get());
        }

        assertThat(indexExists("_all"), equalTo(false));
    }

    public void testCloseIndexIsRejected() throws Exception {
        Settings settings = Settings.builder()
                .put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), true)
                .build();
        assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));

        createIndex("index1", "1index");

        // Should succeed, since no wildcards
        assertAcked(client().admin().indices().prepareClose("1index").get());

        expectThrows(IllegalArgumentException.class, () -> client().admin().indices().prepareClose("i*").get());
        expectThrows(IllegalArgumentException.class, () -> client().admin().indices().prepareClose("_all").get());
    }

    public void testCloseIndexDefaultBehaviour() throws Exception {
        if (randomBoolean()) {
            Settings settings = Settings.builder()
                    .put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), false)
                    .build();
            assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));
        }

        createIndex("index1", "1index");

        if (randomBoolean()) {
            assertAcked(client().admin().indices().prepareClose("_all").get());
        } else {
            assertAcked(client().admin().indices().prepareClose("*").get());
        }

        ClusterState state = client().admin().cluster().prepareState().get().getState();
        for (ObjectObjectCursor<String, IndexMetadata> indexMetadataObjectObjectCursor : state.getMetadata().indices()) {
            assertEquals(IndexMetadata.State.CLOSE, indexMetadataObjectObjectCursor.value.getState());
        }
    }

    public void testOpenIndexIsRejected() throws Exception {
        Settings settings = Settings.builder()
                .put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), true)
                .build();
        assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));

        createIndex("index1", "1index");
        assertAcked(client().admin().indices().prepareClose("1index", "index1").get());

        expectThrows(IllegalArgumentException.class, () -> client().admin().indices().prepareOpen("i*").get());
        expectThrows(IllegalArgumentException.class, () -> client().admin().indices().prepareOpen("_all").get());
    }

    public void testOpenIndexDefaultBehaviour() throws Exception {
        if (randomBoolean()) {
            Settings settings = Settings.builder()
                    .put(DestructiveOperations.REQUIRES_NAME_SETTING.getKey(), false)
                    .build();
            assertAcked(client().admin().cluster().prepareUpdateSettings().setTransientSettings(settings));
        }

        createIndex("index1", "1index");
        assertAcked(client().admin().indices().prepareClose("1index", "index1").get());

        if (randomBoolean()) {
            assertAcked(client().admin().indices().prepareOpen("_all").get());
        } else {
            assertAcked(client().admin().indices().prepareOpen("*").get());
        }

        ClusterState state = client().admin().cluster().prepareState().get().getState();
        for (ObjectObjectCursor<String, IndexMetadata> indexMetadataObjectObjectCursor : state.getMetadata().indices()) {
            assertEquals(IndexMetadata.State.OPEN, indexMetadataObjectObjectCursor.value.getState());
        }
    }
}
