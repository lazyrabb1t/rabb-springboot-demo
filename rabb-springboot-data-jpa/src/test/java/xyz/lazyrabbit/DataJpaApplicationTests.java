package xyz.lazyrabbit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import xyz.lazyrabbit.entity.*;
import xyz.lazyrabbit.repository.*;

import java.util.List;

@SpringBootTest
@Slf4j
class DataJpaApplicationTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RolePermissionRepository rolePermissionRepository;

    /**
     * 一对一测试
     */
    @Test
    void testOnetoOne() {

        UserInfo userInfo = new UserInfo();
        userInfo.setName("张三");
        userInfo.setAge(18);

        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setUserInfo(userInfo);

        userRepository.save(user);

        List<User> userList = userRepository.findByUsername("zhangsan");
        Assert.notNull(userList, "获取记录失败");
        System.out.println(userList.get(0));
    }

    /**
     * 一对多测试
     */
    @Test
    void testOnetoMany() {

        UserType userType = new UserType();
        userType.setTypeName("VIP PLUS");

        User user = new User();
        user.setUsername("lisi");
        user.setPassword("123456");
        user.setUserType(userType);

        userRepository.save(user);


        List<User> userList = userRepository.findByUsername("lisi");
        Assert.notNull(userList, "获取记录失败");
        System.out.println(userList.get(0));
    }


    /**
     * 一对多测试2
     */
    @Test
    void testOnetoMany2() {

        User user = new User();
        user.setUsername("wangwu");
        user.setPassword("123456");
        userRepository.save(user);
        User user2 = new User();
        user2.setUsername("chenshi");
        user2.setPassword("123456");
        userRepository.save(user2);

        Role role = new Role();
        role.setRole("系统管理员");
        roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(user.getId());
        userRoleRepository.save(userRole);

        UserRole userRole2 = new UserRole();
        userRole2.setRoleId(role.getId());
        userRole2.setUserId(user2.getId());
        userRoleRepository.save(userRole2);


        Iterable<Role> all = roleRepository.findAll();
        Assert.notNull(all, "获取记录失败");
        System.out.println(all.iterator().next());
    }

    /**
     * 多对多测试
     */
    @Test
    void testManytoMany() {
        Role role1 = new Role();
        role1.setRole("账户管理员");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setRole("日志管理员");
        roleRepository.save(role2);
        Role role3 = new Role();
        role3.setRole("审批管理员");
        roleRepository.save(role3);

        Permission permission1 = new Permission();
        permission1.setPermission("/user/info");
        permissionRepository.save(permission1);
        Permission permission2 = new Permission();
        permission2.setPermission("/user/list");
        permissionRepository.save(permission2);

        RolePermission rolePermission1 = new RolePermission();
        rolePermission1.setPermissionId(permission1.getId());
        rolePermission1.setRoleId(role1.getId());
        rolePermissionRepository.save(rolePermission1);
        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setPermissionId(permission1.getId());
        rolePermission2.setRoleId(role2.getId());
        rolePermissionRepository.save(rolePermission2);
        RolePermission rolePermission3 = new RolePermission();
        rolePermission3.setPermissionId(permission1.getId());
        rolePermission3.setRoleId(role3.getId());
        rolePermissionRepository.save(rolePermission3);
        RolePermission rolePermission4 = new RolePermission();
        rolePermission4.setPermissionId(permission2.getId());
        rolePermission4.setRoleId(role1.getId());
        rolePermissionRepository.save(rolePermission4);
        RolePermission rolePermission5 = new RolePermission();
        rolePermission5.setPermissionId(permission2.getId());
        rolePermission5.setRoleId(role2.getId());
        rolePermissionRepository.save(rolePermission5);
        RolePermission rolePermission6 = new RolePermission();
        rolePermission6.setPermissionId(permission2.getId());
        rolePermission6.setRoleId(role3.getId());
        rolePermissionRepository.save(rolePermission6);

        Iterable<Role> all = roleRepository.findAll();
        Assert.notNull(all, "获取记录失败");
        all.forEach(e -> {
            System.out.println(e);
        });

    }
}
