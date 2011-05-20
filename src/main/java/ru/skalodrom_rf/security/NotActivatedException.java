package ru.skalodrom_rf.security;

import org.springframework.security.authentication.AccountStatusException;

/**.*/
public class NotActivatedException extends AccountStatusException {
    public NotActivatedException(String msg) {
        super(msg);
    }
}
