package cycling;

import java.io.Serializable;
import java.time.LocalTime;

public final class CheckpointResult implements Serializable {
    private final Checkpoint checkpoint;
    private final LocalTime time;

    public CheckpointResult(Checkpoint checkpoint, LocalTime time) {
        this.checkpoint = checkpoint;
        this.time = time;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public LocalTime getTime() {
        return time;
    }
}
