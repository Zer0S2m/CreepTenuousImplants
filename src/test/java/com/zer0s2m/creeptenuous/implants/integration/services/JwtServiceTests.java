package com.zer0s2m.creeptenuous.implants.integration.services;

import com.zer0s2m.creeptenuous.implants.helpers.UtilsToken;
import com.zer0s2m.creeptenuous.implants.integration.components.RSAKeys;
import com.zer0s2m.creeptenuous.implants.integration.services.impl.JwtServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JwtServiceTests {

    private final JwtService jwtService = new JwtServiceImpl();

    private final RSAKeys rsaKeys = new RSAKeys();

    private final UtilsToken utilsToken = new UtilsToken();

    public JwtServiceTests() throws GeneralSecurityException, IOException {
    }

    @Test
    public void verifyTokenNotKey_success() throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        String token = UtilsToken.generateToken(utilsToken.getPrivateKey());

        Assertions.assertDoesNotThrow(
                () -> jwtService.verifyToken(token));
    }

    @Test
    public void verifyTokenPublicKey_success() throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        String token = UtilsToken.generateToken(utilsToken.getPrivateKey());

        Assertions.assertDoesNotThrow(
                () -> jwtService.verifyToken(token, rsaKeys.getPublicKey()));
    }

}
