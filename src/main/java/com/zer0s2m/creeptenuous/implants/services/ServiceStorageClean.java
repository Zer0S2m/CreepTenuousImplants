package com.zer0s2m.creeptenuous.implants.services;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;

import java.util.List;

/**
 * Interface for implementing a file storage cleaning service.
 */
public interface ServiceStorageClean {

    /**
     * Run file storage cleanup
     * @param resources Information about directories to clean
     */
    void clean(List<ContainerInfoFileSystemObject> resources);

}
