package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class CyclingPortalDeleteRiderResultsInStageTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal = new CyclingPortalImpl();
        String name1 = "team1";
        String name = "Race1";

        int Race1Id = portal.createRace(name, "desc1");
        System.out.println("Race " + Race1Id + " created");

        int stage1Id = portal.addStageToRace(Race1Id, "Mountain", "First regular stage", 150.5,
                LocalDateTime.of(2024, 7, 2, 12, 0), StageType.MEDIUM_MOUNTAIN);

        int team1Id = portal.createTeam(name1, "desc1");
        int rider1Id = portal.createRider(team1Id, "Rider 1", 1990);
        System.out.println("Team " + team1Id + " created");
        System.out.println("Rider " + rider1Id + " created");
        portal.addCategorizedClimbToStage(stage1Id, 100.0, CheckpointType.HC, 8.0, 5.0);
        int sprintCheckpointId = portal.addIntermediateSprintToStage(stage1Id, 50.0);
        System.out.println("sprintCheckpointId" + sprintCheckpointId);

        int[] checkpointIds = portal.getStageCheckpoints(stage1Id);

        if (checkpointIds != null) {
            System.out.println("\nCheckpoint IDs for Stage 1:");
            for (int checkpointId : checkpointIds) {
                System.out.println(checkpointId);
            }
        }

        portal.concludeStagePreparation(stage1Id);

        LocalTime[] checkpointTimes = {LocalTime.of(12, 0), LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(11, 0)};
        portal.registerRiderResultsInStage(stage1Id, rider1Id, checkpointTimes);
        System.out.println("Rider results registered successfully for Stage " + stage1Id);

        LocalTime[] rider1Results = portal.getRiderResultsInStage(stage1Id, rider1Id);
        System.out.println("\nRider 1 Results in Stage 1:");
        System.out.println(Arrays.toString(rider1Results));

        portal.deleteRiderResultsInStage(stage1Id, rider1Id);
        System.out.println("The Results In Stage of Rider " + rider1Id +" was removed");

        try {
            int nonExistentStageId = -1;
            portal.deleteRiderResultsInStage(nonExistentStageId , rider1Id );
            System.err.println("Stage " + nonExistentStageId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Stage in the system");
        }

        try {
            int nonExistentRiderId = -1;
            portal.deleteRiderResultsInStage(stage1Id , nonExistentRiderId );
            System.err.println("Rider " + nonExistentRiderId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Rider in the system");
        }

        try {
            int nonExistentRiderId = -1;
            int nonExistentStageId = -1;
            portal.deleteRiderResultsInStage(nonExistentStageId , nonExistentRiderId );
            System.err.println("Stage " + nonExistentRiderId +" should not be created");
            System.err.println("Rider " + nonExistentRiderId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Rider and stage in the system");
        }
    }
}
