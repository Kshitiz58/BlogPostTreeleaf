package com.treeleaf.blog;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JWTSecretMakerTest {

    @Test
    public void generateSecretKey() {
        SecretKey SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        String EncodedKey = Base64.getEncoder().encodeToString(SecretKey.getEncoded());
        System.out.printf("\nGenerated Secret Key: %s\n", EncodedKey);
    }
}
