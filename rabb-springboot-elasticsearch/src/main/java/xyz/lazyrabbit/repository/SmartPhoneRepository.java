package xyz.lazyrabbit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import xyz.lazyrabbit.entity.SmartPhone;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SmartPhoneRepository extends ElasticsearchRepository<SmartPhone, String> {

    List<SmartPhone> findBySaleTimeBetween(LocalDateTime start,LocalDateTime end);

    @Query("{\"match\": {\"type\": {\"query\": \"?0\"}}}")
    Page<SmartPhone> findByType(String type, Pageable pageable);
}
