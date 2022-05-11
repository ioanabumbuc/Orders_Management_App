package model;

/**
 * Client class defines a client. Its attributes are: id, name, email, phoneNb.
 * Contains a constructor and getters.
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String phoneNb;

    public Client(int id, String name, String email, String phoneNb) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNb = phoneNb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNb() {
        return phoneNb;
    }
}
