package cycling;

public class CyclingPortalRemoveRaceByIdTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        portal1.removeRaceById(Race1Id);
        System.out.println("race " + Race1Id + " Removed");

        try {
            int nonExistentRaceId = -1;
            portal1.removeRaceById(nonExistentRaceId);
            System.err.println("Race " + nonExistentRaceId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Race in the system");
        }
    }
}


