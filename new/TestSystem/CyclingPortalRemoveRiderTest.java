package cycling;

import java.time.LocalDateTime;

public class CyclingPortalRemoveRiderTest {
    public static void main(String[] args) throws Exception {
        CyclingPortal portal = new CyclingPortalImpl();
        String name = "team1";
        int team1Id = portal.createTeam(name, "desc1");
        int rider1Id = portal.createRider(team1Id,"Rider 1", 1990);
        System.out.println("Team " + team1Id + " created");
        System.out.println("Rider " + rider1Id + " created");

        portal.removeRider(rider1Id);
        System.out.println("rider " + rider1Id + " was removed ");


        try {
            int nonExistentRiderId = -1;
            portal.removeRider(nonExistentRiderId);
            System.err.println("Rider " + nonExistentRiderId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Rider in the system");
        }


    }


}

