package pl.antma.wedding.app.guest;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Guest {

    @Id
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "side")
    private String side;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSide() {
        return side;
    }

    public String getUsername() {
        return username;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Guest guest = (Guest) obj;
        return Objects.equals(username, guest.username);
    }
}
