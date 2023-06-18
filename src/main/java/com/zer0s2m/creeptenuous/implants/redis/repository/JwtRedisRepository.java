package com.zer0s2m.creeptenuous.implants.redis.repository;

import com.zer0s2m.creeptenuous.implants.redis.models.JwtRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRedisRepository extends CrudRepository<JwtRedis, String> {
}
