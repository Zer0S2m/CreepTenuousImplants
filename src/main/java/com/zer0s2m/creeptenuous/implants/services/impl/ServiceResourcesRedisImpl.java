package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.redis.models.*;
import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.JwtRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.RightsUserRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.ServiceResourcesRedis;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service for collecting information about file system objects.
 * <p>All data and components are taken and must meet the requirements of the <b>main system</b>.</p>
 */
@Service("service-resource-redis")
public class ServiceResourcesRedisImpl implements ServiceResourcesRedis {

    final static String SEPARATOR_ID_RIGHTS_USER = "__";

    private final DirectoryRedisRepository directoryRedisRepository;

    private final FileRedisRepository fileRedisRepository;

    private final JwtRedisRepository jwtRedisRepository;

    private final RightsUserRedisRepository rightsUserRedisRepository;

    @Autowired
    public ServiceResourcesRedisImpl(DirectoryRedisRepository directoryRedisRepository,
                                     FileRedisRepository fileRedisRepository,
                                     JwtRedisRepository jwtRedisRepository,
                                     RightsUserRedisRepository rightsUserRedisRepository) {
        this.directoryRedisRepository = directoryRedisRepository;
        this.fileRedisRepository = fileRedisRepository;
        this.jwtRedisRepository = jwtRedisRepository;
        this.rightsUserRedisRepository = rightsUserRedisRepository;
    }

    /**
     * Get all information about directories
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return information directories
     */
    @Override
    public List<DirectoryRedis> getResourceDirectoryRedis(List<String> ids) {
        return getResources(directoryRedisRepository.findAllById(ids));
    }

    /**
     * Get all information about files
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return information files
     */
    @Override
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
     * Get all information about directories
     * @return information directories
     */
    @Override
    public List<DirectoryRedis> getResourceDirectoryRedis() {
        return getResources(directoryRedisRepository.findAll());
    }

    /**
     * Get all information about files
     * @return information files
     */
    @Override
    public List<FileRedis> getResourceFileRedis() {
        return getResources(fileRedisRepository.findAll());
    }

    /**
     * Get unused directories by filtering from redis
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @param attached information about file system objects
     * @return filtered file system objects
     */
    @Override
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

    /**
     * Get unused redis objects by filtering from object file system
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @param attached name of file system objects
     * @return filtered ids on object redis
     */
    @Override
    public List<String> getUnusedObjectRedis(
            @NotNull List<DirectoryRedis> entitiesDirectories, @NotNull List<FileRedis> entitiesFiles,
            List<String> attached) {
        List<String> ids = new ArrayList<>();

        entitiesDirectories.forEach(entity -> {
            if (entity != null) {
                if (!attached.contains(entity.getSystemName())) {
                    ids.add(entity.getSystemName());
                }
            }
        });
        entitiesFiles.forEach(entity -> {
            if (entity != null) {
                if (!attached.contains(entity.getSystemName())) {
                    ids.add(entity.getSystemName());
                }
            }
        });

        return ids;
    }

    /**
     * Get unused redis objects (Directories) by filtering from object file system
     * @param entitiesDirectories must not be {@literal null} nor must it contain {@literal null}.
     * @param attached name of file system objects
     * @return filtered ids on object redis
     */
    @Override
    public List<String> getUnusedObjectRedisDirectory(
            @NotNull List<DirectoryRedis> entitiesDirectories, List<String> attached) {
        return getUnusedObjectBaseRedis(entitiesDirectories, attached);
    }

    /**
     * Get unused redis objects (Files) by filtering from object file system
     * @param entitiesFiles must not be {@literal null} nor must it contain {@literal null}.
     * @param attached name of file system objects
     * @return filtered ids on object redis
     */
    @Override
    public List<String> getUnusedObjectRedisFile(
            List<FileRedis> entitiesFiles, List<String> attached) {
        return getUnusedObjectBaseRedis(entitiesFiles, attached);
    }

    @NotNull
    static private List<String> getUnusedObjectBaseRedis(
            @NotNull List<? extends BaseRedis> entities, List<String> attached) {
        List<String> ids = new ArrayList<>();

        entities.forEach(entity -> {
            if (entity != null) {
                if (!attached.contains(entity.getSystemName())) {
                    ids.add(entity.getSystemName());
                }
            }
        });

        return ids;
    }

    /**
     * Get all information about user rights
     * @return user rights
     */
    @Override
    public List<RightsUserRedis> getResourceRightsUserRedis() {
        return getResources(rightsUserRedisRepository.findAll());
    }

    /**
     * Get unused user right by filtering from redis
     * @param rightsUserRedis must not be {@literal null} nor must it contain {@literal null}.
     * @return filtered ids on object redis
     */
    @Override
    public List<String> getUnusedRightsUser(@NotNull List<RightsUserRedis> rightsUserRedis) {
        final List<String> ids = new ArrayList<>();

        List<String> userLogins = getResources(jwtRedisRepository.findAll())
                .stream()
                .map(JwtRedis::getLogin)
                .toList();

        List<String> directoryRedisIds = getResourceDirectoryRedis()
                .stream()
                .filter(Objects::nonNull)
                .map(DirectoryRedis::getSystemName)
                .toList();
        List<String> fileRedisIds = getResourceFileRedis()
                .stream()
                .filter(Objects::nonNull)
                .map(FileRedis::getSystemName)
                .toList();

        List<String> cleanIdsRightsUser = rightsUserRedis
                .stream()
                .filter(Objects::nonNull)
                .map(rightUser -> rightUser.getSystemName().split(SEPARATOR_ID_RIGHTS_USER)[0])
                .toList();

        cleanIdsRightsUser.forEach(idRightsUser -> {
            if (!fileRedisIds.contains(idRightsUser) && !directoryRedisIds.contains(idRightsUser)) {
                collectUnusedRightsUser(userLogins, idRightsUser, ids);
            }
        });

        return ids;
    }

    /**
     * Collect unused user id right
     * @param userLogins user logins
     * @param idRightsUser filesystem object system name
     * @param ids ids user rights object
     */
    private void collectUnusedRightsUser(@NotNull List<String> userLogins,
                                         String idRightsUser, @NotNull List<String> ids) {
        List<String> unusedRightsUser = new ArrayList<>();
        userLogins.forEach(userLogin -> unusedRightsUser.add(idRightsUser + SEPARATOR_ID_RIGHTS_USER +
                userLogin));
        ids.addAll(unusedRightsUser);
    }

}
