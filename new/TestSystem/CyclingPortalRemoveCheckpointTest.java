package cycling;

import java.time.LocalDateTime;

public class CyclingPortalRemoveCheckpointTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 108.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);


        int climbCheckpointId = portal1.addCategorizedClimbToStage(stage1Id, 100.0, CheckpointType.HC, 8.0, 5.0);

        portal1.removeCheckpoint(climbCheckpointId);
        System.out.println("the Checkpoint " + climbCheckpointId + " was removed");

        try {
            int nonExistentcheckpointId = -1;
            portal1.removeStageById(nonExistentcheckpointId);
            System.err.println("checkpoint " + nonExistentcheckpointId + " should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any checkpoint in the system");
        }

    }
}
