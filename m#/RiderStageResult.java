 

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public final class RiderStageResult extends WithId implements Serializable {
    private final Rider rider;
    private final LocalTime startTime;
    private final List<CheckpointResult> checkpointResults;
    private final LocalTime finishTime;

    private int mountainPoints;

    private int points;

    public RiderStageResult(Rider rider, LocalTime startTime, List<CheckpointResult> checkpointResults,
                            LocalTime finishTime) {
        this.rider = rider;
        this.startTime = startTime;
        this.checkpointResults = checkpointResults;
        this.finishTime = finishTime;
    }

    public Rider getRider() {
        return rider;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public List<CheckpointResult> getCheckpointResults() {
        return checkpointResults;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public int getMountainPoints() {
        return mountainPoints;
    }

    public int getPoints() {
        return points;
    }

    public LocalTime getCheckPointTime(Checkpoint checkpoint) {
        for (CheckpointResult checkpointResult : checkpointResults) {
            if (checkpointResult.getCheckpoint().equals(checkpoint)) {
                return checkpointResult.getTime();
            }
        }
        return null;
    }

    public void addToMountainPoints(int mountainPoints) {
        this.mountainPoints += mountainPoints;
    }

    public void addToPoints(int points) {
        this.points += points;
    }

    public void resetMountainPoints() {
        mountainPoints = 0;
    }

    public void resetPoints() {
        mountainPoints = 0;
    }

    @Override
    public int getId() {
        return rider.getId();
    }
}
