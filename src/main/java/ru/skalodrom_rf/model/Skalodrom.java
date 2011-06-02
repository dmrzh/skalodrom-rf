package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
public class Skalodrom extends LongIdPersistenceEntity implements Comparable<LongIdPersistenceEntity>{
    public Skalodrom() {
    }
    public Skalodrom(String name) {
        this.name = name;
    }

    public Skalodrom(String description, String name) {
        this.description = description;
        this.name = name;
    }

    @Index(name ="skalodrom_name_indx")
    @NotNull @Size(min = 2, max = 100)
    @Column(unique=true)
    private String name;

    @Size(min = 5, max = 1000)
    private String description;

    @ManyToMany(mappedBy = "whereClimb")
    private Set<Profile> whoClimb=new HashSet<Profile>();

    @ManyToOne
    private City city;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Profile> getWhoClimb() {
        return whoClimb;
    }

    public void setWhoClimb(Set<Profile> whoClimb) {
        this.whoClimb = whoClimb;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int compareTo(LongIdPersistenceEntity o) {
        return getName().compareTo(((Skalodrom) o).getName());
    }
}
