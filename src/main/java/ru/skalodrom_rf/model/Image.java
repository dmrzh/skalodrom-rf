package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**.*/
@Entity
public class Image  implements PersistentEntity<Long> {
    private Long id;
    @Lob
    private byte[] imageData;

    public Image() {
    }
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
