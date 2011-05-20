package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * .
 */
@Entity
public class SysProperties implements PersistentEntity<String> {
    @Id
    private String key;
    private String value;

    @Override
    public String getPrimaryKey() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
