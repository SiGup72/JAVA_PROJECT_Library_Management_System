package library.models;

import java.time.LocalDate;

public class Member extends Person {
    private LocalDate joinDate;

    // Default constructor
    public Member() {
        super("M000", "Default Member", "default@example.com");
        this.joinDate = LocalDate.now();
    }

    // Parameterized constructor
    public Member(String id, String name, String email, LocalDate joinDate) {
        super(id, name, email);
        this.joinDate = joinDate;
    }

    // Getter functions
    public LocalDate getJoinDate() {
        return joinDate;
    }

    // Setter functions
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    // Display method
    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Email: " + email + " | Join Date: " + joinDate);
    }

}
