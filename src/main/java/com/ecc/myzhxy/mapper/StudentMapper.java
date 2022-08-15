package com.ecc.myzhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecc.myzhxy.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author sunyc
 * @create 2022-06-13 15:50
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {
}
