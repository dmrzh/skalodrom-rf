package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 */
@Entity
public class Scalodrom implements PersistentEntity<Long>{
    public Scalodrom() {
    }
    public Scalodrom(String name) {
        this.name = name;
    }

    public Scalodrom(String description, String name) {
        this.description = description;
        this.name = name;
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }
    @Id @GeneratedValue
    private Long id;

    @NotNull @Size(min = 2, max = 100)
    private String name;

    @Size(min = 5, max = 1000)
    private String description;

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
}
