package cycling;

import java.time.LocalDateTime;

public class CyclingPortalRemoveStageByIdTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 8.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);

        portal1.removeStageById(stage1Id);
        System.out.println("the stage "+ stage1Id+ " was removed");


        try {
            int nonExistentStageId = -1;
            portal1.removeStageById(nonExistentStageId);
            System.err.println("Stage " + nonExistentStageId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Stage in the system");
        }

    }
}
