package pl.antma.wedding.app.mass.function;

import pl.antma.wedding.app.guest.Guest;

import javax.persistence.*;
import java.util.Set;

@Entity
public class MassFunction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "guest_id")
    @OneToMany
    private Set<Guest> guestId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Guest> getGuestId() {
        return guestId;
    }

    public void setGuestId(Set<Guest> guestId) {
        this.guestId = guestId;
    }
}
