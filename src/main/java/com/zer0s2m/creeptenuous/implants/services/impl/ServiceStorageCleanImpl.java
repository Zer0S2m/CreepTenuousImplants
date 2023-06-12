package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.containers.ContainerInfoFileSystemObject;
import com.zer0s2m.creeptenuous.implants.services.ServiceStorageClean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for cleaning file storage.
 * <p> All data and components are taken and must meet the requirements of the <b>main system</b></p>
 */
@Service("service-storage-clean")
public class ServiceStorageCleanImpl implements ServiceStorageClean {

    /**
     * Run file storage cleanup
     * @param resources Information about directories to clean
     */
    public void clean(List<ContainerInfoFileSystemObject> resources) {

    }

}
