package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsername(String username);
}
