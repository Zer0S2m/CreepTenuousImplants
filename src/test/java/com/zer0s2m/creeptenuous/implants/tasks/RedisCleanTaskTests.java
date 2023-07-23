package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.CreepTenuousImplantsApplication;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.JwtRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.RightsUserRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceResourcesRedisImpl;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceStorageCleanImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = {
        ServiceResourcesRedisImpl.class,
        ServiceStorageCleanImpl.class,
        RootPath.class,
        DirectoryRedisRepository.class,
        FileRedisRepository.class,
        JwtRedisRepository.class,
        RightsUserRedisRepository.class
})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ContextConfiguration(classes = { CreepTenuousImplantsApplication.class })
public class RedisCleanTaskTests {

    @Autowired
    private RedisCleanTask redisCleanTask;

    @Test
    public void redisCleanTask_success() {
        Assertions.assertDoesNotThrow(
                () -> redisCleanTask.clean()
        );
    }

}
