package cycling;

import java.time.LocalDateTime;

public class CyclingPortalAddCategorizedClimbToStageTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 15.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);

        int climbCheckpointId = portal1.addCategorizedClimbToStage(stage1Id, 14.0, CheckpointType.C2, 9.0, 12.0);
        System.out.println("aaa  " + climbCheckpointId);

        try {
            portal1.addCategorizedClimbToStage(stage1Id, -1.0, CheckpointType.HC, 8.0, 5.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("the location is out of bounds of the stage length.");
        }

        try {
            portal1.addCategorizedClimbToStage(stage1Id, null, CheckpointType.HC, 8.0, 5.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("the location is out of bounds of the stage length.");
        }

        try {
            int stage2id = portal1.addStageToRace(Race1Id, "Flat1", "Opening stage", 15.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);

            portal1.addCategorizedClimbToStage(stage2id, 19.0, CheckpointType.HC, 8.0, 5.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("the location is out of bounds of the stage length.");
        }

        try {
            int nonExistentialId = -1;
            portal1.addCategorizedClimbToStage(nonExistentialId, 19.0, CheckpointType.HC, 8.0, 5.0);
            System.err.println("stage " + nonExistentialId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any stage in the system");
        }

        try {
            int stage2id = portal1.addStageToRace(Race1Id, "Flat2", "Opening stage", 15.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.TT);

            portal1.addCategorizedClimbToStage(stage2id, 19.0, CheckpointType.HC, 8.0, 5.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("Time-trial stages cannot contain any checkpoint");
        }



    }

}
