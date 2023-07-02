package com.zer0s2m.creeptenuous.implants.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Helpers for generating tokens by private/public keys
 */
public class UtilsToken {

    static final String TITLE_PRIVATE_KEY = "integration-main-system.pem";

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static String generateToken(PrivateKey privateKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("integration", "main");

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.RS512, privateKey)
                .compact();
    }

    public PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = Files.readString(
                Path.of(resourceLoader.getResource("keys/" + TITLE_PRIVATE_KEY).getURI()),
                Charset.defaultCharset());
        key = cleanKeyFromFile(key);
        byte[] encoded = Base64.decodeBase64(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * clear key from file
     * @param rawKey raw key from file
     * @return clean key
     */
    private @NotNull String cleanKeyFromFile(@NotNull String rawKey) {
        return rawKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
    }

}
