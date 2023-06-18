package com.zer0s2m.creeptenuous.implants.redis.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RightsUserRedisTests {

    @Test
    public void equals_success() {
        RightsUserRedis rightsUserRedis1 = new RightsUserRedis();
        RightsUserRedis rightsUserRedis2 = new RightsUserRedis();

        rightsUserRedis1.setSystemName("systemName");
        rightsUserRedis2.setSystemName("systemName");

        Assertions.assertTrue(rightsUserRedis1.equals(rightsUserRedis2));
    }

    @Test
    public void hashCode_success() {
        RightsUserRedis rightsUserRedis = new RightsUserRedis();
        Assertions.assertInstanceOf(Integer.class, rightsUserRedis.hashCode());
    }

    @Test
    public void toString_success() {
        RightsUserRedis rightsUserRedis = new RightsUserRedis();
        Assertions.assertInstanceOf(String.class, rightsUserRedis.toString());
    }

}
