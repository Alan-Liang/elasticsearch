/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.cluster;

import org.elasticsearch.Version;
import org.elasticsearch.common.io.stream.NamedWriteable;

/**
 * Diff that also support NamedWriteable interface
 */
public interface NamedDiff<T extends Diffable<T>> extends Diff<T>, NamedWriteable {
    /**
     * The minimal version of the recipient this custom object can be sent to
     */
    default Version getMinimalSupportedVersion() {
        return Version.CURRENT.minimumIndexCompatibilityVersion();
    }

}
