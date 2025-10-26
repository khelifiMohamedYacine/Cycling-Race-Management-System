 

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CyclingPortalImpl implements CyclingPortal, Serializable {

    List<Race> races = new ArrayList<>();

    List<Team> teams = new ArrayList<>();

    @Override
    public void removeRaceByName(String name) throws NameNotRecognisedException {
        Race raceToRemove = null;
        for (Race race : races) {
            if (race.getName().equals(name)) {
                raceToRemove = race;
                break;
            }
        }
        if (raceToRemove != null) {
            races.remove(raceToRemove);
        } else {
            throw new NameNotRecognisedException("No race found with name " + name);
        }
    }

    @Override
    public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getRidersGeneralClassificationRank();
    }

    @Override
    public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getGeneralClassificationTimes();
    }

    @Override
    public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getRidersPoints();
    }

    @Override
    public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getRidersMountainPoints();
    }

    @Override
    public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getRidersPointClassificationRank();
    }

    @Override
    public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getRidersMountainPointClassificationRank();
    }

    @Override
    public int[] getRaceIds() {
        return toIds(races);
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        checkNameValidity(name);
        for (Race race : races) {
            if (race.getName().equals(name)) {
                throw new IllegalNameException("A race withe name " + name + " already exits");
            }
        }
        Race race = new Race(name, description);
        races.add(race);
        return race.getId();
    }

    private static void checkNameValidity(String name) throws InvalidNameException {
        if (name == null || name.isEmpty() || name.contains(" ") || name.length() > 30) {
            throw new InvalidNameException("Invalid name " + name);
        }
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return String.format("Race id: %s, name: %s, description: %s, the number of stages: %d, and the total length: %e",
                race.getId(),
                race.getName(),
                race.getDescription(),
                race.getStages().size(),
                race.getTotalLength());
    }

    private Race getRaceById(int raceId) throws IDNotRecognisedException {
        for (Race race : races) {
            if (race.getId() == raceId) {
                return race;
            }
        }
        throw new IDNotRecognisedException("No race found with Id " + raceId);
    }

    private Stage getStageById(int stageId) throws IDNotRecognisedException {
        for (Race race : races) {
            for (Stage stage : race.getStages()) {
                if (stage.getId() == stageId) {
                    return stage;
                }
            }
        }
        throw new IDNotRecognisedException("No stage found with Id " + stageId);
    }

    private Team getTeamById(int teamId) throws IDNotRecognisedException {
        for (Team team : teams) {
            if (team.getId() == teamId) {
                return team;
            }
        }
        throw new IDNotRecognisedException("No team found with Id " + teamId);
    }

    private Rider getRiderById(int riderId) throws IDNotRecognisedException {
        for (Team team : teams) {
            for (Rider rider : team.getRiders()) {
                if (rider.getId() == riderId) {
                    return rider;
                }
            }
        }
        throw new IDNotRecognisedException("No rider found with Id " + riderId);
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        races.remove(race);
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        return race.getStages().size();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        checkNameValidity(stageName);
        if (length < 5) {
            throw new InvalidLengthException("Invalid length " + length);
        }
        for (Race race : races) {
            for (Stage stage : race.getStages()) {
                if (stage.getName().equals(stageName)) {
                    throw new IllegalNameException("A stage withe name " + stageName + " already exits");
                }
            }
        }
        Race race = getRaceById(raceId);
        Stage stage = new Stage(stageName, description, length, startTime, type, race);
        race.addStage(stage);
        return stage.getId();
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        Race race = getRaceById(raceId);
        List<Stage> raceStages = race.getStages();
        return toIds(raceStages);
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        return stage.getLength();
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        Stage stageToRemove = null;
        for (Race race : races) {
            List<Stage> raceStages = race.getStages();
            for (Stage stage : raceStages) {
                if (stage.getId() == stageId) {
                    stageToRemove = stage;
                    break;
                }
            }
            if (stageToRemove != null) {
                raceStages.remove(stageToRemove);
                return;
            }
        }
        throw new IDNotRecognisedException("No stage found with Id " + stageId);
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Stage stage = getStageById(stageId);
        if (location == null || location < 0 || location > stage.getLength()) {
            throw new InvalidLocationException("Invalid location " + location);
        }
        if (stage.isWaitingForResults()) {
            throw new InvalidStageStateException("Stage is waiting for results");
        }
        if (stage.isTimeTrial()) {
            throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint");
        }
        Checkpoint categorizedClimb = new Checkpoint(location, type, averageGradient, length);
        stage.addCheckpoint(categorizedClimb);
        return categorizedClimb.getId();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Stage stage = getStageById(stageId);
        if (location < 0 || location > stage.getLength()) {
            throw new InvalidLocationException("Invalid location " + location);
        }
        if (stage.isWaitingForResults()) {
            throw new InvalidStageStateException("Stage is waiting for results");
        }
        if (stage.isTimeTrial()) {
            throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoint");
        }
        Checkpoint intermediateSprint = new Checkpoint(location, CheckpointType.SPRINT, null, null);
        stage.addCheckpoint(intermediateSprint);
        return intermediateSprint.getId();
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        for (Race race : races) {
            for (Stage stage : race.getStages()) {
                Checkpoint checkpointToRemove = null;
                List<Checkpoint> checkpoints = stage.getCheckpoints();
                for (Checkpoint checkpoint : checkpoints) {
                    if (checkpoint.getId() == checkpointId) {
                        if (stage.isWaitingForResults()) {
                            throw new InvalidStageStateException("Stage is waiting for results");
                        }
                        checkpointToRemove = checkpoint;
                        break;
                    }
                }
                if (checkpointToRemove != null) {
                    checkpoints.remove(checkpointToRemove);
                    return;
                }
            }
        }
        throw new IDNotRecognisedException("No checkpoint found with Id " + checkpointId);
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = getStageById(stageId);
        if (stage.isWaitingForResults()) {
            throw new InvalidStageStateException("Stage is waiting for results");
        }
        stage.concludePreparation();
    }

    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        List<Checkpoint> checkpoints = stage.getCheckpoints();
        checkpoints.sort(Comparator.comparingDouble(Checkpoint::getLocation));
        return toIds(checkpoints);
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        checkNameValidity(name);
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                throw new IllegalNameException("A team withe name " + name + " already exits");
            }
        }
        Team team = new Team(name, description);
        teams.add(team);
        return team.getId();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        Team team = getTeamById(teamId);
        teams.remove(team);
    }

    @Override
    public int[] getTeams() {
        return toIds(teams);
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        Team team = getTeamById(teamId);
        List<Rider> riders = team.getRiders();
        return toIds(riders);
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Illegal rider name " + name);
        }
        if (yearOfBirth < 1900) {
            throw new IllegalArgumentException("Illegal year of birth " + yearOfBirth);
        }
        Team team = getTeamById(teamID);
        Rider rider = new Rider(name, yearOfBirth);
        team.addRider(rider);
        return rider.getId();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        Rider riderToRemove = null;
        for (Team team : teams) {
            List<Rider> teamRiders = team.getRiders();
            for (Rider rider : teamRiders) {
                if (rider.getId() == riderId) {
                    riderToRemove = rider;
                    break;
                }
            }
            if (riderToRemove != null) {
                teamRiders.remove(riderToRemove);
                removeRiderFromRaces(riderToRemove);
                return;
            }
        }
        throw new IDNotRecognisedException("No rider found with Id " + riderId);
    }

    private void removeRiderFromRaces(Rider riderToRemove) {
        for (Race race : races) {
            race.removeRider(riderToRemove);
        }
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
        Stage stage = getStageById(stageId);
        if (!stage.isWaitingForResults()) {
            throw new InvalidStageStateException("Stage is not waiting for results");
        }
        Rider rider = getRiderById(riderId);
        if (stage.hasRiderResults(rider)) {
            throw new DuplicatedResultException("rider has already a result for the stage");
        }
        int checkpointsNumber = stage.getCheckpoints().size();
        if (checkpointTimes == null || checkpointTimes.length != checkpointsNumber + 2) {
            throw new InvalidCheckpointTimesException();
        }
        stage.registerRiderResults(rider, checkpointTimes);
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        Rider rider = getRiderById(riderId);
        return stage.getRiderResultsWithElapsedTime(rider);
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        Rider rider = getRiderById(riderId);
        return stage.getRiderAdjustedElapsedTime(rider);
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        Rider rider = getRiderById(riderId);
        stage.deleteRiderResults(rider);
    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        return stage.getRidersRank();
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        return stage.getRankedAdjustedElapsedTimes();
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        return stage.computeAndGetRidersPoints();
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage stage = getStageById(stageId);
        if (!stage.isMountain()) {
            return new int[0];
        }
        return stage.computeAndGetRidersMountainPoints();
    }

    @Override
    public void eraseCyclingPortal() {
        races.clear();
        teams.clear();
    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        CyclingPortalImpl cyclingPortal = (CyclingPortalImpl) objectInputStream.readObject();
        objectInputStream.close();
        races = cyclingPortal.races;
        teams = cyclingPortal.teams;
    }
}
