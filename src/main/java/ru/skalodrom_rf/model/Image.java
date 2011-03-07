package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**.*/
@Entity
public class Image  implements PersistentEntity<Long> {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private byte[] imageData = new byte[0];

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
