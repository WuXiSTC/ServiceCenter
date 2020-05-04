package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface DatabaseRepository extends CassandraRepository<Test, String> {

    Optional<Test> findByID(UUID ID);

    Iterable<Test> findAllByUSER(String User);
}
