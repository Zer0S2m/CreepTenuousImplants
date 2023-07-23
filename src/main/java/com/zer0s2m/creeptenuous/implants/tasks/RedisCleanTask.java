package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.RightsUserRedis;
import com.zer0s2m.creeptenuous.implants.services.ServiceDeletedObjectStatistic;
import com.zer0s2m.creeptenuous.implants.services.ServiceRedisClean;
import com.zer0s2m.creeptenuous.implants.services.ServiceResourcesRedis;
import com.zer0s2m.creeptenuous.implants.services.WalkDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RedisCleanTask {

    private static final Logger logger = LoggerFactory.getLogger(RedisCleanTask.class);

    private final ServiceResourcesRedis serviceResourcesRedis;

    private final ServiceRedisClean serviceRedisClean;

    private final ServiceDeletedObjectStatistic serviceDeletedObjectStatistic;

    private final RootPath rootPath;

    @Autowired
    public RedisCleanTask(
            ServiceResourcesRedis serviceResourcesRedis,
            ServiceRedisClean serviceRedisClean,
            ServiceDeletedObjectStatistic serviceDeletedObjectStatistic,
            RootPath rootPath) {
        this.serviceResourcesRedis = serviceResourcesRedis;
        this.serviceRedisClean = serviceRedisClean;
        this.serviceDeletedObjectStatistic = serviceDeletedObjectStatistic;
        this.rootPath = rootPath;
    }

    @Async
    @Scheduled(cron = "${cron.schedule-clean-redis}")
    public void clean() throws IOException {
        List<ContainerInfoFileSystemObject> attached = WalkDirectory.walkDirectory(rootPath.getRootPath());
        List<String> namesFileSystemObject = WalkDirectory.getNamesFileSystemObject(attached);
        List<DirectoryRedis> directoryRedis = serviceResourcesRedis.getResourceDirectoryRedis();
        List<FileRedis> fileRedis = serviceResourcesRedis.getResourceFileRedis();
        List<RightsUserRedis> rightsUserRedis = serviceResourcesRedis.getResourceRightsUserRedis();

        logger.info("Root path [{}]", rootPath.getRootPath().toString());
        logger.info("Count user rights in redis [{}]", rightsUserRedis.size());
        logger.info("Count directories in redis [{}]", directoryRedis.size());
        logger.info("Count files in redis [{}]", fileRedis.size());
        logger.info("Count file system object [{}]", attached.size() - 1);

        List<String> idsFileSystemObject = serviceResourcesRedis.getUnusedObjectRedis(
                directoryRedis, fileRedis, namesFileSystemObject);
        List<String> idsRightsUser = serviceResourcesRedis.getUnusedRightsUser(rightsUserRedis);

        logger.info("Count unused file system object in redis [{}]", idsFileSystemObject.size());
        logger.info("Count unused user rights in redis [{}]", idsRightsUser.size());

        serviceRedisClean.cleanFileSystemObject(idsFileSystemObject);
        serviceRedisClean.cleanRightsUser(idsRightsUser);

        serviceDeletedObjectStatistic.createDeletedObjectStatisticRedisFileObject(
                serviceResourcesRedis.getUnusedObjectRedisDirectory(
                        directoryRedis, namesFileSystemObject), TypeObjectDeleted.DIRECTORY_REDIS);
        serviceDeletedObjectStatistic.createDeletedObjectStatisticRedisFileObject(
                serviceResourcesRedis.getUnusedObjectRedisFile(
                        fileRedis, namesFileSystemObject), TypeObjectDeleted.FILE_REDIS);
        serviceDeletedObjectStatistic.createDeletedObjectStatisticRedisRightUser(idsRightsUser);
    }

}
