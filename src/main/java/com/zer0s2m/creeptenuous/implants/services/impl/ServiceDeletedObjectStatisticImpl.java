package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.db.models.DeletedObjectStatistic;
import com.zer0s2m.creeptenuous.implants.db.repository.DeletedObjectStatisticRepository;
import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;
import com.zer0s2m.creeptenuous.implants.services.ServiceDeletedObjectStatistic;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Basic service for collecting statistics of deleted dead objects
 */
@Service("service-deleted-object-statistic")
public class ServiceDeletedObjectStatisticImpl implements ServiceDeletedObjectStatistic {

    private final DeletedObjectStatisticRepository deletedObjectStatisticRepository;

    @Autowired
    public ServiceDeletedObjectStatisticImpl(
            DeletedObjectStatisticRepository deletedObjectStatisticRepository) {
        this.deletedObjectStatisticRepository = deletedObjectStatisticRepository;
    }

    /**
     * Generate statistics on deleting hashes of a file object type
     * @param idsFileSystemObjects system names of file objects
     * @param typeObjectDeleted type of object to be deleted
     */
    @Override
    public void createDeletedObjectStatisticRedisFileObject(
            @NotNull List<String> idsFileSystemObjects, TypeObjectDeleted typeObjectDeleted) {
        Collection<DeletedObjectStatistic> deletedObjectStatistics = new ArrayList<>();

        idsFileSystemObjects.forEach(obj -> {
            DeletedObjectStatistic newObj = new DeletedObjectStatistic();
            newObj.setSystemName(obj);
            newObj.setTypeObject(typeObjectDeleted);
            deletedObjectStatistics.add(newObj);
        });

        deletedObjectStatisticRepository.saveAll(deletedObjectStatistics);
    }

    /**
     * Generate deleted object statistics for type - user right
     * @param idsRightsUser must not be {@literal null} nor must it contain {@literal null}.
     */
    @Override
    public void createDeletedObjectStatisticRedisRightUser(@NotNull List<String> idsRightsUser) {
        Collection<DeletedObjectStatistic> deletedObjectStatistics = new ArrayList<>();

        idsRightsUser.forEach(id -> {
            String[] splitId = id.split("__");
            DeletedObjectStatistic newObj;

            if (splitId.length > 1) {
                newObj = new DeletedObjectStatistic(
                        splitId[1], TypeObjectDeleted.RIGHT_USER);
                newObj.setSystemName(splitId[0]);
            } else {
                newObj = new DeletedObjectStatistic();
                newObj.setSystemName(splitId[0]);
                newObj.setTypeObject(TypeObjectDeleted.RIGHT_USER);
            }

            deletedObjectStatistics.add(newObj);
        });

        deletedObjectStatisticRepository.saveAll(deletedObjectStatistics);
    }

    /**
     * Generate statistics on deleted objects of type - file objects
     * @param data data to create
     */
    @Override
    public void createDeletedObjectStatisticFileStorage(@NotNull List<ContainerInfoFileSystemObject> data) {
        Collection<DeletedObjectStatistic> deletedObjectStatistics = new ArrayList<>();

        data.forEach(obj -> deletedObjectStatistics.add(new DeletedObjectStatistic(
                obj.systemName(),
                obj.source().toString(),
                obj.isDirectory() ? TypeObjectDeleted.DIRECTORY : TypeObjectDeleted.FILE
        )));

        deletedObjectStatisticRepository.saveAll(deletedObjectStatistics);
    }

}
