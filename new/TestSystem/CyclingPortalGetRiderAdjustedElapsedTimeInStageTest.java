package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortalGetRiderAdjustedElapsedTimeInStageTest {

    public static void main(String[] args) throws Exception {
        CyclingPortal cyclingPortal = new CyclingPortalImpl();
        int teamId = cyclingPortal.createTeam("team", "desc");
        int rider1Id = cyclingPortal.createRider(teamId, "rider1", 2000);
        int rider2Id = cyclingPortal.createRider(teamId, "rider2", 2000);
        int rider3Id = cyclingPortal.createRider(teamId, "rider3", 2000);
        int rider4Id = cyclingPortal.createRider(teamId, "rider4", 2000);
        int raceId = cyclingPortal.createRace("race", "desc");
        LocalDateTime raceStartLocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalTime raceStartLocalTime = raceStartLocalDateTime.toLocalTime();
        int stageId = cyclingPortal.addStageToRace(raceId, "stage1", "desc", 10, raceStartLocalDateTime, StageType.FLAT);
        cyclingPortal.addIntermediateSprintToStage(stageId, 1);
        cyclingPortal.addIntermediateSprintToStage(stageId, 5);
        cyclingPortal.addIntermediateSprintToStage(stageId, 8);
        cyclingPortal.concludeStagePreparation(stageId);
        cyclingPortal.registerRiderResultsInStage(stageId, rider1Id, raceStartLocalTime, raceStartLocalTime.plusMinutes(2), raceStartLocalTime.plusMinutes(10), raceStartLocalTime.plusMinutes(16), raceStartLocalTime.plusMinutes(20));
        cyclingPortal.registerRiderResultsInStage(stageId, rider2Id, raceStartLocalTime, raceStartLocalTime.plusMinutes(1), raceStartLocalTime.plusMinutes(5), raceStartLocalTime.plusMinutes(8), raceStartLocalTime.plusMinutes(10).plusNanos(900));
        cyclingPortal.registerRiderResultsInStage(stageId, rider3Id, raceStartLocalTime, raceStartLocalTime.plusMinutes(1), raceStartLocalTime.plusMinutes(5), raceStartLocalTime.plusMinutes(8), raceStartLocalTime.plusMinutes(10));
        cyclingPortal.registerRiderResultsInStage(stageId, rider4Id, raceStartLocalTime, raceStartLocalTime.plusMinutes(1), raceStartLocalTime.plusMinutes(5), raceStartLocalTime.plusMinutes(8), raceStartLocalTime.plusMinutes(12));
        LocalTime riderAdjustedElapsedTimeInStageRider1 = cyclingPortal.getRiderAdjustedElapsedTimeInStage(stageId, rider1Id);
        LocalTime riderAdjustedElapsedTimeInStageRider2 = cyclingPortal.getRiderAdjustedElapsedTimeInStage(stageId, rider2Id);
        LocalTime riderAdjustedElapsedTimeInStageRider3 = cyclingPortal.getRiderAdjustedElapsedTimeInStage(stageId, rider3Id);
        LocalTime riderAdjustedElapsedTimeInStageRider4 = cyclingPortal.getRiderAdjustedElapsedTimeInStage(stageId, rider4Id);

        System.out.println("rider " + rider1Id + ", stage " + stageId + ", AdjustedElapsedTimeInStage " + riderAdjustedElapsedTimeInStageRider1);
        System.out.println("rider " + rider2Id + ", stage " + stageId + ", AdjustedElapsedTimeInStage " + riderAdjustedElapsedTimeInStageRider2);
        System.out.println("rider " + rider3Id + ", stage " + stageId + ", AdjustedElapsedTimeInStage " + riderAdjustedElapsedTimeInStageRider3);
        System.out.println("rider " + rider4Id + ", stage " + stageId + ", AdjustedElapsedTimeInStage " + riderAdjustedElapsedTimeInStageRider4);

    }
}
