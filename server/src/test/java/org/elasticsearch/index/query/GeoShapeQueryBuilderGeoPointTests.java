/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.index.query;

import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.test.geo.RandomShapeGenerator;

public class GeoShapeQueryBuilderGeoPointTests extends GeoShapeQueryBuilderTests {

    protected String fieldName() {
        return GEO_POINT_FIELD_NAME;
    }

    protected GeoShapeQueryBuilder doCreateTestQueryBuilder(boolean indexedShape) {
        RandomShapeGenerator.ShapeType shapeType = RandomShapeGenerator.ShapeType.POLYGON;
        ShapeBuilder<?, ?, ?> shape = RandomShapeGenerator.createShapeWithin(random(), null, shapeType);
        GeoShapeQueryBuilder builder;
        clearShapeFields();
        if (indexedShape == false) {
            builder = new GeoShapeQueryBuilder(fieldName(), shape);
        } else {
            indexedShapeToReturn = shape;
            indexedShapeId = randomAlphaOfLengthBetween(3, 20);
            builder = new GeoShapeQueryBuilder(fieldName(), indexedShapeId);
            if (randomBoolean()) {
                indexedShapeIndex = randomAlphaOfLengthBetween(3, 20);
                builder.indexedShapeIndex(indexedShapeIndex);
            }
            if (randomBoolean()) {
                indexedShapePath = randomAlphaOfLengthBetween(3, 20);
                builder.indexedShapePath(indexedShapePath);
            }
            if (randomBoolean()) {
                indexedShapeRouting = randomAlphaOfLengthBetween(3, 20);
                builder.indexedShapeRouting(indexedShapeRouting);
            }
        }
        if (randomBoolean()) {
            builder.relation(randomFrom(ShapeRelation.INTERSECTS));
        }

        if (randomBoolean()) {
            builder.ignoreUnmapped(randomBoolean());
        }
        return builder;
    }

}
