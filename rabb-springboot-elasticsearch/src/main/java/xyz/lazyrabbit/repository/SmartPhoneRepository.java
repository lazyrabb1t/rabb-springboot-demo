package xyz.lazyrabbit.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import xyz.lazyrabbit.entity.SmartPhone;

@Repository
public interface SmartPhoneRepository extends ElasticsearchRepository<SmartPhone, String> {
}
