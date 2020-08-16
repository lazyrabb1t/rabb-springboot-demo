package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer userId;
}
