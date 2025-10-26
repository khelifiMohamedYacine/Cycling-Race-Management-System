package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class CyclingPortalRegisterRiderResultsInStageTest {

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


        try {
            int nonExistentRiderId = -1;
            portal.registerRiderResultsInStage(stage1Id, nonExistentRiderId, checkpointTimes1);
            System.err.println("Rider " + nonExistentRiderId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match any Rider in the system.");
        }

        try {
            portal.registerRiderResultsInStage(stage1Id, rider1Id, checkpointTimes1);
            System.err.println("Rider " + rider1Id +" should not be created");
        } catch (DuplicatedResultException e) {
            System.out.println("the rider has already a result for the stage. Each rider can have only one result per stage..");
        }

        try {
            LocalTime[] checkpointTimes11 = { LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(11, 0)};
            portal.registerRiderResultsInStage(stage1Id, rider1Id, checkpointTimes11);
            System.err.println("Rider " + rider1Id +" should not be created");
        } catch (DuplicatedResultException e) {
            System.out.println("the length of checkpointTimes is not equal to n+2, where n is the number of checkpoints in the stage; +2 represents the start time and the finish time of the stage..");
        }

    }
}




