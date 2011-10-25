package ru.skalodrom_rf.model;

import javax.persistence.Entity;

/***/
@Entity
public class EmailAddress extends LongIdPersistenceEntity{
    private String currentAddress;
    private String newAdress;
    private String activationCode;

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getNewAdress() {
        return newAdress;
    }

    public void setNewAdress(String newAdress) {
        this.newAdress = newAdress;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
