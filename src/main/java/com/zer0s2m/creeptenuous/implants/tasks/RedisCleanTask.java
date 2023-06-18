package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.services.ServiceRedisClean;
import com.zer0s2m.creeptenuous.implants.services.ServiceResourcesRedis;
import com.zer0s2m.creeptenuous.implants.services.WalkDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RedisCleanTask {

    private static final Logger logger = LoggerFactory.getLogger(RedisCleanTask.class);

    private final ServiceResourcesRedis serviceResourcesRedis;

    private final ServiceRedisClean serviceRedisClean;

    private final RootPath rootPath;

    @Autowired
    public RedisCleanTask(ServiceResourcesRedis serviceResourcesRedis, ServiceRedisClean serviceRedisClean,
                          RootPath rootPath) {
        this.serviceResourcesRedis = serviceResourcesRedis;
        this.serviceRedisClean = serviceRedisClean;
        this.rootPath = rootPath;
    }

    @Scheduled(cron = "${cron.schedule-clean-redis}")
    public void clean() throws IOException {
        List<ContainerInfoFileSystemObject> attached = WalkDirectory.walkDirectory(rootPath.getRootPath());
        List<String> namesFileSystemObject = WalkDirectory.getNamesFileSystemObject(attached);
        List<DirectoryRedis> directoryRedis = serviceResourcesRedis.getResourceDirectoryRedis();
        List<FileRedis> fileRedis = serviceResourcesRedis.getResourceFileRedis();

        logger.info("Root path [{}]", rootPath.getRootPath().toString());
        logger.info("Count directories in redis [{}]", directoryRedis.size());
        logger.info("Count files in redis [{}]", fileRedis.size());
        logger.info("Count file system object [{}]", attached.size() - 1);

        List<String> ids = serviceResourcesRedis.getUnusedObjectRedis(
                directoryRedis, fileRedis, namesFileSystemObject);

        serviceRedisClean.cleanFileSystemObject(ids);
    }

}
