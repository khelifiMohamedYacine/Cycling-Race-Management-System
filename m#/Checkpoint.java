 

import java.io.Serializable;
import java.util.List;

public class Checkpoint extends WithId implements Serializable {

    private static final List<CheckpointType> MOUNTAIN_CHEKPOINT_TYPES = List.of(CheckpointType.C4, CheckpointType.C3, CheckpointType.C2, CheckpointType.C1, CheckpointType.HC);
    private static int UNIQUE_ID = 0;
    private final int id;
    private final double location;

    private final CheckpointType type;
    private final Double averageGradient;
    private final Double length;

    public Checkpoint(double location, CheckpointType type, Double averageGradient, Double length) {
        super();
        id = UNIQUE_ID++;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public double getLocation() {
        return location;
    }

    public CheckpointType getType() {
        return type;
    }

    public Double getAverageGradient() {
        return averageGradient;
    }

    public Double getLength() {
        return length;
    }

    public boolean isMountain() {
        return type != null && MOUNTAIN_CHEKPOINT_TYPES.contains(type);
    }
}
