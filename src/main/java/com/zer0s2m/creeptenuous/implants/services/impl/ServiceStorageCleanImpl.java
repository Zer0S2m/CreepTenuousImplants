package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.services.ServiceStorageClean;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for cleaning file storage.
 * <p> All data and components are taken and must meet the requirements of the <b>main system</b></p>
 */
@Service("service-storage-clean")
public class ServiceStorageCleanImpl implements ServiceStorageClean {

    private final RootPath rootPath;

    @Autowired
    public ServiceStorageCleanImpl(RootPath rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Run file storage cleanup
     * @param resources Information about directories to clean
     */
    public void clean(@NotNull List<ContainerInfoFileSystemObject> resources) {
        List<ContainerInfoFileSystemObject> cleanResources = deleteFromListRootPathSystem(resources);
        List<ContainerInfoFileSystemObject> nonDeletedResources = new ArrayList<>();

        cleanResources.forEach(resource -> {
            try {
                Files.deleteIfExists(resource.source());
            } catch (IOException ignored) {
                nonDeletedResources.add(resource);
            }
        });

        if (nonDeletedResources.size() != 0) {
            clean(nonDeletedResources);
        }
    }

    /**
     * Deleting a file share root
     * @param resources information about file objects
     * @return filtered data
     */
    private List<ContainerInfoFileSystemObject> deleteFromListRootPathSystem(
            @NotNull List<ContainerInfoFileSystemObject> resources) {
        Path rootPath = this.rootPath.getRootPath();
        return resources
                .stream()
                .filter(resource -> !resource.source().equals(rootPath))
                .collect(Collectors.toList());
    }

}
