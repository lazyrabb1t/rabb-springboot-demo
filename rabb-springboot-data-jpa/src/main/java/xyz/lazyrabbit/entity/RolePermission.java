package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_role_permission")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
