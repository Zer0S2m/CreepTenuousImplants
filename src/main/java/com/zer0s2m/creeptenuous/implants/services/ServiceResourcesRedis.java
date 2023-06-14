package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;

import java.util.List;

/**
 * Interface for implementing a service for collecting information about file system objects
 */
public interface ServiceResourcesRedis {

    /**
     * Get all information about directories
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return information directories
     */
    List<DirectoryRedis> getResourceDirectoryRedis(List<String> ids);

    /**
     * Get all information about files
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return information files
     */
    List<FileRedis> getResourceFileRedis(List<String> ids);

    /**
     * Get all information about directories
     * @return information directories
     */
    List<DirectoryRedis> getResourceDirectoryRedis();

    /**
     * Get all information about files
     * @return information files
     */
    List<FileRedis> getResourceFileRedis();

    /**
     * Get unused directories by filtering from redis
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @param attached information about file system objects
     * @return filtered file system objects
     */
    List<ContainerInfoFileSystemObject> getUnusedObjectFileSystem(
            List<DirectoryRedis> entitiesDirectories, List<FileRedis> entitiesFiles,
            List<ContainerInfoFileSystemObject> attached);

}
