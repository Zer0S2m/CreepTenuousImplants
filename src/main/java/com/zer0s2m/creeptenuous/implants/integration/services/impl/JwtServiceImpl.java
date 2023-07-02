package com.zer0s2m.creeptenuous.implants.integration.services.impl;

import com.zer0s2m.creeptenuous.implants.integration.components.RSAKeys;
import com.zer0s2m.creeptenuous.implants.integration.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

/**
 * Service for authenticating jwt tokens
 */
@Service("jwt-service")
public class JwtServiceImpl implements JwtService {

    private final RSAKeys rsaKeys = new RSAKeys();

    public JwtServiceImpl() throws GeneralSecurityException, IOException {
    }

    /**
     * Authenticate jwt token using public key
     * @param token access JWT token
     * @param publicKey public key
     * @return claims conveyed by the JWT
     */
    @Override
    public Claims verifyToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    /**
     * Authenticate jwt token using public key
     * @param token access JWT token
     * @return claims conveyed by the JWT
     */
    @Override
    public Claims verifyToken(String token) {
        return verifyToken(token, rsaKeys.getPublicKey());
    }

}
