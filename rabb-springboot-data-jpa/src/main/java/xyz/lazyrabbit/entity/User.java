package xyz.lazyrabbit.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userInfoId", referencedColumnName = "id")
    private UserInfo userInfo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private UserType userType;

    @ManyToOne(cascade = {})
    @JoinTable(name = "t_user_role",
            joinColumns = @JoinColumn(name="userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userInfo=" + userInfo +
                ", userType=" + userType +
                '}';
    }
}
