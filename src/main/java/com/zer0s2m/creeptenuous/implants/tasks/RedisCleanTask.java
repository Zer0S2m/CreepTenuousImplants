package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.services.ServiceResourcesRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisCleanTask {

    private static final Logger logger = LoggerFactory.getLogger(RedisCleanTask.class);

    private final ServiceResourcesRedis serviceResourcesRedis;

    @Autowired
    public RedisCleanTask(ServiceResourcesRedis serviceResourcesRedis) {
        this.serviceResourcesRedis = serviceResourcesRedis;
    }

    @Scheduled(fixedDelay = 5000)
    public void clean() {
        List<DirectoryRedis> directoryRedis = serviceResourcesRedis.getResourceDirectoryRedis();
        List<FileRedis> fileRedis = serviceResourcesRedis.getResourceFileRedis();

        logger.info("Count directories in redis [{}]", directoryRedis.size());
        logger.info("Count files in redis [{}]", fileRedis.size());
    }

}
