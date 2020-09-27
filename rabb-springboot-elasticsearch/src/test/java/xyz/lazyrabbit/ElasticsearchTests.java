package xyz.lazyrabbit;

import lombok.extern.java.Log;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import xyz.lazyrabbit.entity.SmartPhone;
import xyz.lazyrabbit.repository.SmartPhoneRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Log
class ElasticsearchTests {

    @Autowired
    SmartPhoneRepository smartPhoneRepository;

    @Autowired
    ElasticsearchRestTemplate esRestTemplate;

    @Autowired
    RestHighLevelClient highLevelClient;

    @Test
    void initData() {
        smartPhoneRepository.deleteAll();

        SmartPhone smartPhone = new SmartPhone();
        smartPhone.setBrand("Xiaomi");
        smartPhone.setPrice(1999D);
        smartPhone.setType("K30 Utral");
        smartPhone.setSaleTime(LocalDateTime.now());

        SmartPhone smartPhone2 = new SmartPhone();
        smartPhone2.setBrand("Xiaomi");
        smartPhone2.setPrice(5499D);
        smartPhone2.setType("Mi10 Utral");
        smartPhone2.setSaleTime(LocalDateTime.now().minusHours(3));

        SmartPhone smartPhone3 = new SmartPhone();
        smartPhone3.setBrand("Xiaomi");
        smartPhone3.setPrice(4999D);
        smartPhone3.setType("Mi10 Pro");
        smartPhone3.setSaleTime(LocalDateTime.now().minusDays(5));

        SmartPhone smartPhone4 = new SmartPhone();
        smartPhone4.setBrand("Xiaomi");
        smartPhone4.setPrice(3999D);
        smartPhone4.setType("Mi10");
        smartPhone4.setSaleTime(LocalDateTime.now().minusDays(15));

        SmartPhone smartPhone5 = new SmartPhone();
        smartPhone5.setBrand("Xiaomi");
        smartPhone5.setPrice(2999D);
        smartPhone5.setType("K30 Pro");
        smartPhone5.setSaleTime(LocalDateTime.now().minusDays(20));

        SmartPhone smartPhone6 = new SmartPhone();
        smartPhone6.setBrand("Huawei");
        smartPhone6.setPrice(4999D);
        smartPhone6.setType("P40 Pro");
        smartPhone6.setSaleTime(LocalDateTime.now().minusDays(25));

        SmartPhone smartPhone7 = new SmartPhone();
        smartPhone7.setBrand("Huawei");
        smartPhone7.setPrice(6999D);
        smartPhone7.setType("Mate40 Pro");
        smartPhone7.setSaleTime(LocalDateTime.now().plusDays(15));


        smartPhoneRepository.saveAll(Arrays.asList(smartPhone, smartPhone2, smartPhone3, smartPhone4, smartPhone5,
                smartPhone6, smartPhone7));
    }

    @Test
    void repositoryTest() {

        Iterable<SmartPhone> all = smartPhoneRepository.findAll();
        System.out.println("所有数据");
        all.forEach(e -> {
            System.out.println(e.toString());
        });


        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now().minusHours(2);
        List<SmartPhone> bySaleTimeBetween = smartPhoneRepository.findBySaleTimeBetween(start, end);
        System.out.println("十天内的数据");
        bySaleTimeBetween.forEach(e -> {
            System.out.println(e.toString());
        });


        Page<SmartPhone> byType = smartPhoneRepository.findByType("MI10 Utral", PageRequest.of(0, 10));
        System.out.println("包含MI10 Utral的数据");
        byType.getContent().forEach(e -> {
            System.out.println(e.toString());
        });

    }

    @Test
    void esRestTemplateTest() {

        //简单查询
        SearchHits<SmartPhone> smartPhoneSearchHits = esRestTemplate.search(
                new NativeSearchQueryBuilder().withQuery(
                        QueryBuilders.matchQuery("type", "K30 Utral")
                ).build(),
                SmartPhone.class);
        System.out.println("matchQuery：K30 Utral");
        smartPhoneSearchHits.getSearchHits().forEach(e -> {
            System.out.println(e.getContent().toString());
        });

        smartPhoneSearchHits = esRestTemplate.search(
                new NativeSearchQueryBuilder().withQuery(
                        QueryBuilders.matchPhraseQuery("type", "K30 Utral")
                ).build(),
                SmartPhone.class);
        System.out.println("matchPhrase：K30 Utral");
        smartPhoneSearchHits.getSearchHits().forEach(e -> {
            System.out.println(e.getContent().toString());
        });

        // 聚合查询
        smartPhoneSearchHits = esRestTemplate.search(
                new NativeSearchQueryBuilder().addAggregation(
                        AggregationBuilders.avg("avg_price").field("price")).build(),
                SmartPhone.class);
        ParsedAvg parsedAvg = smartPhoneSearchHits.getAggregations().get("avg_price");
        System.out.println("平均价格");
        System.out.println(parsedAvg.getValue());

        smartPhoneSearchHits = esRestTemplate.search(
                new NativeSearchQueryBuilder().addAggregation(
                        AggregationBuilders.terms("brand_agg").field("brand")
                                .subAggregation(AggregationBuilders.avg("avg_price").field("price"))
                ).build(),
                SmartPhone.class);
        ParsedStringTerms brand_agg = smartPhoneSearchHits.getAggregations().get("brand_agg");
        //获取桶
        List<? extends Terms.Bucket> buckets = brand_agg.getBuckets();
        //遍历输出
        buckets.forEach(bucket -> {
            ParsedAvg temp = bucket.getAggregations().get("avg_price");
            System.out.println("brand：" + bucket.getKeyAsString());
            System.out.println("count：" + bucket.getDocCount());
            System.out.println("avg_price：" + temp.getValue());
        });

    }

    @Test
    void highLevelClientTest() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.queryStringQuery("K30 Utral").field("type")));
        SearchResponse search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("query_string：K30 Utral");
        System.out.println(Arrays.toString(search.getHits().getHits()));

        searchRequest = new SearchRequest();
        searchRequest.source(new SearchSourceBuilder().aggregation(AggregationBuilders.avg("avg_price").field("price")));
        search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ParsedAvg parsedAvg = search.getAggregations().get("avg_price");

        System.out.println("avg_price");
        System.out.println(parsedAvg.getValue());

        searchRequest = new SearchRequest();
        searchRequest.source(new SearchSourceBuilder().aggregation(
                AggregationBuilders.terms("brand_agg").field("brand")
                        .subAggregation(AggregationBuilders.avg("avg_price").field("price"))
        ));
        search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ParsedStringTerms brand_agg = search.getAggregations().get("brand_agg");
        //获取桶
        List<? extends Terms.Bucket> buckets = brand_agg.getBuckets();
        //遍历输出
        buckets.forEach(bucket -> {
            ParsedAvg temp = bucket.getAggregations().get("avg_price");
            System.out.println("brand：" + bucket.getKeyAsString());
            System.out.println("count：" + bucket.getDocCount());
            System.out.println("avg_price：" + temp.getValue());
        });
    }
}
