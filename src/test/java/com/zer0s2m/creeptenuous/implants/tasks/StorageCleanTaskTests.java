package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.CreepTenuousImplantsApplication;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceResourcesRedisImpl;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceStorageCleanImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest(classes = {
        ServiceResourcesRedisImpl.class,
        ServiceStorageCleanImpl.class,
        RootPath.class,
        DirectoryRedisRepository.class,
        FileRedisRepository.class
})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ContextConfiguration(classes = { CreepTenuousImplantsApplication.class })
public class StorageCleanTaskTests {

    @Autowired
    private RootPath rootPath;

    private Path root;

    @Autowired
    private StorageCleanTask storageCleanTask;

    @BeforeEach
    public void init() {
        root = rootPath.getRootPath();
    }

    @Test
    public void storageCleanTask_success() throws IOException {
        final String directoryNameTest = "testDirectory";
        final String fileNameTest = "testFile.txt";

        Path directoryTest = Files.createDirectory(Path.of(root.toString(), directoryNameTest));
        Path testFile = Files.createFile(Path.of(directoryTest.toString(), fileNameTest));

        Assertions.assertDoesNotThrow(
                () -> storageCleanTask.storageCleanTask());

        Assertions.assertFalse(Files.exists(directoryTest));
        Assertions.assertFalse(Files.exists(testFile));
    }

}
