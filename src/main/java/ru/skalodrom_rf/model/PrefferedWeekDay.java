package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * .
 */
@Entity
public class PrefferedWeekDay implements PersistentEntity<Long> {
    public PrefferedWeekDay() {
    }

    public PrefferedWeekDay(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private Long id;

    private String name;

    @Override
    public Long getPrimaryKey() {
        return id;
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
