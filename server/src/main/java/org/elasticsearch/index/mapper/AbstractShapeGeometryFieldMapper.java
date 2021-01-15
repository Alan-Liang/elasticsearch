/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.index.mapper;

import org.elasticsearch.common.Explicit;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder.Orientation;
import org.elasticsearch.index.analysis.NamedAnalyzer;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * Base class for {@link GeoShapeFieldMapper} and {@link LegacyGeoShapeFieldMapper}
 */
public abstract class AbstractShapeGeometryFieldMapper<Parsed, Processed> extends AbstractGeometryFieldMapper<Parsed, Processed> {

    public static Parameter<Explicit<Boolean>> coerceParam(Function<FieldMapper, Explicit<Boolean>> initializer,
                                                           boolean coerceByDefault) {
        return Parameter.explicitBoolParam("coerce", true, initializer, coerceByDefault);
    }

    public static Parameter<Explicit<Orientation>> orientationParam(Function<FieldMapper, Explicit<Orientation>> initializer) {
        return new Parameter<>("orientation", true,
            () -> new Explicit<>(Orientation.RIGHT, false),
            (n, c, o) -> new Explicit<>(ShapeBuilder.Orientation.fromString(o.toString()), true),
            initializer)
            .setSerializer((b, f, v) -> b.field(f, v.value()), v -> v.value().toString());
    }

    public abstract static class AbstractShapeGeometryFieldType extends AbstractGeometryFieldType {

        private final Orientation orientation;

        protected AbstractShapeGeometryFieldType(String name, boolean isSearchable, boolean isStored, boolean hasDocValues,
                                                 boolean parsesArrayValue, Parser<?> parser,
                                                 Orientation orientation, Map<String, String> meta) {
            super(name, isSearchable, isStored, hasDocValues, parsesArrayValue, parser, meta);
            this.orientation = orientation;
        }

        public Orientation orientation() { return this.orientation; }
    }

    protected Explicit<Boolean> coerce;
    protected Explicit<Orientation> orientation;

    protected AbstractShapeGeometryFieldMapper(String simpleName, MappedFieldType mappedFieldType,
                                               Map<String, NamedAnalyzer> indexAnalyzers,
                                               Explicit<Boolean> ignoreMalformed, Explicit<Boolean> coerce,
                                               Explicit<Boolean> ignoreZValue, Explicit<Orientation> orientation,
                                               MultiFields multiFields, CopyTo copyTo,
                                               Indexer<Parsed, Processed> indexer, Parser<Parsed> parser) {
        super(simpleName, mappedFieldType, indexAnalyzers, ignoreMalformed, ignoreZValue, multiFields, copyTo, indexer, parser);
        this.coerce = coerce;
        this.orientation = orientation;
    }

    protected AbstractShapeGeometryFieldMapper(String simpleName, MappedFieldType mappedFieldType,
                                               Explicit<Boolean> ignoreMalformed, Explicit<Boolean> coerce,
                                               Explicit<Boolean> ignoreZValue, Explicit<Orientation> orientation,
                                               MultiFields multiFields, CopyTo copyTo,
                                               Indexer<Parsed, Processed> indexer, Parser<Parsed> parser) {
        this(simpleName, mappedFieldType, Collections.emptyMap(),
            ignoreMalformed, coerce, ignoreZValue, orientation, multiFields, copyTo, indexer, parser);
    }

    @Override
    public final boolean parsesArrayValue() {
        return false;
    }

    public boolean coerce() {
        return coerce.value();
    }

    public Orientation orientation() {
        return orientation.value();
    }
}
