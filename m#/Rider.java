 

import java.io.Serializable;

public class Rider extends WithId implements Serializable {
    private static int UNIQUE_ID = 0;
    private final int id;
    private final String name;
    private final int yearOfBirth;

    public Rider(String name, int yearOfBirth) {
        id = UNIQUE_ID++;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

}
