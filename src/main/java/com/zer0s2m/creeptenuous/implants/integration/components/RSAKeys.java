package com.zer0s2m.creeptenuous.implants.integration.components;

import org.apache.tomcat.util.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Component for storing private and public keys
 */
public class RSAKeys {

    static final public String TITLE_PUBLIC_KEY = "integration-main-system.pub";

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    private final PublicKey publicKey;

    public RSAKeys() throws GeneralSecurityException, IOException {
        this.publicKey = getPublicKeyFromFile();
    }

    /**
     * Get public key from file
     * @return public key
     * @throws GeneralSecurityException error parse
     * @throws IOException I/O error
     */
    private PublicKey getPublicKeyFromFile() throws GeneralSecurityException, IOException {
        String key = Files.readString(
                Path.of(resourceLoader.getResource("keys/" + TITLE_PUBLIC_KEY).getURI()),
                Charset.defaultCharset());
        key = cleanKeyFromFile(key);

        byte[] encoded = Base64.decodeBase64(key);

        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encoded);
        return factory.generatePublic(pubKeySpec);
    }

    /**
     * clear key from file
     * @param rawKey raw key from file
     * @return clean key
     */
    private @NotNull String cleanKeyFromFile(@NotNull String rawKey) {
        return rawKey
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
