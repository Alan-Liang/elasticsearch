/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.common.compress;

/** Exception indicating that we were expecting something compressed, which
 *  was not compressed or corrupted so that the compression format could not
 *  be detected. */
public class NotCompressedException extends RuntimeException {

    public NotCompressedException() {
        super();
    }

}
