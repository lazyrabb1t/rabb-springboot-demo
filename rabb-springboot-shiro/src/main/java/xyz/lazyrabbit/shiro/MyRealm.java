package xyz.lazyrabbit.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import xyz.lazyrabbit.entity.Permissions;
import xyz.lazyrabbit.entity.Role;
import xyz.lazyrabbit.entity.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class MyRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色
        authorizationInfo.setRoles(getUser(username).getRoles().stream().map(
                e -> e.getRoleName()
        ).collect(Collectors.toSet()));
        // 设置权限
        authorizationInfo.setStringPermissions(
                getUser(username).getRoles().stream().flatMap(
                        e -> e.getPermissions().stream()
                ).collect(Collectors.toSet())
                        .stream().map(
                        e -> e.getPermissionsName()
                ).collect(Collectors.toSet()));

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 根据 Token 获取用户名，如果您不知道该 Token 怎么来的，先可以不管，下文会解释
        String username = (String) authenticationToken.getPrincipal();
        // 根据用户名从数据库中查询该用户
        User user = getUser(username);
        if (user != null) {
            // 把当前用户存到 Session 中
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            // 传入用户名和密码进行身份认证，并返回认证信息
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "myRealm");
            return authcInfo;
        } else {
            return null;
        }
    }

    public User getUser(String username) {
        Permissions permissions1 = new Permissions(1, "add");
        Permissions permissions2 = new Permissions(2, "edit");
        Permissions permissions3 = new Permissions(3, "delete");
        Role role1 = new Role(1, "admin", new HashSet<Permissions>(Arrays.asList(permissions2, permissions3)));
        Role role2 = new Role(2, "normal", new HashSet<Permissions>(Arrays.asList(permissions1)));
        User user1 = new User(1, "admin", "123456", new HashSet<Role>(Arrays.asList(role1)));
        User user2 = new User(2, "zhangsan", "123456", new HashSet<Role>(Arrays.asList(role2)));
        Map<String, User> map = new HashMap();
        map.put("admin", user1);
        map.put("zhangsan", user2);
        return map.get(username);
    }
}
