package com.zer0s2m.creeptenuous.implants.redis.repository;

import com.zer0s2m.creeptenuous.implants.redis.models.RightsUserRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightsUserRedisRepository extends CrudRepository<RightsUserRedis, String> {
}
