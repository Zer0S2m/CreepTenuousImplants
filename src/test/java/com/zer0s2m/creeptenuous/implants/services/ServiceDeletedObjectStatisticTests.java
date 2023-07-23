package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.CreepTenuousImplantsApplication;
import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.db.models.DeletedObjectStatistic;
import com.zer0s2m.creeptenuous.implants.db.repository.DeletedObjectStatisticRepository;
import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceDeletedObjectStatisticImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {
        ServiceDeletedObjectStatisticImpl.class,
        DeletedObjectStatisticRepository.class
})
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ContextConfiguration(classes = { CreepTenuousImplantsApplication.class })
public class ServiceDeletedObjectStatisticTests {

    @Autowired
    private ServiceDeletedObjectStatistic serviceDeletedObjectStatistic;

    @Autowired
    private DeletedObjectStatisticRepository deletedObjectStatisticRepository;

    @Test
    @Rollback
    public void createDeletedObjectStatisticRedisFileObject_success() {
        List<String> idsFileSystemObjects = new ArrayList<>();
        idsFileSystemObjects.add("systemName");

        Assertions.assertDoesNotThrow(
                () -> serviceDeletedObjectStatistic.createDeletedObjectStatisticRedisFileObject(
                        idsFileSystemObjects, TypeObjectDeleted.DIRECTORY_REDIS));
    }

    @Test
    @Rollback
    public void createDeletedObjectStatisticRedisRightUser_success() {
        List<String> idsRightsUser = new ArrayList<>();
        idsRightsUser.add("systemName");
        idsRightsUser.add("systemName__userLogin");

        Assertions.assertDoesNotThrow(
                () -> serviceDeletedObjectStatistic.createDeletedObjectStatisticRedisRightUser(
                        idsRightsUser));
    }

    @Test
    @Rollback
    public void createDeletedObjectStatisticFileStorage_success() {
        List<ContainerInfoFileSystemObject> data = new ArrayList<>();
        final Path sourcePath = Path.of("source");
        data.add(new ContainerInfoFileSystemObject(
                sourcePath,
                "systemName",
                false,
                true
        ));
        data.add(new ContainerInfoFileSystemObject(
                sourcePath,
                "systemName",
                true,
                false
        ));

        Assertions.assertDoesNotThrow(
                () -> serviceDeletedObjectStatistic.createDeletedObjectStatisticFileStorage(data));
    }

    @Test
    @Rollback
    public void getStatistics_success() {
        deletedObjectStatisticRepository.save(new DeletedObjectStatistic(
                "systemName", "systemPath", TypeObjectDeleted.DIRECTORY));

        Assertions.assertTrue(serviceDeletedObjectStatistic.getStatistics().size() >= 1);
    }

}
