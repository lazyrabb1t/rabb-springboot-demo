package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {

}
