package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user_info")
public class UserInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
//    @OneToOne(cascade = {},mappedBy = "userInfo")
//    private User user;
}
