package cycling;

import java.time.LocalDateTime;
import java.util.Arrays;

public class CyclingPortalGetStageCheckpointsTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        int stage1Id = portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 15.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.HIGH_MOUNTAIN);


        int[] checkpoints = portal1.getStageCheckpoints(stage1Id);
        System.out.println("the checkpoints in stage " + Arrays.toString(checkpoints));



        try {
            int nonExistentStageId = -1;
            portal1.getStageCheckpoints(nonExistentStageId);
            System.err.println("Stage " + nonExistentStageId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Stage in the system");
        }
    }
}

