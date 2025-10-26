package cycling;

import java.util.Arrays;

public class CyclingPortalGetTeamRidersTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "team1";
        int team1Id = portal1.createTeam(name, "desc1");
        System.out.println("Team " + team1Id + " created");

        portal1.createRider(team1Id, "Rider 1", 1990);
        portal1.createRider(team1Id, "Rider 2", 1985);

        int [] ridersinteam = portal1.getTeamRiders(team1Id);
        System.out.println("the riders in th team are "+ Arrays.toString(ridersinteam));


        try {
            int nonExistentteamId = -1;
            portal1.getTeamRiders(nonExistentteamId);
            System.err.println("Team " + nonExistentteamId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Team in the system");
        }


    }
}
