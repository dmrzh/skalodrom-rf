package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**.*/
@MappedSuperclass
public class LongIdPersistenceEntity implements PersistentEntity<Long> ,Comparable<LongIdPersistenceEntity>{
        @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public LongIdPersistenceEntity(Long id) {
        this.id = id;
    }

    public LongIdPersistenceEntity() {
    }

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
      @Override
    public int compareTo(LongIdPersistenceEntity o) {
        if(o.id==null || id== null) {
            return 0;
        }
        return id.compareTo(o.getId());
    }

}
