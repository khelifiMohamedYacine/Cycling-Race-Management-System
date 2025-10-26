package cycling;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import static cycling.WithId.toIds;

public class Stage extends WithId implements Serializable {

    private static int UNIQUE_ID = 0;

    private final int id;

    private final String name;

    private final String description;

    private final double length;

    private final LocalDateTime startTime;

    private final StageType type;

    private final Race race;

    private final List<Checkpoint> checkpoints;

    private final List<RiderStageResult> riderStageResults;

    private boolean waitingForResults;

    public Stage(String name, String description, double length, LocalDateTime startTime, StageType type, Race race) {
        id = UNIQUE_ID++;
        this.name = name;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.race = race;
        checkpoints = new ArrayList<>();
        riderStageResults = new ArrayList<>();
        waitingForResults = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLength() {
        return length;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public StageType getType() {
        return type;
    }

    public boolean isWaitingForResults() {
        return waitingForResults;
    }

    public boolean isTimeTrial() {
        return type == StageType.TT;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void concludePreparation() {
        waitingForResults = true;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
    }

    public boolean hasRiderResults(Rider rider) {
        for (RiderStageResult riderStageResult : riderStageResults) {
            if (riderStageResult.getRider().equals(rider)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRiderResults() {
        return !riderStageResults.isEmpty();
    }

    public void registerRiderResults(Rider rider, LocalTime[] checkpointTimes) {
        List<CheckpointResult> checkpointResults = new ArrayList<>();
        LocalTime startTime = checkpointTimes[0];
        LocalTime finishTime = checkpointTimes[checkpointTimes.length - 1];
        for (int i = 0; i < checkpoints.size(); i++) {
            Checkpoint checkpoint = checkpoints.get(i);
            LocalTime checkpointTime = checkpointTimes[i + 1];
            checkpointResults.add(new CheckpointResult(checkpoint, checkpointTime));
        }
        race.initRiderRaceResult(rider);
        riderStageResults.add(new RiderStageResult(rider, startTime, checkpointResults, finishTime));
    }

    public RiderStageResult getRiderResult(Rider rider) {
        for (RiderStageResult riderStageResult : riderStageResults) {
            if (riderStageResult.getRider().equals(rider)) {
                return riderStageResult;
            }
        }
        return null;
    }

    public LocalTime getRiderAdjustedElapsedTime(Rider rider) {
        RiderStageResult riderStageResult = getRiderResult(rider);
        if (riderStageResult == null) {
            return null;
        }
        LocalTime adjustedElapsedTime = riderStageResult.getFinishTime();
        boolean adjustedElapsedTimeUpdated = true;
        while (adjustedElapsedTimeUpdated) {
            adjustedElapsedTimeUpdated = false;
            for (RiderStageResult otherRiderStageResult : riderStageResults) {
                if (otherRiderStageResult.equals(riderStageResult)) {
                    continue;
                }
                LocalTime otherFinishTime = otherRiderStageResult.getFinishTime();
                if (otherFinishTime.isBefore(adjustedElapsedTime)) {
                    Duration duration = Duration.between(otherFinishTime, adjustedElapsedTime);
                    if (duration.getSeconds() < 1) {
                        adjustedElapsedTime = otherFinishTime;
                        adjustedElapsedTimeUpdated = true;
                    }
                }
            }
        }
        return adjustedElapsedTime;
    }

    public void deleteRiderResults(Rider rider) {
        riderStageResults.removeIf(riderStageResult -> riderStageResult.getRider().equals(rider));
    }

    public LocalTime[] getRiderResultsWithElapsedTime(Rider rider) {
        RiderStageResult riderStageResult = getRiderResult(rider);
        if (riderStageResult == null) {
            return new LocalTime[0];
        }
        LocalTime[] riderResultsWithElapsedTime = new LocalTime[checkpoints.size() + 1];
        List<CheckpointResult> checkpointResults = riderStageResult.getCheckpointResults();
        for (int i = 0; i < checkpointResults.size(); i++) {
            riderResultsWithElapsedTime[i] = checkpointResults.get(i).getTime();
        }
        Duration duration = Duration.between(riderStageResult.getStartTime(), riderStageResult.getFinishTime());
        LocalTime elapsedTime = LocalTime.ofNanoOfDay(duration.getNano());
        riderResultsWithElapsedTime[checkpoints.size()] = elapsedTime;
        return riderResultsWithElapsedTime;
    }

    public int[] getRidersRank() {
        if (riderStageResults.isEmpty()) {
            return new int[0];
        }
        riderStageResults.sort(Comparator.comparing(this::getRiderElapsedTime));
        return toIds(riderStageResults);
    }

    public List<Rider> getRiders() {
        List<Rider> riders = new ArrayList<>();
        for (RiderStageResult riderStageResult : riderStageResults) {
            riders.add(riderStageResult.getRider());
        }
        return riders;
    }

    private LocalTime getRiderElapsedTime(RiderStageResult riderStageResult) {
        Duration duration = Duration.between(riderStageResult.getStartTime(), riderStageResult.getFinishTime());
        return LocalTime.ofNanoOfDay(duration.getNano());
    }

    public LocalTime[] getRankedAdjustedElapsedTimes() {
        if (riderStageResults.isEmpty()) {
            return new LocalTime[0];
        }
        List<Rider> riders = getRiders();
        riders.sort(Comparator.comparing(this::getRiderFinishTime));
        LocalTime[] rankedAdjustedElapsedTimes = new LocalTime[riders.size()];
        for (int i = 0; i < riders.size(); i++) {
            rankedAdjustedElapsedTimes[i] = getRiderAdjustedElapsedTime(riders.get(i));
        }
        return rankedAdjustedElapsedTimes;
    }

    private LocalTime getRiderFinishTime(Rider rider) {
        return getRiderResult(rider).getFinishTime();
    }

    public boolean isMountain() {
        return type == StageType.HIGH_MOUNTAIN || type == StageType.MEDIUM_MOUNTAIN;
    }

    public int[] computeAndGetRidersMountainPoints() {
        if (riderStageResults.isEmpty()) {
            return new int[0];
        }
        riderStageResults.forEach(RiderStageResult::resetMountainPoints);
        for (Checkpoint checkpoint : checkpoints) {
            if (checkpoint.isMountain()) {
                riderStageResults.sort(Comparator.comparing(riderStageResult -> riderStageResult.getCheckPointTime(checkpoint)));
                updateMountainRiderResults(checkpoint.getType());
            }
        }
        riderStageResults.sort(Comparator.comparing(this::getRiderElapsedTime));
        return transformRiderResults(RiderStageResult::getMountainPoints);
    }

    private void updateMountainRiderResults(CheckpointType type) {
        switch (type) {
            case C4 -> addToRiderPoints(RiderStageResult::addToMountainPoints, 1);
            case C3 -> addToRiderPoints(RiderStageResult::addToMountainPoints, 2, 1);
            case C2 -> addToRiderPoints(RiderStageResult::addToMountainPoints, 5, 3, 2, 1);
            case C1 -> addToRiderPoints(RiderStageResult::addToMountainPoints, 10, 8, 6, 4, 2, 1);
            case HC -> addToRiderPoints(RiderStageResult::addToMountainPoints, 20, 15, 12, 10, 8, 6, 4, 2);
        }
    }

    public int[] computeAndGetRidersPoints() {
        if (riderStageResults.isEmpty()) {
            return new int[0];
        }
        riderStageResults.forEach(RiderStageResult::resetPoints);
        riderStageResults.sort(Comparator.comparing(this::getRiderElapsedTime));
        switch (type) {
            case FLAT ->
                    addToRiderPoints(RiderStageResult::addToPoints, 50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2);
            case MEDIUM_MOUNTAIN ->
                    addToRiderPoints(RiderStageResult::addToPoints, 30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2);
            case HIGH_MOUNTAIN, TT ->
                    addToRiderPoints(RiderStageResult::addToPoints, 20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        }
        return transformRiderResults(RiderStageResult::getPoints);
    }

    private int[] transformRiderResults(ToIntFunction<RiderStageResult> function) {
        int[] ids = new int[riderStageResults.size()];
        for (int i = 0; i < riderStageResults.size(); i++) {
            ids[i] = function.applyAsInt(riderStageResults.get(i));
        }
        return ids;
    }

    private void addToRiderPoints(ObjIntConsumer<RiderStageResult> supplier, int... points) {
        for (int i = 0; i < points.length; i++) {
            if (i < riderStageResults.size()) {
                supplier.accept(riderStageResults.get(i), points[i]);
            }
        }
    }

    public int getRidersPoints(Rider rider) {
        for (RiderStageResult riderStageResult : riderStageResults) {
            if (riderStageResult.getRider().equals(rider)) {
                return riderStageResult.getPoints();
            }
        }
        return 0;
    }

    public int getRidersMountainPoints(Rider rider) {
        for (RiderStageResult riderStageResult : riderStageResults) {
            if (riderStageResult.getRider().equals(rider)) {
                return riderStageResult.getMountainPoints();
            }
        }
        return 0;
    }
}
