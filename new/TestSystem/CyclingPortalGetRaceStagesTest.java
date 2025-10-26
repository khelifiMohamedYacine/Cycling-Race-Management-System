package cycling;

import java.time.LocalDateTime;
import java.util.Arrays;

public class CyclingPortalGetRaceStagesTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 8.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
        portal1.addStageToRace(Race1Id, "Mountain", "First regular stage", 150.5,
                LocalDateTime.of(2024, 7, 2, 12, 0), StageType.MEDIUM_MOUNTAIN);

        int[] stageIds = portal1.getRaceStages(Race1Id);
        System.out.println("the Stage Ids in " + name + " are " + Arrays.toString(stageIds));

        try {
            int nonExistentRaceId = -1;
            portal1.getNumberOfStages(nonExistentRaceId);
            System.err.println("Race " + nonExistentRaceId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any race in the system");
        }
    }
}
