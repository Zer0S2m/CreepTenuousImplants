package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Directory scanning utilities.
 */
public interface WalkDirectory {

    /**
     * Get info directory from source path
     * @param source source system path
     * @return info attached files and directories
     * @throws IOException error system
     */
    static @NotNull List<ContainerInfoFileSystemObject> walkDirectory(Path source) throws IOException {
        List<ContainerInfoFileSystemObject> attached = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(source)) {
            paths
                    .forEach((attach) -> attached.add(new ContainerInfoFileSystemObject(
                            attach,
                            attach.getFileName().toString(),
                            Files.isRegularFile(attach),
                            Files.isDirectory(attach)
                    )));
        }
        return attached;
    }

}
