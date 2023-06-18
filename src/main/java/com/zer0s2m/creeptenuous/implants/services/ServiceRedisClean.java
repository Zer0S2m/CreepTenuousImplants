package com.zer0s2m.creeptenuous.implants.services;

/**
 * Interface for implementing cleaning radishes from dead objects
 */
public interface ServiceRedisClean {

    /**
     * Run redis cleanup file system object
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     */
    void cleanFileSystemObject(Iterable<String> ids);

    /**
     * Run redis cleanup rights user
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     */
    void cleanRightsUser(Iterable<String> ids);

}
