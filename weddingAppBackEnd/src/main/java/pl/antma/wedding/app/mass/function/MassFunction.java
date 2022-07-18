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
    @ManyToMany
    private Set<Guest> guests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }
}
