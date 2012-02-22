package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Profile  implements PersistentEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private User user;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @NotNull
    private EmailAddress email=new EmailAddress();
    private String fio;
    private String phone;
    @OneToOne()
    @Cascade(CascadeType.ALL)
    private Image avatar= new Image();
    private Double weight;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ClimbLevel climbLevel=ClimbLevel.UNKNOWN;
    @ManyToMany()
    //@Cascade(CascadeType.ALL)
    private Set<Skalodrom> whereClimb= new TreeSet<Skalodrom>();

    @ManyToMany()
    //@Sort(type = SortType.COMPARATOR, comparator = IdCompartor.class)
    //@Cascade(CascadeType.DELETE_ORPHAN)
    private Set<WeekDay> prefferedWeekDay=new TreeSet<WeekDay>(new IdCompartor());

    @OneToMany()
    @Cascade(CascadeType.ALL)
    private Set<ClimbTime> whenClimb=new TreeSet<ClimbTime>();

    private String about;

    @Enumerated(EnumType.STRING)
    private Sex sex=Sex.MALE;

    public Profile() {
    }

    @Override
    public Long getPrimaryKey() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public ClimbLevel getClimbLevel() {
        return climbLevel;
    }

    public void setClimbLevel(ClimbLevel climbLevel) {
        this.climbLevel = climbLevel;
    }

    public Set<ClimbTime> getWhenClimb() {
        return whenClimb;
    }

    public void setWhenClimb(Set<ClimbTime> whenClimb) {
        this.whenClimb = whenClimb;
    }

    public Set<Skalodrom> getWhereClimb() {
        return whereClimb;
    }

    public void setWhereClimb(Set<Skalodrom> whereClimb) {
        this.whereClimb = whereClimb;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<WeekDay> getPrefferedWeekDay() {
        return prefferedWeekDay;
    }

    public void setPrefferedWeekDay(Set<WeekDay> prefferedWeekDay) {
        this.prefferedWeekDay = prefferedWeekDay;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
