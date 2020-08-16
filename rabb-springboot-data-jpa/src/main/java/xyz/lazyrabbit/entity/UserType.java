package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user_type")
public class UserType {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String typeName;
    private String memo;
//    @OneToMany(mappedBy = "userType", cascade = {})
//    // 防止json序列化死循环
//    @JsonBackReference
//    private List<User> userList;
}
