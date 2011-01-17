package ru.skalodrom_rf.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Profile  {
    @Id  @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @OneToOne(mappedBy = "profile")
    private TUser user;

    private String email;

    public TUser getUser() {
        return user;
    }

    public void setUser(TUser user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
