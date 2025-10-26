package cycling;

import java.time.LocalDateTime;

public class CyclingPortalAddStageToRaceTest {
    public static void main(String[] args) throws Exception {

        CyclingPortal portal1 = new CyclingPortalImpl();

        String name = "Race1";
        int Race1Id = portal1.createRace(name, "desc1");
        System.out.println("race " + Race1Id + " created");

        portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 8.5,
                LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);


        try {
            int NonExistentRaceId = -1;
            portal1.addStageToRace(NonExistentRaceId, "Flat", "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("Stage " + NonExistentRaceId + " should not be created");
        } catch (IDNotRecognisedException e) {
            System.out.println("the ID does not match to any race in the system");
        }

        try {
            portal1.addStageToRace(Race1Id, "Flat", "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("Stage " + name + " should not be created");
        } catch (IllegalNameException e) {
            System.out.println("Exception expected on Stage creation with the same name");
        }

        //Test If the name is null
        try {
            portal1.addStageToRace(Race1Id, null, "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("Stage with null name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on Stage creation null name");
        }

        //Test If the name is empty
        try {
            portal1.addStageToRace(Race1Id, "", "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("Stage with empty name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on stage creation empty name");
        }

        //Test If the name has more than 30 characters
        try {
            portal1.addStageToRace(Race1Id, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("stage with ore than 30 characters name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on stage creation ore than 30 characters name");
        }

        //Test If the name  has white spaces
        try {
            portal1.addStageToRace(Race1Id, "Fl  at", "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("Stage with spaced name should not be created");
        } catch (InvalidNameException e) {
            System.out.println("Exception expected on stage creation spaced name");
        }

        try {
            portal1.addStageToRace(Race1Id, "Mountain", "Opening stage", 4,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            System.err.println("Stage with spaced name should not be created");
        } catch (InvalidLengthException e) {
            System.out.println("Exception expected on stage a length more than 5km");
        }
    }
}
