package ru.skalodrom_rf.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**.*/
@Entity
public class City extends LongIdPersistenceEntity{

    @OneToMany(mappedBy = "city")
    private Set<Skalodrom> skalodroms=new HashSet<Skalodrom>();
    @ManyToOne
    private Country country;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skalodrom> getSkalodroms() {
        return skalodroms;
    }

    public void setSkalodroms(Set<Skalodrom> skalodroms) {
        this.skalodroms = skalodroms;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
