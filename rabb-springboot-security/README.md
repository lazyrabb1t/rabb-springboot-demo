# springboot集成Spring Security

参照了spring官网的一个指南，使用Spring Security框架以实现安全控制。

参照地址：https://spring.io/guides/gs/securing-web/。

同时我也使用拦截器的方式做了一个登录的校验，具体可以查看代码。


## 一、关于Spring Security

Web 应用的安全性包括用户认证（Authentication）和用户授权（Authorization）两个部分。用户认证指的是验证某个用户是否为系统中的合法主体，也就是说用户能否访问该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认证过程。用户授权指的是验证某个用户是否有权限执行某个操作。


对于上面提到的两种应用情景，Spring Security 框架都有很好的支持。在用户认证方面，Spring Security 框架支持主流的认证方式，包括 HTTP 基本认证、HTTP 表单验证、HTTP 摘要认证、OpenID 和 LDAP 等。在用户授权方面，Spring Security 提供了基于角色的访问控制和访问控制列表（Access Control List，ACL），可以对应用中的领域对象进行细粒度的控制。

## 二、使用springsecurity创建一个基础项目

这里主要参考了spring官网的一个指南，地址：https://spring.io/guides/gs/securing-web/。

首先引入相关依赖
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-test</artifactId>
	<scope>test</scope>
</dependency>

```

然后创建登录页和主页以及对应controller

login.html

```
<form class="form-signin" th:action="@{/login}" method="post">
    <div class="text-center mb-4">
        <h1 class="h3 mb-3 font-weight-normal">Welcome</h1>
    </div>

    <div class="form-label-group">
        <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputUsername">Email address</label>
    </div>

    <div class="form-label-group">
        <input type="password" id="inputPassword"  name="password"  class="form-control" placeholder="Password" required>
        <label for="inputPassword">Password</label>
    </div>
    <div class="mb-1" style="color: #ff680e">
        <p th:if="${param.error}">Invalid username and password.</p>
        <p th:if="${param.logout}"> You have been logged out.</p>
    </div>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>
```

index.html

```
<div class="container">
    <h1 class="display-3">Hello, [[${#httpServletRequest.remoteUser}]]!</h1>
    <p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of
        content. Use it as a starting point to create something more unique.</p>
    <p>
        <form th:action="@{/logout}" method="post">
            <input type="submit" value="Sign Out" class="btn btn-primary btn-lg"/>
        </form>
    </p>
</div>
```

controller

```
@GetMapping("/")
public String index() {
    return "index";
}
@GetMapping("/login")
public String login() {
    return "login";
}
```

最后进行security配置

```
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/js/**", "/css/**", "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("root")
                        .password("123456")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
```

关于Spring Security的配置主要有三个部分

- 在configure中定义了哪些路径需要进行保护，哪些不需要进行保护，同时设置了formLogin的登录方式，以及自定义的login页面，如果不设置的话会有一个默认的页面。
- userDetailsService方法创建了一个用户，用于对登录用户进行校验。
- @EnableWebSecurity用于启用 Spring Security 的 Web 安全支持。
