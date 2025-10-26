package cycling;

import java.time.LocalDateTime;

public class CyclingPortalAddIntermediateSprintToStageTest {


    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 8.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);

        int sprintCheckpointId = portal1.addIntermediateSprintToStage(stage1Id, 5.0);
        System.out.println(sprintCheckpointId);


        try {
            portal1.addIntermediateSprintToStage(stage1Id, 50.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("the location is out of bounds of the stage length.");
        }


        try {
            portal1.addIntermediateSprintToStage(stage1Id, -1.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("the location is out of bounds of the stage length.");
        }


        try {
            int nonExistentStageId = -1;
            portal1.addIntermediateSprintToStage(nonExistentStageId, 6.0);
            System.err.println("stage " +nonExistentStageId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any stage in the system");
        }


        try {

            int stage2id = portal1.addStageToRace(Race1Id, "Flat2", "Opening stage", 15.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.TT);

            portal1.addIntermediateSprintToStage(stage2id, 50.0);
            System.err.println("Stage should not be created");
        } catch (InvalidLocationException e) {
            System.out.println("Time-trial stages cannot contain any checkpoint");
        }


    }
}
