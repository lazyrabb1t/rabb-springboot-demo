package xyz.lazyrabbit.mapper;

import org.springframework.stereotype.Repository;
import xyz.lazyrabbit.entity.AccountInfoType;

import java.util.List;

@Repository
public interface AccountInfoTypeMapper {

    List<AccountInfoType> findAll();

    Integer insert(AccountInfoType accountInfoType);

    Integer updateById(AccountInfoType accountInfoType);

    Integer deleteById(Integer id);
}
