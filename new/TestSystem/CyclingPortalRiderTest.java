package cycling;

public class CyclingPortalRiderTest {

    public static void main(String[] args) throws Exception {
        CyclingPortal portal = new CyclingPortalImpl();
        String name = "team1";
        int team1Id = portal.createTeam(name, "desc1");
        int rider1Id = portal.createRider(team1Id,"Rider 1", 1990);
        System.out.println("Team " + team1Id + " created");
        System.out.println("Rider " + rider1Id + " created");


            //Test If the name already exists in the platform
        try {
            int nonExistentteamId = -1;
            portal.createRider(nonExistentteamId, "Rider 1", 1990);
            System.err.println("Rider " + nonExistentteamId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Rider in the system");
        }

            //Test If the name is null
        try {
            portal.createRider(team1Id, null, 1996);
            System.err.println("Rider with null name should not be created");
        } catch (IllegalArgumentException e) {
            System.out.println("Exception expected on team creation null name");
        }

            //Test If the name is empty
        try {
            portal.createRider(team1Id, "name", 1000);
            System.err.println("Rider with empty name should not be created");
        } catch (IllegalArgumentException e) {
            System.out.println("Exception expected on rider creation have his year of birth is less than 1900 ");
        }

            //Test If the name has more than 30 characters
        try {
            portal.createRider(team1Id, "",1999);
            System.err.println("Team with ore than 30 characters name should not be created");
        } catch (IllegalArgumentException e) {
            System.out.println("Exception expected on rider creation empty name");
        }
    }


}
