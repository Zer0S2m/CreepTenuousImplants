package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.RightsUserRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.ServiceRedisClean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for cleaning radishes from dead objects
 */
@Service("service-clean-redis")
public class ServiceRedisCleanImpl implements ServiceRedisClean {

    private final DirectoryRedisRepository directoryRedisRepository;

    private final FileRedisRepository fileRedisRepository;

    private final RightsUserRedisRepository rightsUserRepository;

    @Autowired
    public ServiceRedisCleanImpl(DirectoryRedisRepository directoryRedisRepository,
                                 FileRedisRepository fileRedisRepository,
                                 RightsUserRedisRepository rightsUserRepository) {
        this.directoryRedisRepository = directoryRedisRepository;
        this.fileRedisRepository = fileRedisRepository;
        this.rightsUserRepository = rightsUserRepository;
    }

    /**
     * Run redis cleanup file system object
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     */
    public void cleanFileSystemObject(Iterable<String> ids) {
        directoryRedisRepository.deleteAllById(ids);
        fileRedisRepository.deleteAllById(ids);
    }

    /**
     * Run redis cleanup rights user
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     */
    public void cleanRightsUser(Iterable<String> ids) {
        rightsUserRepository.deleteAllById(ids);
    }

}
