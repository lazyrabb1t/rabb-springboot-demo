package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "t_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String permission;
    private String memo;
    @ManyToMany(mappedBy = "permissionList", targetEntity = Role.class,fetch = FetchType.EAGER)
    private List<Role> roleList;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permission='" + permission + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
