package ru.skalodrom_rf.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**.*/
@Entity
public class Country extends LongIdPersistenceEntity {


    @OneToMany(mappedBy = "country")
    Set<City> cities=new HashSet<City>();

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }
}
