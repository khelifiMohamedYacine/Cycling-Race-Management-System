package cycling;


public class CyclingPortalRemoveTeamTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "team1";
        int team1Id = portal1.createTeam(name, "desc1");
        System.out.println("Team " + team1Id + " created");

        portal1.removeTeam(team1Id);
        System.out.println("the teamId " + team1Id + "was removed");

        try {
            int nonExistentteamId = -1;
            portal1.removeTeam(nonExistentteamId);
            System.err.println("Team " + nonExistentteamId +" should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any Team in the system");
        }
    }


}
