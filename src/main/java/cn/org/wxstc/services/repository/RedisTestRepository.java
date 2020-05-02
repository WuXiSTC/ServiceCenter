package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RedisTestRepository extends CrudRepository<Test, String> {
    Optional<Test> findByID(UUID ID);
}
