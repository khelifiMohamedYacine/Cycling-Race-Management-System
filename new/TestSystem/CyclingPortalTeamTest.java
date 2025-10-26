package cycling;

import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.IllegalNameException;
import cycling.InvalidNameException;

public class CyclingPortalTeamTest {
    public static void main(String[] args) throws Exception {
        CyclingPortal cyclingPortal = new CyclingPortalImpl();
        String name = "team1";
        int team1Id = cyclingPortal.createTeam(name, "desc1");
        System.out.println("Team " + team1Id + " created");

        //Test If the name already exists in the platform
        try {
            cyclingPortal.createTeam(name, "desc1");
            System.err.println("Team " + name +" should not be created");
        } catch (IllegalNameException e) {
            System.out.println("Exception expected on team creation with the same name");
        }

        //Test If the name is null
        try {
            cyclingPortal.createTeam(null, "desc1");
            System.err.println("Team with null name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on team creation null name");
        }

        //Test If the name is empty
        try {
            cyclingPortal.createTeam("", "desc1");
            System.err.println("Team with empty name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on team creation empty name");
        }

        //Test If the name has more than 30 characters
        try {
            cyclingPortal.createTeam("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "desc1");
            System.err.println("Team with ore than 30 characters name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on team creation ore than 30 characters name");
        }

        //Test If the name  has white spaces
        try {
            cyclingPortal.createTeam("na me", "desc1");
            System.err.println("Team with spaced name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on team creation spaced name");
        }
    }
}
