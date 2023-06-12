package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.services.ServiceResourcesRedis;
import com.zer0s2m.creeptenuous.implants.services.ServiceStorageClean;
import com.zer0s2m.creeptenuous.implants.services.WalkDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StorageCleanTask {

    private static final Logger logger = LoggerFactory.getLogger(StorageCleanTask.class);

    private final ServiceStorageClean serviceStorageClean;

    private final ServiceResourcesRedis serviceResourcesRedis;

    private final RootPath rootPath;

    @Autowired
    public StorageCleanTask(ServiceStorageClean serviceStorageClean, ServiceResourcesRedis serviceResourcesRedis,
                            RootPath rootPath) {
        this.serviceStorageClean = serviceStorageClean;
        this.serviceResourcesRedis = serviceResourcesRedis;
        this.rootPath = rootPath;
    }

    @Scheduled(fixedRate = 7000)
    private void storageCleanTask() throws IOException {
        List<ContainerInfoFileSystemObject> attached = WalkDirectory.walkDirectory(rootPath.getRootPath());
        List<String> namesFileSystemObject = WalkDirectory.getNamesFileSystemObject(attached);
        List<DirectoryRedis> directoryRedis = serviceResourcesRedis.getResourceDirectoryRedis(namesFileSystemObject);
        List<FileRedis> fileRedis = serviceResourcesRedis.getResourceFileRedis(namesFileSystemObject);
        List<ContainerInfoFileSystemObject> unusedAttached = serviceResourcesRedis.getUnusedObjectFileSystem(
                directoryRedis, fileRedis, attached);

        logger.info("Root path [{}]", rootPath.getRootPath().toString());
        logger.info("Count directories in redis [{}]", directoryRedis.size());
        logger.info("Count files in redis [{}]", fileRedis.size());
        logger.info("Count file system object [{}]", namesFileSystemObject.size() - 1);
        logger.info("Count unused file system object [{}]", unusedAttached.size() - 1);

        serviceStorageClean.clean(unusedAttached);
    }

}
