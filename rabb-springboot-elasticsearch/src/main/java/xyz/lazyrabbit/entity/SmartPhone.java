package xyz.lazyrabbit.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(indexName = "smart_phone")
public class SmartPhone {

    @Id
    private String id;

    private String type;

    private Double price;

    private Date saleTime;
}
