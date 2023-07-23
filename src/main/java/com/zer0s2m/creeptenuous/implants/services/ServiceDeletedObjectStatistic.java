package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;

import java.util.List;

/**
 * Basic interface for collecting statistics of deleted dead objects
 */
public interface ServiceDeletedObjectStatistic {

    /**
     * Generate statistics on deleting hashes of a file object type
     * @param idsFileSystemObjects system names of file objects
     * @param typeObjectDeleted type of object to be deleted
     */
    void createDeletedObjectStatisticRedisFileObject(
            List<String> idsFileSystemObjects, TypeObjectDeleted typeObjectDeleted);

    /**
     * Generate deleted object statistics for type - user right
     * @param idsRightsUser must not be {@literal null} nor must it contain {@literal null}.
     */
    void createDeletedObjectStatisticRedisRightUser(List<String> idsRightsUser);

    /**
     * Generate statistics on deleted objects of type - file objects
     * @param data data to create
     */
    void createDeletedObjectStatisticFileStorage(List<ContainerInfoFileSystemObject> data);

}
