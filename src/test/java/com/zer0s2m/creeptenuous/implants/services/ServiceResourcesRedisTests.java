package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.CreepTenuousImplantsApplication;
import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.redis.models.DirectoryRedis;
import com.zer0s2m.creeptenuous.implants.redis.models.FileRedis;
import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceResourcesRedisImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {
        ServiceResourcesRedisImpl.class,
        DirectoryRedisRepository.class,
        FileRedisRepository.class
})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ContextConfiguration(classes = { CreepTenuousImplantsApplication.class })
public class ServiceResourcesRedisTests {

    @Autowired
    private ServiceResourcesRedisImpl serviceResourcesRedis;

    @Test
    public void getResourceDirectoryRedis_success() {
        Assertions.assertDoesNotThrow(
                () -> serviceResourcesRedis.getResourceDirectoryRedis(new ArrayList<>())
        );
    }

    @Test
    public void getResourceFileRedis_success() {
        Assertions.assertDoesNotThrow(
                () -> serviceResourcesRedis.getResourceFileRedis(new ArrayList<>())
        );
    }

    @Test
    public void getUnusedObjectFileSystem_success() {
        final String systemNameDirectory = "test_folder_1";
        final String systemNameFile = "test_file_1";

        final Path pathDirectory = Path.of(systemNameDirectory);
        ContainerInfoFileSystemObject containerDirectory = new ContainerInfoFileSystemObject(
                pathDirectory, systemNameDirectory, false, true);
        ContainerInfoFileSystemObject containerFile = new ContainerInfoFileSystemObject(
                Path.of(systemNameFile), systemNameFile, true, false);

        DirectoryRedis directoryRedis = new DirectoryRedis();
        directoryRedis.setPath(pathDirectory.toString());
        directoryRedis.setSystemName(systemNameDirectory);

        FileRedis fileRedis = new FileRedis();
        fileRedis.setPath(pathDirectory.toString());
        fileRedis.setSystemName(systemNameFile + "not_found");

        List<ContainerInfoFileSystemObject> unusedAttached = serviceResourcesRedis.getUnusedObjectFileSystem(
                List.of(directoryRedis), List.of(fileRedis), List.of(containerDirectory, containerFile));

        Assertions.assertEquals(1, unusedAttached.size());
    }

}
