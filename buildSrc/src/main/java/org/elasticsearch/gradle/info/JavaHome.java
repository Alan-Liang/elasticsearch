/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */
package org.elasticsearch.gradle.info;

import org.gradle.api.provider.Provider;

import java.io.File;

public class JavaHome {
    private Integer version;
    private Provider<File> javaHome;

    private JavaHome(int version, Provider<File> javaHome) {
        this.version = version;
        this.javaHome = javaHome;
    }

    public static JavaHome of(int version, Provider<File> javaHome) {
        return new JavaHome(version, javaHome);
    }

    public Integer getVersion() {
        return version;
    }

    public Provider<File> getJavaHome() {
        return javaHome;
    }
}
