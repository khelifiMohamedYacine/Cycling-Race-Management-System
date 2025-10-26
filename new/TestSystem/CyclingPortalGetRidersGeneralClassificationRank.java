package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class CyclingPortalGetRidersGeneralClassificationRank {
    public static void main(String[] args) throws Exception  {

        CyclingPortal portal = new CyclingPortalImpl();
        String name1 = "team1";
        String name = "Race1";

        int Race1Id = portal.createRace(name, "desc1");
        System.out.println("Race " + Race1Id + " created");

        int stage1Id = portal.addStageToRace(Race1Id, "Mountain", "First regular stage", 150.5,
                LocalDateTime.of(2024, 7, 2, 12, 0), StageType.MEDIUM_MOUNTAIN);

        int team1Id = portal.createTeam(name1, "desc1");
        int rider1Id = portal.createRider(team1Id, "Rider 1", 1990);
        int rider2Id = portal.createRider(team1Id, "Rider 2", 1995);
        int rider3Id = portal.createRider(team1Id, "Rider 3", 1994);
        int rider4Id = portal.createRider(team1Id, "Rider 4", 1999);
        int rider5Id = portal.createRider(team1Id, "Rider 5", 1991);

        System.out.println("Team " + team1Id + " created");
        System.out.println("Rider " + rider1Id + " created");
        System.out.println("Rider " + rider2Id + " created");
        System.out.println("Rider " + rider3Id + " created");
        System.out.println("Rider " + rider4Id + " created");
        System.out.println("Rider " + rider5Id + " created");


        // Add other checkpoints
        portal.addCategorizedClimbToStage(stage1Id, 100.0, CheckpointType.HC, 8.0, 5.0);
        int sprintCheckpointId = portal.addIntermediateSprintToStage(stage1Id, 50.0);
        System.out.println("Sprint checkpoint ID: " + sprintCheckpointId);

        // Get checkpoint IDs for the stage
        int[] checkpointIds = portal.getStageCheckpoints(stage1Id);
        if (checkpointIds != null) {
            System.out.println("\nCheckpoint IDs for Stage 1:");
            for (int checkpointId : checkpointIds) {
                System.out.println(checkpointId);
            }
        }

        // Conclude the preparation of the stage
        portal.concludeStagePreparation(stage1Id);

        // Register rider results with proper number of checkpoint times
        LocalTime[] checkpointTimes1 = {LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(11, 0)};
        LocalTime[] checkpointTimes2 = {LocalTime.of(12, 5), LocalTime.of(13, 5), LocalTime.of(14, 5), LocalTime.of(11, 5)};
        LocalTime[] checkpointTimes3 = {LocalTime.of(12, 10), LocalTime.of(13, 10), LocalTime.of(14, 10), LocalTime.of(11, 10)};
        LocalTime[] checkpointTimes4 = {LocalTime.of(12, 15), LocalTime.of(13, 15), LocalTime.of(14, 15), LocalTime.of(11, 15)};
        LocalTime[] checkpointTimes5 = {LocalTime.of(12, 20), LocalTime.of(13, 20), LocalTime.of(14, 20), LocalTime.of(11, 20)};

        portal.registerRiderResultsInStage(stage1Id, rider1Id, checkpointTimes1);
        portal.registerRiderResultsInStage(stage1Id, rider2Id, checkpointTimes2);
        portal.registerRiderResultsInStage(stage1Id, rider3Id, checkpointTimes3);
        portal.registerRiderResultsInStage(stage1Id, rider4Id, checkpointTimes4);
        portal.registerRiderResultsInStage(stage1Id, rider5Id, checkpointTimes5);

        System.out.println("Rider results registered successfully for Stage " + stage1Id);

        LocalTime[] rider1Results = portal.getRiderResultsInStage(stage1Id, rider1Id);
        LocalTime[] rider2Results = portal.getRiderResultsInStage(stage1Id, rider2Id);
        LocalTime[] rider3Results = portal.getRiderResultsInStage(stage1Id, rider3Id);
        LocalTime[] rider4Results = portal.getRiderResultsInStage(stage1Id, rider4Id);
        LocalTime[] rider5Results = portal.getRiderResultsInStage(stage1Id, rider5Id);

        System.out.println("\nRider 1 Results in Stage 1:");
        System.out.println(Arrays.toString(rider1Results));
        System.out.println("\nRider 2 Results in Stage 1:");
        System.out.println(Arrays.toString(rider2Results));
        System.out.println("\nRider 3 Results in Stage 1:");
        System.out.println(Arrays.toString(rider3Results));
        System.out.println("\nRider 4 Results in Stage 1:");
        System.out.println(Arrays.toString(rider4Results));
        System.out.println("\nRider 5 Results in Stage 1:");
        System.out.println(Arrays.toString(rider5Results));

        int[] ridersRankInStage = portal.getRidersRankInStage(stage1Id);
        System.out.println("\nRiders' Finished Positions in Stage 1:");
        System.out.println(Arrays.toString(ridersRankInStage));

        int[] RidersMountainPointsInStage = portal.getRidersMountainPointsInStage(stage1Id);
        System.out.println("\nRiders Mountain Points In Stage in Stage 1:");
        System.out.println(Arrays.toString(RidersMountainPointsInStage));

        int[] RidersGeneralClassificationRank =  portal.getRidersGeneralClassificationRank(Race1Id);
        System.out.println("\nRiders General Classification Rank:");
        System.out.println(Arrays.toString(RidersGeneralClassificationRank));

        try {
            int nonExistentRaceId = -1;
            portal.getRidersGeneralClassificationRank(nonExistentRaceId);
            System.err.println("Race " + nonExistentRaceId  +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match any Race in the system.");
        }

    }
}
