package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.User;
import xyz.lazyrabbit.entity.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
}
