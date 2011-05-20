package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
public class Skalodrom implements PersistentEntity<Long> , Comparable{
    public Skalodrom() {
    }
    public Skalodrom(String name) {
        this.name = name;
    }

    public Skalodrom(String description, String name) {
        this.description = description;
        this.name = name;
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }
    @Id @GeneratedValue
    private Long id;

     @Index(name ="skalodrom_name_indx")
    @NotNull @Size(min = 2, max = 100)
    private String name;

    @Size(min = 5, max = 1000)
    private String description;

    @ManyToMany(mappedBy = "whereClimb")
    private Set<Profile> whoClimb=new HashSet<Profile>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Set<Profile> getWhoClimb() {
        return whoClimb;
    }

    public void setWhoClimb(Set<Profile> whoClimb) {
        this.whoClimb = whoClimb;
    }

    @Override
    public int compareTo(Object o) {
        return getName().compareTo(((Skalodrom)o).getName());
    }
}
