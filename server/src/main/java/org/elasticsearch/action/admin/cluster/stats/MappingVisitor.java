/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.stats;

import java.util.Map;
import java.util.function.BiConsumer;

public final class MappingVisitor {

    private MappingVisitor() {}

    public static void visitMapping(Map<String, ?> mapping, BiConsumer<String, Map<String, ?>> fieldMappingConsumer) {
        visitMapping(mapping, "", fieldMappingConsumer);
    }

    private static void visitMapping(Map<String, ?> mapping, String path, BiConsumer<String, Map<String, ?>> fieldMappingConsumer) {
        Object properties = mapping.get("properties");
        if (properties instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, ?> propertiesAsMap = (Map<String, ?>) properties;
            for (String field : propertiesAsMap.keySet()) {
                Object v = propertiesAsMap.get(field);
                if (v instanceof Map) {

                    @SuppressWarnings("unchecked")
                    Map<String, ?> fieldMapping = (Map<String, ?>) v;
                    fieldMappingConsumer.accept(path + field, fieldMapping);
                    visitMapping(fieldMapping, path + field + ".", fieldMappingConsumer);

                    // Multi fields
                    Object fieldsO = fieldMapping.get("fields");
                    if (fieldsO instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, ?> fields = (Map<String, ?>) fieldsO;
                        for (String subfield : fields.keySet()) {
                            Object v2 = fields.get(subfield);
                            if (v2 instanceof Map) {
                                @SuppressWarnings("unchecked")
                                Map<String, ?> fieldMapping2 = (Map<String, ?>) v2;
                                fieldMappingConsumer.accept(path + field + "." + subfield, fieldMapping2);
                            }
                        }
                    }
                }
            }
        }
    }

}
