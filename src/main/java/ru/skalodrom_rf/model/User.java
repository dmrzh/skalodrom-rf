package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity()
@Table(name = "User")
public class User implements PersistentEntity<String>{
    public String getPrimaryKey() {
        return login;
    }

    @Id    
    private String login;


    private String password;

    private Integer activationCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile=new Profile();

    public User() {
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(Integer activationCode) {
        this.activationCode = activationCode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
