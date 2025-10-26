package cycling;

public class CyclingPortalRemoveRaceByNameTest {
    public static void main(String[] args) throws Exception {
        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        portal1.removeRaceByName("Race1");
        System.out.println("the race named " + name + "was removed");

        try {
            portal1.removeRaceByName("name");
            System.err.println("Race " + name +" should not be created");
        } catch (NameNotRecognisedException e) {
            System.out.println("If the name does not match to any Race in the system");
        }

    }
}
