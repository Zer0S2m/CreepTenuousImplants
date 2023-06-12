package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class WalkDirectoryTests {

    private final Path root = Path.of(System.getenv("CT_ROOT_PATH"));

    @Test
    public void walkDirectory_success() throws IOException {
        Path directoryTest = Files.createDirectory(Path.of(root.toString(), "testDirectory1"));

        List<ContainerInfoFileSystemObject> attached = WalkDirectory.walkDirectory(root);
        Assertions.assertTrue(attached.size() >= 1);

        Files.delete(directoryTest);
    }

    @Test
    public void getNamesFileSystemObject_success() throws IOException {
        final String directoryNameTest = "testDirectory1";
        Path directoryTest = Files.createDirectory(Path.of(root.toString(), directoryNameTest));

        List<ContainerInfoFileSystemObject> attached = WalkDirectory.walkDirectory(root);
        Assertions.assertTrue(attached.size() >= 1);

        List<String> namesFileSystemObject = WalkDirectory.getNamesFileSystemObject(attached);

        // root path storage
        namesFileSystemObject.remove(0);

        Assertions.assertTrue(namesFileSystemObject.size() >= 1);
        Assertions.assertEquals(namesFileSystemObject.get(0), directoryNameTest);

        Files.delete(directoryTest);
    }

}
