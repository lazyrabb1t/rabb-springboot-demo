package xyz.lazyrabbit;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lazyrabbit.entity.SmartPhone;
import xyz.lazyrabbit.repository.SmartPhoneRepository;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@RestController
public class ElasticsearchApplication {

    @Autowired
    SmartPhoneRepository smartPhoneRepository;
    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

    @GetMapping("/init")
    public Object init() {

        SmartPhone smartPhone = new SmartPhone();
        smartPhone.setPrice(1999D);
        smartPhone.setType("K30 Utral");
        smartPhone.setSaleTime(new Date());
        smartPhoneRepository.save(smartPhone);
        return "ok";
    }

    @GetMapping("/query")
    public Object query() {
        return smartPhoneRepository.findAll();
    }

    @GetMapping("/template/query")
    public Object query2() {

        return esRestTemplate.search(
                new NativeSearchQueryBuilder().withQuery(
                        QueryBuilders.matchQuery("type", "K30 Utral")
                ).build(),
                SmartPhone.class);
    }
}
