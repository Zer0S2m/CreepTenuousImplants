package com.zer0s2m.creeptenuous.implants.redis.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FileRedisTests {

    @Test
    public void equals_success() {
        FileRedis fileRedis1 = new FileRedis();
        FileRedis fileRedis2 = new FileRedis();

        fileRedis1.setSystemName("systemName");
        fileRedis2.setSystemName("systemName");
        fileRedis1.setPath("path");
        fileRedis2.setPath("path");

        Assertions.assertTrue(fileRedis1.equals(fileRedis2));
    }

    @Test
    public void hashCode_success() {
        FileRedis fileRedis = new FileRedis();
        Assertions.assertInstanceOf(Integer.class, fileRedis.hashCode());
    }

    @Test
    public void toString_success() {
        FileRedis fileRedis = new FileRedis();
        Assertions.assertInstanceOf(String.class, fileRedis.toString());
    }

}
