package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.RightsUserRedis;

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

    /**
     * Get unused redis objects by filtering from object file system
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @param attached name of file system objects
     * @return filtered ids on object redis
     */
    List<String> getUnusedObjectRedis(
            List<DirectoryRedis> entitiesDirectories, List<FileRedis> entitiesFiles, List<String> attached);

    /**
     * Get all information about user rights
     * @return user rights
     */
    List<RightsUserRedis> getResourceRightsUserRedis();

    /**
     * Get unused user right by filtering from redis
     * @param rightsUserRedis must not be {@literal null} nor must it contain {@literal null}.
     * @return filtered ids on object redis
     */
    List<String> getUnusedRightsUser(List<RightsUserRedis> rightsUserRedis);

}
