package cycling;

import java.time.LocalDateTime;


public class CyclingPortalGetStageLengthTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 8.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);

        double stagelength = portal1.getStageLength(stage1Id);
        System.out.println("the stage length of stage "+ stage1Id + " is " + stagelength + "Km");

        try {
            int nonExistentStageId = -1;
            portal1.getStageLength(nonExistentStageId);
            System.err.println("Stage " + nonExistentStageId+" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Stage in the system");
        }

    }
}

