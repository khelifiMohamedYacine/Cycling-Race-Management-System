package cycling;



import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;


public class testcomplet {

    public static void main(String[] args) {
        CyclingPortal portal1 = new CyclingPortalImpl();


        try {
            // Create races
            int race3Id = portal1.createRace("Race1", "Description1");
            int race2Id = portal1.createRace("Race2", "Description2");

            // View race details
            System.out.println("Race 1 details:");
            System.out.println(portal1.viewRaceDetails(race3Id));

            System.out.println("\nRace 2 details:");
            System.out.println(portal1.viewRaceDetails(race2Id));

            // Get race IDs
            System.out.println("\nAll Race IDs:");
            int[] raceIds = portal1.getRaceIds();
            if (raceIds != null) {
                for (int raceId : raceIds) {
                    System.out.println(raceId);
                }
            } else {
                System.out.println("Race IDs array is null");
            }

            // Remove a race
            System.out.println("\nRemoving Race 1...");
            portal1.removeRaceById(race3Id);

            int race1Id = portal1.createRace("Race1", "Description1");

            int stage1Id = portal1.addStageToRace(race1Id, "Flat", "Opening stage", 8.5,
                    LocalDateTime.of(2024, 7, 1, 12, 0), StageType.FLAT);
            int stage2Id = portal1.addStageToRace(race2Id, "Mountain", "First regular stage", 150.5,
                    LocalDateTime.of(2024, 7, 2, 12, 0), StageType.MEDIUM_MOUNTAIN);

            System.out.println("\nStage IDs for the race:");
            System.out.println(stage2Id);
            System.out.println(stage1Id);

            // Get the number of stages for the race
            int numberOfStages = portal1.getNumberOfStages(race1Id);
            System.out.println("Number of stages for the race: " + numberOfStages);

            // Get the list of stage IDs for the race
            System.out.println("\nAll stage IDs:");

            int[] stageIds = portal1.getRaceStages(race1Id);
            if (stageIds != null) {
                for (int stageId : stageIds) {
                    System.out.println(stageId);
                }
            } else {
                System.out.println("Race IDs array is null");
            }


            // Get the length of a stage
            double stage1Length = portal1.getStageLength(stage1Id);
            System.out.println("\nLength of Stage 1: " + stage1Length + " km");


            // Remove a stage
            System.out.println("\nRemoving Stage 1...");
            portal1.removeStageById(stage1Id);

            // Get the updated number of stages for the race
            numberOfStages = portal1.getNumberOfStages(race1Id);
            System.out.println("Number of stages after removal: " + numberOfStages);

            //Add categorized climb to the stage
            int climbCheckpointId = portal1.addCategorizedClimbToStage(stage2Id, 100.0, CheckpointType.HC, 8.0, 5.0);
            System.out.println(climbCheckpointId);

            // Add intermediate sprint to the stage
            int sprintCheckpointId = portal1.addIntermediateSprintToStage(stage2Id, 50.0);
            System.out.println(sprintCheckpointId);

            // Get the list of checkpoint IDs for the stage
            int[] checkpointIds = portal1.getStageCheckpoints(stage2Id);
            System.out.println("Checkpoint IDs for Stage 1:");

            if (checkpointIds != null) {
                System.out.println("\nCheckpoint IDs for Stage 1:");
                for (int checkpointId : checkpointIds) {
                    System.out.println(checkpointId);
                }
            }

            // Conclude the preparation of the stage
            portal1.concludeStagePreparation(stage2Id);


            portal1.removeCheckpoint(climbCheckpointId);

            int team1Id = portal1.createTeam("TeamA", "Description for Team A");
            int team2Id = portal1.createTeam("TeamB", "Description for Team B");

            System.out.println("ids of 1 and 2");
            System.out.println(team1Id);
            System.out.println(team2Id);

            // Get the list of team IDs
            int[] teamIds = portal1.getTeams();
            System.out.println("Team IDs in the system:");

            if (teamIds != null) {
                for (int teamId : teamIds) {
                    System.out.println(teamId);
                }
            } else {
                System.out.println("Race IDs array is null");
            }

            // Add riders to teams
            int rider1Id = portal1.createRider(team1Id, "Rider 1", 1990);
            int rider2Id = portal1.createRider(team2Id, "Rider 2", 1985);

            portal1.getTeamRiders(team1Id);
            portal1.getTeamRiders(team2Id);

            // Get the riders of a team
            int[] team1Riders = portal1.getTeamRiders(team1Id);
            System.out.println("\nRidersId in Team A:");
            System.out.println(rider1Id);

            if (team1Riders != null) {
                System.out.println("\nCheckpoint IDs for Stage 1:");
                for (int riderId : team1Riders) {
                    System.out.println(riderId);
                }
            }

            int[] team2Riders = portal1.getTeamRiders(team2Id);
            System.out.println("\nRiders in Team B:");
            System.out.println(rider2Id);

            if (team2Riders != null) {
                System.out.println("\nCheckpoint IDs for Stage 1:");
                for (int riderId : team2Riders) {
                    System.out.println(riderId);
                }
            }
            // Remove a team
            System.out.println("\nRemoving Team A...");
            portal1.removeTeam(team1Id);

            int[] removedTeamRiders = portal1.getTeamRiders(team2Id);

            portal1.registerRiderResultsInStage(stage2Id, rider2Id, LocalTime.of(8, 0), LocalTime.of(10, 0), LocalTime.of(12, 0));

            // Get rider results in the stage
            LocalTime[] rider1Results = portal1.getRiderResultsInStage(stage2Id, rider2Id);
            System.out.println("\nRider 2 Results in Stage 1:");
            System.out.println(Arrays.toString(rider1Results));

            // Get riders' finished positions in the stage
            int[] ridersRankInStage = portal1.getRidersRankInStage(stage2Id);
            System.out.println("\nRiders' Finished Positions in Stage 1:");
            System.out.println(Arrays.toString(ridersRankInStage));

            // Get adjusted elapsed times of riders in the stage
            LocalTime[] rankedAdjustedElapsedTimes = portal1.getRankedAdjustedElapsedTimesInStage(stage2Id);
            System.out.println("\nRanked Adjusted Elapsed Times in Stage 1:");
            System.out.println(Arrays.toString(rankedAdjustedElapsedTimes));


            int[] RidersPointsInStage = portal1.getRidersPointsInStage(stage2Id);
            System.out.println("\nRiders Points In Stage in Stage 1:");
            System.out.println(Arrays.toString(RidersPointsInStage));


            int[] RidersMountainPointsInStage = portal1.getRidersMountainPointsInStage(stage2Id);
            System.out.println("\nRiders Mountain Points In Stage in Stage 1:");
            System.out.println(Arrays.toString(RidersMountainPointsInStage));


            int[] RidersGeneralClassificationRank =  portal1.getRidersGeneralClassificationRank(race2Id);
            System.out.println("\nRiders General Classification Rank:");
            System.out.println(Arrays.toString(RidersGeneralClassificationRank));


            LocalTime[] GeneralClassificationTimesInRace = portal1.getGeneralClassificationTimesInRace(race2Id);
            System.out.println("\nGeneral Classification Times In Race:");
            System.out.println(Arrays.toString(GeneralClassificationTimesInRace));


            int[] RidersPointsInRace = portal1.getRidersPointsInRace(race2Id);
            System.out.println("\nRiders Points In Race:");
            System.out.println(Arrays.toString(RidersPointsInRace));


            int[] RidersMountainPointsInRace = portal1.getRidersMountainPointsInRace(race2Id);
            System.out.println("\nRiders Mountain Points In Race");
            System.out.println(Arrays.toString(RidersMountainPointsInRace));


            int[] RidersPointClassificationRank = portal1.getRidersPointClassificationRank(race2Id);
            System.out.println("\nRiders Point Classification Rank");
            System.out.println(Arrays.toString(RidersPointClassificationRank));

            int[] RidersMountainPointClassificationRank = portal1.getRidersMountainPointClassificationRank(race2Id);
            System.out.println("\nRiders Mountain Point Classification Rank");
            System.out.println(Arrays.toString(RidersMountainPointClassificationRank));



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
