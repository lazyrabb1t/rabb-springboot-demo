package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.UserType;

public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
}
