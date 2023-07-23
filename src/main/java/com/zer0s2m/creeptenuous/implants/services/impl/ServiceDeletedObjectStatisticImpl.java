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

    @Override
    public void createDeletedObjectStatisticRedis() {

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
