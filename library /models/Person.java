package library.models;

public abstract class Person {
    protected String id;
    protected String name;
    protected String email;

    // Default constructor
    Person() {
        this.id = "P000";
        this.name = "Default Name";
        this.email = "default@email.com";
    }

    // Parameterized constructor
    Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter functions
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    // Setter functions
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Display method
    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
}
