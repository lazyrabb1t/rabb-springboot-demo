package xyz.lazyrabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.lazyrabbit.repository.UserRepository;

@SpringBootApplication
@RestController
public class DataJpaApplication {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @RequestMapping("/user/list")
    public Object list() {

        return userRepository.findAll();
    }
}
