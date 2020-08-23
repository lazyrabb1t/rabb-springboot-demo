package xyz.lazyrabbit.entity;

import lombok.Data;

@Data
public class AccountInfo {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private String memo;
}
