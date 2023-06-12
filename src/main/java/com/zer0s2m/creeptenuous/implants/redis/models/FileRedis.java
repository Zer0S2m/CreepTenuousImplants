package com.zer0s2m.creeptenuous.implants.redis.models;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Part of the hash for Redis from the main system
 */
@Data
@RedisHash("files")
public class FileRedis {

    @Id
    @Column(name = "systemNameFile")
    private String systemName;

    @Column(name = "pathFile")
    private String path;

}
