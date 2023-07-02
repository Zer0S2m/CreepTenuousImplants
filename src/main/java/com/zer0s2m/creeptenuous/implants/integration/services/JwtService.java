package com.zer0s2m.creeptenuous.implants.integration.services;

import io.jsonwebtoken.Claims;

import java.security.PublicKey;

/**
 * Interface for implementing jwt token authentication
 */
public interface JwtService {

    /**
     * Authenticate jwt token using public key
     * @param token access JWT token
     * @param publicKey public key
     * @return claims conveyed by the JWT
     */
    Claims verifyToken(String token, PublicKey publicKey);

    /**
     * Authenticate jwt token using public key
     * @param token access JWT token
     * @return claims conveyed by the JWT
     */
    Claims verifyToken(String token);

}
