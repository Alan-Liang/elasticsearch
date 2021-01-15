/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.common.compress;

import org.elasticsearch.common.xcontent.XContent;

/** Exception indicating that we were expecting some {@link XContent} but could
 *  not detect its type. */
public class NotXContentException extends RuntimeException {

    public NotXContentException(String message) {
        super(message);
    }

}
