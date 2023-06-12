package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.ServiceResourcesRedis;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service for collecting information about file system objects.
 * <p>All data and components are taken and must meet the requirements of the <b>main system</b>.</p>
 */
@Service("service-resource-redis")
public class ServiceResourcesRedisImpl implements ServiceResourcesRedis {

    private final DirectoryRedisRepository directoryRedisRepository;

    private final FileRedisRepository fileRedisRepository;

    @Autowired
    public ServiceResourcesRedisImpl(DirectoryRedisRepository directoryRedisRepository,
                                     FileRedisRepository fileRedisRepository) {
        this.directoryRedisRepository = directoryRedisRepository;
        this.fileRedisRepository = fileRedisRepository;
    }

    /**
     * Get all information about directories
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return information directories
     */
    public List<DirectoryRedis> getResourceDirectoryRedis(List<String> ids) {
        return getResources(directoryRedisRepository.findAllById(ids));
    }

    /**
     * Get all information about files
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return information files
     */
    public List<FileRedis> getResourceFileRedis(List<String> ids) {
        return getResources(fileRedisRepository.findAllById(ids));
    }

    /**
     * Convert iterable object to array
     * @param iterable iterable object
     * @return data array
     * @param <T> the type of elements returned by the iterator
     */
    private <T> List<T> getResources(final @NotNull Iterable<T> iterable) {
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }

    /**
     * Get unused directories by filtering from redis
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @param attached information about file system objects
     * @return filtered file system objects
     */
    public List<ContainerInfoFileSystemObject> getUnusedObjectFileSystem(
            List<DirectoryRedis> entitiesDirectories, List<FileRedis> entitiesFiles,
            @NotNull List<ContainerInfoFileSystemObject> attached) {
        List<String> systemNames = concatenateAndGetSystemNames(entitiesDirectories, entitiesFiles);

        return attached
                .stream()
                .filter(attach -> !systemNames.contains(attach.systemName()))
                .collect(Collectors.toList());
    }

    /**
     * Concatenate and get system names
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @return system names of file system objects
     */
    private @NotNull List<String> concatenateAndGetSystemNames(
            @NotNull List<DirectoryRedis> entitiesDirectories, @NotNull List<FileRedis> entitiesFiles) {
        List<String> systemNames = new ArrayList<>();

        entitiesDirectories.forEach(entity -> systemNames.add(entity.getSystemName()));
        entitiesFiles.forEach(entity -> systemNames.add(entity.getSystemName()));

        return systemNames;
    }

}
