package com.ecc.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author sunyc
 * @create 2022-06-13 15:44
 */
@Repository  //pojo注解
@Data      //简化 get，set，to_string等等方法
@AllArgsConstructor //全参构造器
@NoArgsConstructor //无参构造器
@TableName(value = "tb_student")//指向数据库的实体表
public class Student {
    @TableId(value = "id",type= IdType.AUTO)//主键id 自动识别
    private Integer id;
    private Integer sno;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String introducation;
    private String portraitPath;
    private String clazzName;


}
