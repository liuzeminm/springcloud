package com.xangqun.springcloud.model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: [zhangzhiguang]
 * @Date: [2018-08-21 23:00]
 * @Description: [ ]
 * @Version: [1.0.0]
 * @Copy: [com.zzg]
 */
@Data
public class SysUserExcel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5886012896705137070L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String headImgUrl;

    private String phone;

    private Integer sex;

    private Boolean enabled;

    private String type;

    private Date createTime;

    private Date updateTime;

}
