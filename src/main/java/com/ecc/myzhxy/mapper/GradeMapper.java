package com.ecc.myzhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecc.myzhxy.pojo.Grade;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author sunyc
 * @create 2022-06-13 15:50
 */
@Repository
public interface GradeMapper extends BaseMapper<Grade> {
}
