package com.zer0s2m.creeptenuous.implants.redis.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Part of the hash for Redis from the main system
 */
@Data
@RedisHash("jwt-tokens")
public class JwtRedis {

    @Id
    private String login;

}
