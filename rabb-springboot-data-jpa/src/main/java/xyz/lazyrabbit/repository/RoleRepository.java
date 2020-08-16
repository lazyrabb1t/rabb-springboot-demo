package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
