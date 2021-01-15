/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.search.profile.query;

import java.util.Locale;

public enum QueryTimingType {
    CREATE_WEIGHT,
    BUILD_SCORER,
    NEXT_DOC,
    ADVANCE,
    MATCH,
    SCORE,
    SHALLOW_ADVANCE,
    COMPUTE_MAX_SCORE,
    SET_MIN_COMPETITIVE_SCORE;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
