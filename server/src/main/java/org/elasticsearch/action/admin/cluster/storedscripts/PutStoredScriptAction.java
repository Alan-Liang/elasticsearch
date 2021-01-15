/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.action.admin.cluster.storedscripts;

import org.elasticsearch.action.ActionType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;


public class PutStoredScriptAction extends ActionType<AcknowledgedResponse> {

    public static final PutStoredScriptAction INSTANCE = new PutStoredScriptAction();
    public static final String NAME = "cluster:admin/script/put";

    private PutStoredScriptAction() {
        super(NAME, AcknowledgedResponse::readFrom);
    }

}
