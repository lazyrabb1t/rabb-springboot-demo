package xyz.lazyrabbit.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.lazyrabbit.entity.AccountInfo;

import java.util.List;

@Repository
public interface AccountInfoMapper {


    @Options(useGeneratedKeys = true, keyProperty = "id")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("insert into t_account_info(username,email,password,memo) values (#{username},#{email},#{password},#{memo})")
    void insert(AccountInfo accountInfo);

    @Select("select username,email,password,memo from t_account_info where id = #{id}")
    AccountInfo findById(Integer id);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "u"),
            @Result(property = "email", column = "e"),
            @Result(property = "password", column = "p"),
            @Result(property = "memo", column = "m")
    })
    @Select("select id id,username u ,email e ,password p ,memo m from t_account_info where username like concat('%',#{name},'%')")
    List<AccountInfo> findByName(String name);

    @Select("select * from t_account_info")
    List<AccountInfo> findAll();

    @Update("update t_account_info set username=#{username},email=#{email},password=#{password},memo=#{memo} where id=#{id}")
    Integer updateById(AccountInfo accountInfo);

    @Delete("delete from t_account_info where id=#{id}")
    Integer deleteById(Integer id);
}
