package cycling;

import java.time.LocalDateTime;

public class CyclingPortalConcludeStagePreparationTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 15.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);

        portal1.concludeStagePreparation(stage1Id);

        try {
            int nonExistentialId = -1;
            portal1.concludeStagePreparation(nonExistentialId);
            System.err.println("stage " + nonExistentialId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any stage in the system");
        }
    }
}
