package ru.skalodrom_rf.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/***/
@Entity
public class EmailAddress extends LongIdPersistenceEntity{
    private static final Logger LOG = LoggerFactory.getLogger(EmailAddress.class);
    static private SecureRandom prng ;
    {
        try {
            prng = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage(),e);
        }
    }


    private String address;
    private String newAdress;
    private String activationCode;    //if null - new address valid



    public String getAddress() {
        return address;
    }

    public String getNewAdress() {
        return newAdress;
    }

    public void setAddress(String address) {
       setNewAdress(address);
    }

    public void setNewAdress(String newAdress) {
        if(newAdress.equals(this.newAdress)){
            return;
        }
        this.newAdress = newAdress;
        activationCode=""+Math.abs(prng.nextInt());

    }

    /**
     * @param code activation code to confirm, must be not null.
     * @return
     */
   public boolean confirmAddress(String code){
       if(code==null){
           throw new IllegalArgumentException("code must be not null");
       }
       if(activationCode==null){
           throw new IllegalStateException("email already activated, nothing to do");
       }
       if(code.equals(activationCode)){
          activationCode=null;
          address=newAdress;
           return true;
       }
       return false;
       
   }

    public String getActivationCode() {
        return activationCode;
    }
}
