package tw.com.taipower.firebasequickstart.entity;

/**
 * Created by new on 2016/4/25.
 */
public class User {
    private int birthYear;
    private String fullName;

    public User() {
    }

    public User(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public long getBirthYear() {
        return birthYear;
    }

    public String getFullName() {
        return fullName;
    }
}
