package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**.*/
@MappedSuperclass
public class LongIdPersistenceEntity implements PersistentEntity<Long> {
        @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }
}
