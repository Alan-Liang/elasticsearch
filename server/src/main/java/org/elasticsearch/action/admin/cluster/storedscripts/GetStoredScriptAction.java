/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.storedscripts;

import org.elasticsearch.action.ActionType;

public class GetStoredScriptAction extends ActionType<GetStoredScriptResponse> {

    public static final GetStoredScriptAction INSTANCE = new GetStoredScriptAction();
    public static final String NAME = "cluster:admin/script/get";

    private GetStoredScriptAction() {
        super(NAME, GetStoredScriptResponse::new);
    }
}
