package cycling;

public class CyclingPortalviewRaceDetailsTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        String RaceDetails = portal1.viewRaceDetails(Race1Id);
        System.out.println("race " + RaceDetails + " created");

        try {
            int nonExistentRaceId = -1;
            String details = portal1.viewRaceDetails(nonExistentRaceId);
            System.err.println("Race " + nonExistentRaceId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any race in the system");
        }
    }
}
