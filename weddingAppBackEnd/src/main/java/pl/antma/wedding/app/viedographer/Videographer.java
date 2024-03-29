package pl.antma.wedding.app.viedographer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Videographer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "cameraman")
    private boolean cameraman;

    @Column(name = "photographer")
    private boolean photographer;

    @Column(name = "chosen")
    private boolean chosen;

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isCameraman() {
        return cameraman;
    }

    public void setCameraman(boolean cameraman) {
        this.cameraman = cameraman;
    }

    public boolean isPhotographer() {
        return photographer;
    }

    public void setPhotographer(boolean photographer) {
        this.photographer = photographer;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
