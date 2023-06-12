package com.zer0s2m.creeptenuous.implants.redis.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DirectoryRedisTests {

    @Test
    public void equals_success() {
        DirectoryRedis directoryRedis1 = new DirectoryRedis();
        DirectoryRedis directoryRedis2 = new DirectoryRedis();

        directoryRedis1.setSystemName("systemName");
        directoryRedis2.setSystemName("systemName");
        directoryRedis1.setPath("path");
        directoryRedis2.setPath("path");

        Assertions.assertTrue(directoryRedis1.equals(directoryRedis2));
    }

    @Test
    public void hashCode_success() {
        DirectoryRedis fileRedis = new DirectoryRedis();
        Assertions.assertInstanceOf(Integer.class, fileRedis.hashCode());
    }

    @Test
    public void toString_success() {
        DirectoryRedis fileRedis = new DirectoryRedis();
        Assertions.assertInstanceOf(String.class, fileRedis.toString());
    }
    
}
