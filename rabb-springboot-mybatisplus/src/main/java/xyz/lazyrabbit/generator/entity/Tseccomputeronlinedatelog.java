package xyz.lazyrabbit.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Tseccomputeronlinedatelog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "online_id", type = IdType.AUTO)
    private Integer onlineId;

    /**
     * 主机唯一标识
     */
    private String computerGuid;

    /**
     * 上线时间
     */
    private LocalDateTime onlineTime;


}
