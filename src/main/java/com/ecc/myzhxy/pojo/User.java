package com.ecc.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sunyc
 * @create 2022-06-15 9:31
 */
@Data
@TableName(value = "user")
public class User  {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
    @TableLogic
    private Integer deleted;

}
