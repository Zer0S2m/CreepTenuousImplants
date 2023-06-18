package com.zer0s2m.creeptenuous.implants.redis.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JwtRedisTests {

    @Test
    public void equals_success() {
        JwtRedis jwtRedis1 = new JwtRedis();
        JwtRedis jwtRedis2 = new JwtRedis();

        jwtRedis1.setLogin("login");
        jwtRedis2.setLogin("login");

        Assertions.assertTrue(jwtRedis1.equals(jwtRedis2));
    }

    @Test
    public void hashCode_success() {
        JwtRedis jwtRedis = new JwtRedis();
        Assertions.assertInstanceOf(Integer.class, jwtRedis.hashCode());
    }

    @Test
    public void toString_success() {
        JwtRedis jwtRedis = new JwtRedis();
        Assertions.assertInstanceOf(String.class, jwtRedis.toString());
    }
    
}
