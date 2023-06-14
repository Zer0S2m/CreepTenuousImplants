package com.zer0s2m.creeptenuous.implants.services;

import java.util.List;

/**
 * Interface for implementing cleaning radishes from dead objects
 */
public interface ServiceRedisClean {

    /**
     * Run redis cleanup
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     */
    void clean(List<String> ids);

}
