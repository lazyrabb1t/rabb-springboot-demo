package xyz.lazyrabbit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permissions {
    private Integer id;
    private String permissionsName;
}
