/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.common.geo.parsers;

import org.locationtech.jts.geom.Coordinate;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.xcontent.ToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Node used to represent a tree of coordinates.
 * <p>
 * Can either be a leaf node consisting of a Coordinate, or a parent with
 * children
 */
public class CoordinateNode implements ToXContentObject {
    public final Coordinate coordinate;
    public final List<CoordinateNode> children;

    /**
     * Creates a new leaf CoordinateNode
     *
     * @param coordinate
     *            Coordinate for the Node
     */
    CoordinateNode(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.children = null;
    }

    /**
     * Creates a new parent CoordinateNode
     *
     * @param children
     *            Children of the Node
     */
    CoordinateNode(List<CoordinateNode> children) {
        this.children = children;
        this.coordinate = null;
    }

    public boolean isEmpty() {
        return (coordinate == null && (children == null || children.isEmpty()));
    }

    protected int numDimensions() {
        if (isEmpty()) {
            throw new ElasticsearchException("attempting to get number of dimensions on an empty coordinate node");
        }
        if (coordinate != null) {
            return Double.isNaN(coordinate.z) ? 2 : 3;
        }
        return children.get(0).numDimensions();
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        if (children == null) {
            builder.startArray().value(coordinate.x).value(coordinate.y).endArray();
        } else {
            builder.startArray();
            for (CoordinateNode child : children) {
                child.toXContent(builder, params);
            }
            builder.endArray();
        }
        return builder;
    }
}
