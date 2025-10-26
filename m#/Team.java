 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team extends WithId implements Serializable {
    private static int UNIQUE_ID = 0;

    private final int id;

    private final String name;

    private final String description;

    private final List<Rider> riders;

    public Team(String name, String description) {
        id = UNIQUE_ID++;
        this.name = name;
        this.description = description;
        riders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Rider> getRiders() {
        return riders;
    }

    public void addRider(Rider rider) {
        riders.add(rider);
    }
}
