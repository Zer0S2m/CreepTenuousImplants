package com.zer0s2m.creeptenuous.implants.redis.models;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Part of the hash for Redis from the main system
 */
@Data
@RedisHash("directories")
public class DirectoryRedis implements BaseRedis {

    @Id
    @Column(name = "systemNameDirectory")
    private String systemName;

    @Column(name = "pathDirectory")
    private String path;

}
