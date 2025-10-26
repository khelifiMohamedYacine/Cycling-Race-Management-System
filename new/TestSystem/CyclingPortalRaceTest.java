package cycling;

public class CyclingPortalRaceTest {
    public static void main(String[] args) throws Exception {
        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        //Test If the name already exists in the platform
        try {
            portal1.createRace(name, "desc1");
            System.err.println("Race " + name +" should not be created");
        } catch (IllegalNameException e) {
            System.out.println("Exception expected on Race creation with the same name");
        }

        //Test If the name is null
        try {
            portal1.createRace(null, "desc1");
            System.err.println("Race with null name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on Race creation null name");
        }

        //Test If the name is empty
        try {
            portal1.createRace("", "desc1");
            System.err.println("Race with empty name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on Race creation empty name");
        }

        //Test If the name has more than 30 characters
        try {
            portal1.createRace("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "desc1");
            System.err.println("Race with ore than 30 characters name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on Race creation ore than 30 characters name");
        }

        //Test If the name  has white spaces
        try {
            portal1.createRace("na me", "desc1");
            System.err.println("Race with spaced name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on Race creation spaced name");
        }

    }
}
