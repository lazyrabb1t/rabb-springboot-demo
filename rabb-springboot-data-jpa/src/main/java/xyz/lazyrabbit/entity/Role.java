package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String role;
    private String memo;


//    @OneToMany(cascade = {}, targetEntity = User.class, fetch = FetchType.EAGER, mappedBy = "role")
//    @JoinTable(name = "t_user_role",
//            joinColumns = @JoinColumn(name="roleId", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
//    private List<User> userList;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_permission", joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private List<Permission> permissionList;
}
