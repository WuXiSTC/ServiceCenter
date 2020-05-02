package cn.org.wxstc.services.repository;

import cn.org.wxstc.services.entity.Test;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

public interface TestRepository extends CassandraRepository<Test, String> {

}
