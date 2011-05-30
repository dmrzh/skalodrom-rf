package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**.*/
@Entity
public class ClimbTime implements PersistentEntity<Long> ,Comparable<ClimbTime> , Serializable{

    public ClimbTime() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate date=new LocalDate();
    @Enumerated(EnumType.STRING)
    private Time time=Time.EVENING;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status=Status.OPEN;

    private String emailCode;



    @Override
    public Long getPrimaryKey() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((ClimbTime)obj)==0;
    }

    @Override
    public int hashCode() {
        return date.hashCode()+31*time.hashCode();
    }

    @Override
    public int compareTo(ClimbTime  o) {
        if(date.compareTo(o.getDate())==0){
            return time.compareTo(o.getTime());
        }
        return date.compareTo(o.getDate());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
}
