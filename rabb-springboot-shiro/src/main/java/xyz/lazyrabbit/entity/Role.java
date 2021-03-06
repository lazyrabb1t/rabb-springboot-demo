package xyz.lazyrabbit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Role {
    private Integer id;
    private String roleName;
    private Set<Permissions> permissions;
}
