package library.models;

import java.time.LocalDate;

public class Librarian extends Person {
    private double salary;
    private int experience; 
    private LocalDate dateOfJoining;

    // Parameterized constructor
    public Librarian(String id, String name, String email,
                     double salary, int experience, LocalDate dateOfJoining) {

        super(id, name, email);
        this.salary = salary;
        this.experience = experience;
        this.dateOfJoining = dateOfJoining;
    }

    // Default constructor
    public Librarian() {
        super("L000", "Default Librarian", "librarian@example.com");
        this.salary = 30000.0;
        this.experience = 1;
        this.dateOfJoining = LocalDate.now();
    }

    // Getters
    public double getSalary() { return salary; }
    public int getExperience() { return experience; }
    public LocalDate getDateOfJoining() { return dateOfJoining; }

    // Setters
    public void setSalary(double salary) { this.salary = salary; }
    public void setExperience(int experience) { this.experience = experience; }
    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    // Display method
    public void display() {
        System.out.println("Librarian ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Salary: ₹" + salary);
        System.out.println("Experience: " + experience + " years");
        System.out.println("Date of Joining: " + dateOfJoining);
    }
}
