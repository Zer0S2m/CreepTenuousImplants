package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;

import java.util.List;

/**
 * Basic interface for collecting statistics of deleted dead objects
 */
public interface ServiceDeletedObjectStatistic {

    void createDeletedObjectStatisticRedis();

    /**
     * Generate statistics on deleted objects of type - file objects
     * @param data data to create
     */
    void createDeletedObjectStatisticFileStorage(List<ContainerInfoFileSystemObject> data);

}
