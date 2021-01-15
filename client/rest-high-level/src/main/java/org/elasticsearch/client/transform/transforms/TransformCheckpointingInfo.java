/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.client.transform.transforms;

import org.elasticsearch.client.common.TimeUtil;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentParser;

import java.time.Instant;
import java.util.Objects;

public class TransformCheckpointingInfo {

    public static final ParseField LAST_CHECKPOINT = new ParseField("last", "current");
    public static final ParseField NEXT_CHECKPOINT = new ParseField("next", "in_progress");
    public static final ParseField OPERATIONS_BEHIND = new ParseField("operations_behind");
    public static final ParseField CHANGES_LAST_DETECTED_AT = new ParseField("changes_last_detected_at");

    private final TransformCheckpointStats last;
    private final TransformCheckpointStats next;
    private final long operationsBehind;
    private final Instant changesLastDetectedAt;

    private static final ConstructingObjectParser<TransformCheckpointingInfo, Void> LENIENT_PARSER =
            new ConstructingObjectParser<>(
                "transform_checkpointing_info",
                true,
                a -> {
                        long behind = a[2] == null ? 0L : (Long) a[2];
                        Instant changesLastDetectedAt = (Instant)a[3];
                        return new TransformCheckpointingInfo(
                            a[0] == null ? TransformCheckpointStats.EMPTY : (TransformCheckpointStats) a[0],
                            a[1] == null ? TransformCheckpointStats.EMPTY : (TransformCheckpointStats) a[1],
                            behind,
                            changesLastDetectedAt);
                    });

    static {
        LENIENT_PARSER.declareObject(ConstructingObjectParser.optionalConstructorArg(),
                (p, c) -> TransformCheckpointStats.fromXContent(p), LAST_CHECKPOINT);
        LENIENT_PARSER.declareObject(ConstructingObjectParser.optionalConstructorArg(),
                (p, c) -> TransformCheckpointStats.fromXContent(p), NEXT_CHECKPOINT);
        LENIENT_PARSER.declareLong(ConstructingObjectParser.optionalConstructorArg(), OPERATIONS_BEHIND);
        LENIENT_PARSER.declareField(ConstructingObjectParser.optionalConstructorArg(),
            p -> TimeUtil.parseTimeFieldToInstant(p, CHANGES_LAST_DETECTED_AT.getPreferredName()),
            CHANGES_LAST_DETECTED_AT,
            ObjectParser.ValueType.VALUE);
    }

    public TransformCheckpointingInfo(TransformCheckpointStats last,
                                      TransformCheckpointStats next,
                                      long operationsBehind,
                                      Instant changesLastDetectedAt) {
        this.last = Objects.requireNonNull(last);
        this.next = Objects.requireNonNull(next);
        this.operationsBehind = operationsBehind;
        this.changesLastDetectedAt = changesLastDetectedAt;
    }

    public TransformCheckpointStats getLast() {
        return last;
    }

    public TransformCheckpointStats getNext() {
        return next;
    }

    public long getOperationsBehind() {
        return operationsBehind;
    }

    @Nullable
    public Instant getChangesLastDetectedAt() {
        return changesLastDetectedAt;
    }

    public static TransformCheckpointingInfo fromXContent(XContentParser p) {
        return LENIENT_PARSER.apply(p, null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(last, next, operationsBehind, changesLastDetectedAt);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        TransformCheckpointingInfo that = (TransformCheckpointingInfo) other;

        return Objects.equals(this.last, that.last) &&
            Objects.equals(this.next, that.next) &&
            this.operationsBehind == that.operationsBehind &&
            Objects.equals(this.changesLastDetectedAt, that.changesLastDetectedAt);
    }

}
