/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.support;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

/**
 * Holds the action filters injected through plugins, properly sorted by {@link org.elasticsearch.action.support.ActionFilter#order()}
 */
public class ActionFilters {

    private final ActionFilter[] filters;

    public ActionFilters(Set<ActionFilter> actionFilters) {
        this.filters = actionFilters.toArray(new ActionFilter[actionFilters.size()]);
        Arrays.sort(filters, new Comparator<ActionFilter>() {
            @Override
            public int compare(ActionFilter o1, ActionFilter o2) {
                return Integer.compare(o1.order(), o2.order());
            }
        });
    }

    /**
     * Returns the action filters that have been injected
     */
    public ActionFilter[] filters() {
        return filters;
    }
}
