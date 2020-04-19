package xyz.lazyrabbit.generator.service.impl;

import xyz.lazyrabbit.generator.entity.GenConfig;
import xyz.lazyrabbit.generator.mapper.GenConfigMapper;
import xyz.lazyrabbit.generator.service.IGenConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lazyrabbit
 * @since 2020-04-05
 */
@Service
public class GenConfigServiceImpl extends ServiceImpl<GenConfigMapper, GenConfig> implements IGenConfigService {

}
