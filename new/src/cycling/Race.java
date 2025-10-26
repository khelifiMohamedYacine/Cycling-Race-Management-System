package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

import static cycling.WithId.toIds;

public class Race extends WithId implements Serializable {

    private static int UNIQUE_ID = 0;

    private final int id;

    private final String name;

    private final String description;

    private final List<Stage> stages;

    private final List<RiderRaceResult> riderRaceResults;

    public Race(String name, String description) {
        id = UNIQUE_ID++;
        this.name = name;
        this.description = description;
        stages = new ArrayList<>();
        riderRaceResults = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void addStage(Stage stage) {
        stages.add(stage);
    }

    public String getDescription() {
        return description;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public double getTotalLength() {
        double totalLength = 0;
        for (Stage stage : stages) {
            totalLength += stage.getLength();
        }
        return totalLength;
    }

    public void removeRider(Rider riderToRemove) {
        riderRaceResults.removeIf(riderRaceResult -> riderRaceResult.getRider().equals(riderToRemove));
        for (Stage stage : stages) {
            stage.deleteRiderResults(riderToRemove);
        }
    }

    public int[] getRidersGeneralClassificationRank() {
        if (riderRaceResults.isEmpty()) {
            return new int[0];
        }
        computeRiderRaceResults();
        riderRaceResults.sort(Comparator.comparing(RiderRaceResult::getTotalRaceAdjustedElapsedTime));
        return toIds(riderRaceResults);
    }

    private void computeRiderRaceResults() {
        riderRaceResults.forEach(RiderRaceResult::resetTotals);
        for (Stage stage : stages) {
            stage.computeAndGetRidersPoints();
            stage.computeAndGetRidersMountainPoints();
            for (RiderRaceResult riderRaceResult : riderRaceResults) {
                Rider rider = riderRaceResult.getRider();
                LocalTime riderAdjustedElapsedTime = stage.getRiderAdjustedElapsedTime(rider);
                riderRaceResult.addToTotalRaceAdjustedElapsedTime(riderAdjustedElapsedTime);
                riderRaceResult.addToTotalPoints(stage.getRidersPoints(rider));
                riderRaceResult.addToTotalMountainPoints(stage.getRidersMountainPoints(rider));
            }
        }
    }

    public LocalTime[] getGeneralClassificationTimes() {
        if (riderRaceResults.isEmpty()) {
            return new LocalTime[0];
        }
        computeRiderRaceResults();
        riderRaceResults.sort(Comparator.comparing(RiderRaceResult::getTotalRaceAdjustedElapsedTime));
        LocalTime[] totalRaceAdjustedElapsedTimes = new LocalTime[riderRaceResults.size()];
        for (int i = 0; i < riderRaceResults.size(); i++) {
            totalRaceAdjustedElapsedTimes[i] = riderRaceResults.get(i).getTotalRaceAdjustedElapsedTime();
        }
        return totalRaceAdjustedElapsedTimes;
    }

    public int[] getRidersPoints() {
        if (riderRaceResults.isEmpty()) {
            return new int[0];
        }
        computeRiderRaceResults();
        riderRaceResults.sort(Comparator.comparing(RiderRaceResult::getTotalRaceAdjustedElapsedTime));
        return transformRiderResults(RiderRaceResult::getTotalPoints);
    }

    public void initRiderRaceResult(Rider rider) {
        for (RiderRaceResult riderRaceResult : riderRaceResults) {
            if (riderRaceResult.getRider().equals(rider)) {
                return;
            }
        }
        riderRaceResults.add(new RiderRaceResult(rider));
    }

    private int[] transformRiderResults(ToIntFunction<RiderRaceResult> function) {
        int[] ids = new int[riderRaceResults.size()];
        for (int i = 0; i < riderRaceResults.size(); i++) {
            ids[i] = function.applyAsInt(riderRaceResults.get(i));
        }
        return ids;
    }

    public int[] getRidersMountainPoints() {
        if (riderRaceResults.isEmpty()) {
            return new int[0];
        }
        computeRiderRaceResults();
        riderRaceResults.sort(Comparator.comparing(RiderRaceResult::getTotalRaceAdjustedElapsedTime));
        return transformRiderResults(RiderRaceResult::getTotalMountainPoints);
    }

    public int[] getRidersPointClassificationRank() {
        if (riderRaceResults.isEmpty()) {
            return new int[0];
        }
        computeRiderRaceResults();
        riderRaceResults.sort(Comparator.comparing(RiderRaceResult::getTotalPoints).reversed());
        return toIds(riderRaceResults);
    }

    public int[] getRidersMountainPointClassificationRank() {
        if (riderRaceResults.isEmpty()) {
            return new int[0];
        }
        computeRiderRaceResults();
        riderRaceResults.sort(Comparator.comparing(RiderRaceResult::getTotalPoints).reversed());
        return toIds(riderRaceResults);
    }
}
