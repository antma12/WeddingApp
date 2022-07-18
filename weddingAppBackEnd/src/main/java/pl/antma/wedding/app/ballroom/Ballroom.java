package pl.antma.wedding.app.ballroom;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Ballroom {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "availabledates")
    @ElementCollection
    private Set<LocalDate> availableDates;

    @Column(name = "price")
    private BigInteger price;

    //TODO: seperate object NightStay?
    @Column(name = "nightstay")
    private boolean isWithNightStay;

    @Column(name = "pricepernight")
    private BigInteger pricePerNight;

    @Column(name = "opinion")
    private String opinion;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Set<LocalDate> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(Set<LocalDate> availableDates) {
        this.availableDates = availableDates;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public boolean getIsWithNightStay() {
        return isWithNightStay;
    }

    public void setIsWithNightStay(boolean isWithNightStay) {
        this.isWithNightStay = isWithNightStay;
    }

    public BigInteger getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigInteger pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
