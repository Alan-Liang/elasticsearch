/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * and the Server Side Public License, v 1; you may not use this file except in
 * compliance with, at your election, the Elastic License or the Server Side
 * Public License, v 1.
 */

package org.elasticsearch.indices.recovery;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.index.shard.ShardId;

import java.io.IOException;

final class RecoveryFinalizeRecoveryRequest extends RecoveryTransportRequest {

    private final long recoveryId;
    private final ShardId shardId;
    private final long globalCheckpoint;
    private final long trimAboveSeqNo;

    RecoveryFinalizeRecoveryRequest(StreamInput in) throws IOException {
        super(in);
        recoveryId = in.readLong();
        shardId = new ShardId(in);
        globalCheckpoint = in.readZLong();
        trimAboveSeqNo = in.readZLong();
    }

    RecoveryFinalizeRecoveryRequest(final long recoveryId, final long requestSeqNo, final ShardId shardId,
                                    final long globalCheckpoint, final long trimAboveSeqNo) {
        super(requestSeqNo);
        this.recoveryId = recoveryId;
        this.shardId = shardId;
        this.globalCheckpoint = globalCheckpoint;
        this.trimAboveSeqNo = trimAboveSeqNo;
    }

    public long recoveryId() {
        return this.recoveryId;
    }

    public ShardId shardId() {
        return shardId;
    }

    public long globalCheckpoint() {
        return globalCheckpoint;
    }

    public long trimAboveSeqNo() {
        return trimAboveSeqNo;
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeLong(recoveryId);
        shardId.writeTo(out);
        out.writeZLong(globalCheckpoint);
        out.writeZLong(trimAboveSeqNo);
    }
}
