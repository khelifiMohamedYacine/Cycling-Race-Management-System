 

import java.io.Serializable;
import java.time.LocalTime;

public class RiderRaceResult extends WithId implements Serializable {

    private final Rider rider;

    private LocalTime totalRaceAdjustedElapsedTime;

    private int totalMountainPoints;

    private int totalPoints;

    public RiderRaceResult(Rider rider) {
        this.rider = rider;
    }

    public Rider getRider() {
        return rider;
    }

    public void addToTotalRaceAdjustedElapsedTime(LocalTime riderAdjustedElapsedTime) {
        if (totalRaceAdjustedElapsedTime == null) {
            totalRaceAdjustedElapsedTime = riderAdjustedElapsedTime;
        } else {
            totalRaceAdjustedElapsedTime = totalRaceAdjustedElapsedTime.plusNanos(riderAdjustedElapsedTime.toNanoOfDay());
        }
    }

    public void addToTotalMountainPoints(int mountainPoints) {
        totalMountainPoints += mountainPoints;
    }

    public void addToTotalPoints(int points) {
        totalPoints += points;
    }

    public LocalTime getTotalRaceAdjustedElapsedTime() {
        return totalRaceAdjustedElapsedTime;
    }

    public void resetTotals() {
        totalRaceAdjustedElapsedTime = null;
        totalPoints = 0;
        totalMountainPoints = 0;
    }

    @Override
    public int getId() {
        return rider.getId();
    }

    public int getTotalMountainPoints() {
        return totalMountainPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
