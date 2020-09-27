package xyz.lazyrabbit.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Document(indexName = "smart_phone")
public class SmartPhone {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Text)
    private String type;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Text)
    private String desc;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time_no_millis)
    private LocalDateTime saleTime;
}
