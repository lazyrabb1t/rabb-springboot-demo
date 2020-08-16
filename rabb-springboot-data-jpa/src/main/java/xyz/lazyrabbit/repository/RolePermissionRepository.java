package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.RolePermission;

public interface RolePermissionRepository extends CrudRepository<RolePermission, Integer> {

}
