package com.junyoung.searchwheretogoapi.service.auth;

import de.rtner.security.auth.spi.SimplePBKDF2;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

    private static final SimplePBKDF2 ENCRYPT_ENGINE = new SimplePBKDF2();

    public boolean checkPassword(String encryptedPassword, String password) {
        return ENCRYPT_ENGINE.verifyKeyFormatted(encryptedPassword, password);
    }

    public String encryptPassword(String password) {
        return ENCRYPT_ENGINE.deriveKeyFormatted(password);
    }

}
