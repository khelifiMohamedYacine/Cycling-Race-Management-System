package cycling;

import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.StageType;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CyclingPortalTestSaveLoad {
    public static void main(String[] args) throws Exception {
        CyclingPortal cyclingPortal = new CyclingPortalImpl();
        int team1Id = cyclingPortal.createTeam("team1", "desc1");
        int team2Id = cyclingPortal.createTeam("team2", "desc2");
        int race1Id = cyclingPortal.createRace("race1", "desc race 1");
        int rider1Id = cyclingPortal.createRider(team1Id, "rider 1", 2000);
        int stage1Id = cyclingPortal.addStageToRace(race1Id, "stage1", "desc", 10, LocalDateTime.now(), StageType.FLAT);
        cyclingPortal.addIntermediateSprintToStage(stage1Id, 7);

        String filename = Files.createTempFile("cycling", ".bin").toAbsolutePath().toString();
        System.out.println("Saving cycling portal to " + filename);
        cyclingPortal.saveCyclingPortal(filename);
        CyclingPortal loaded = new CyclingPortalImpl();
        System.out.println("Loading cycling portal from " + filename);
        loaded.loadCyclingPortal(filename);

        System.out.println(loaded.viewRaceDetails(race1Id));
        //TODO Yacine, add and check other fields


    }
}
