package xyz.lazyrabbit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.lazyrabbit.entity.AccountInfo;
import xyz.lazyrabbit.entity.AccountInfoType;
import xyz.lazyrabbit.mapper.AccountInfoMapper;
import xyz.lazyrabbit.mapper.AccountInfoTypeMapper;

@SpringBootTest
@Slf4j
class MybatisApplicationTests {

    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Autowired
    AccountInfoTypeMapper accountInfoTypeMapper;
    @Test
    void testAnnotation() {

        log.info("插入2条数据到数据库");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUsername("巴黎圣日尔曼");
        accountInfo.setEmail("admin@lazyrabbit.xyz");
        accountInfo.setPassword("123456");
        accountInfo.setMemo("手机");
        accountInfoMapper.insert(accountInfo);
        AccountInfo accountInfo2 = new AccountInfo();
        accountInfo2.setUsername("拜仁慕尼黑");
        accountInfo2.setEmail("admin@lazyrabbit.xyz");
        accountInfo2.setPassword("123456");
        accountInfo2.setMemo("手机");
        accountInfoMapper.insert(accountInfo2);


        log.info("当前数据库一共有数据：{}条", accountInfoMapper.findAll().size());

        Integer id = accountInfo.getId();
        AccountInfo findById = accountInfoMapper.findById(id);
        log.info("根据ID{}查询数据：{}", id, findById);
        AccountInfo findByName = accountInfoMapper.findByName("拜仁慕尼黑").get(0);
        log.info("根据Name查询数据：{}", accountInfoMapper.findByName("拜仁慕尼黑"));
        findByName.setMemo("今天赌五毛辣条拜仁大胜！！！");
        accountInfoMapper.updateById(findByName);
        log.info("更新 {} 的备注", findByName.getUsername());
        accountInfoMapper.deleteById(findByName.getId());
        log.info("删除数据： {} ", findByName.getUsername());
        log.info("当前数据库一共有数据：{}条", accountInfoMapper.findAll().size());
    }

    @Test
    void testXml() {
        AccountInfoType accountInfoType = new AccountInfoType();
        accountInfoType.setAccountType("视频软件");
        log.info("录入一条数据");
        accountInfoTypeMapper.insert(accountInfoType);
        log.info("查询所有数据：{}", accountInfoTypeMapper.findAll());

    }

}
