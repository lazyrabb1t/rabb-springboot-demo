package xyz.lazyrabbit.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.lazyrabbit.entity.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
}
