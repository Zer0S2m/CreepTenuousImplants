package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.services.impl.ServiceStorageCleanImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServiceStorageCleanTests {

    @Mock
    private RootPath rootPath;

    @InjectMocks
    private ServiceStorageCleanImpl serviceStorageClean;

    private Path root;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        root = Path.of(System.getenv("CT_ROOT_PATH"));
    }

    @Test
    public void clean_success() throws IOException {
        Mockito.when(rootPath.getRootPath()).thenReturn(root);

        final String directoryNameTest = "testDirectory";
        final String fileNameTest = "testFile.txt";

        Path directoryTest = Files.createDirectory(Path.of(root.toString(), directoryNameTest));
        Path testFile = Files.createFile(Path.of(directoryTest.toString(), fileNameTest));

        serviceStorageClean.clean(List.of(
                new ContainerInfoFileSystemObject(root, "root", false, true),
                new ContainerInfoFileSystemObject(directoryTest, directoryNameTest, false, true),
                new ContainerInfoFileSystemObject(testFile, fileNameTest, true, false)
        ));

        Assertions.assertFalse(Files.exists(directoryTest));
        Assertions.assertFalse(Files.exists(testFile));
    }

}
