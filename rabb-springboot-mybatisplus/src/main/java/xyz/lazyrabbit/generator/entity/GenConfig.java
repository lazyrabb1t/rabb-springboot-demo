package xyz.lazyrabbit.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lazyrabbit
 * @since 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GenConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 作者
     */
    private String author;

    /**
     * 是否覆盖
     */
    private Boolean cover;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 至于哪个包下
     */
    private String pack;

    /**
     * 前端代码生成的路径
     */
    private String path;

    /**
     * 前端Api文件路径
     */
    private String apiPath;

    /**
     * 表前缀
     */
    private String prefix;

    /**
     * 接口名称
     */
    private String apiAlias;


}
