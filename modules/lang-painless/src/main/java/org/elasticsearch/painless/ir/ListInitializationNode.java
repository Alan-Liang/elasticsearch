/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.painless.ir;

import org.elasticsearch.painless.Location;
import org.elasticsearch.painless.phase.IRTreeVisitor;

public class ListInitializationNode extends ArgumentsNode {

    /* ---- begin visitor ---- */

    @Override
    public <Scope> void visit(IRTreeVisitor<Scope> irTreeVisitor, Scope scope) {
        irTreeVisitor.visitListInitialization(this, scope);
    }

    @Override
    public <Scope> void visitChildren(IRTreeVisitor<Scope> irTreeVisitor, Scope scope) {
        for (ExpressionNode argumentNode : getArgumentNodes()) {
            argumentNode.visit(irTreeVisitor, scope);
        }
    }

    /* ---- end visitor ---- */

    public ListInitializationNode(Location location) {
        super(location);
    }

}
