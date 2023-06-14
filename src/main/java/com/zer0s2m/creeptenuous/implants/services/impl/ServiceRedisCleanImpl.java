package com.zer0s2m.creeptenuous.implants.services.impl;

import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.ServiceRedisClean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for cleaning radishes from dead objects
 */
@Service("service-clean-redis")
public class ServiceRedisCleanImpl implements ServiceRedisClean {

    private final DirectoryRedisRepository directoryRedisRepository;

    private final FileRedisRepository fileRedisRepository;

    @Autowired
    public ServiceRedisCleanImpl(DirectoryRedisRepository directoryRedisRepository,
                                 FileRedisRepository fileRedisRepository) {
        this.directoryRedisRepository = directoryRedisRepository;
        this.fileRedisRepository = fileRedisRepository;
    }

    /**
     * Run redis cleanup
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     */
    public void clean(List<String> ids) {
        directoryRedisRepository.deleteAllById(ids);
        fileRedisRepository.deleteAllById(ids);
    }

}
