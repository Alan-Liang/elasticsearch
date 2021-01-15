/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.Version;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.index.mapper.MapperService;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Objects;

public class RoutingMissingException extends ElasticsearchException {

    private final String id;

    public RoutingMissingException(String index, String id) {
        super("routing is required for [" + index + "]/[" + id + "]");
        Objects.requireNonNull(index, "index must not be null");
        Objects.requireNonNull(id, "id must not be null");
        setIndex(index);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public RestStatus status() {
        return RestStatus.BAD_REQUEST;
    }

    public RoutingMissingException(StreamInput in) throws IOException{
        super(in);
        if (in.getVersion().before(Version.V_8_0_0)) {
            in.readString();
        }
        id = in.readString();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        if (out.getVersion().before(Version.V_8_0_0)) {
            out.writeString(MapperService.SINGLE_MAPPING_NAME);
        }
        out.writeString(id);
    }
}
