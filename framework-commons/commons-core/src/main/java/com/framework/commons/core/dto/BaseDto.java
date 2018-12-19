package com.framework.commons.core.dto;

import com.framework.commons.core.AbstractBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author heyanjing
 * date:2018-12-09 2018/12/9:19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseDto extends AbstractBean implements IDto {
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createDatetime;
    /**
     * 修改人
     */

    private String updateUser;
    /**
     * 修改时间
     */

    private Date updateDatetime;
    /**
     * 数据状态
     */

    private Integer state;

    /**
     * 是否admin
     */
    private Integer isSuper;

    /**
     * 访问令牌
     */

    private String token;
    /**
     * 客户端登录ip
     */

    private String ip;
    /**
     * 页码
     */

    private Integer pageIndex;
    /**
     * 每页显示条数
     */

    private Integer pageSize;
}
