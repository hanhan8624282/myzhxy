package com.ecc.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecc.myzhxy.mapper.StudentMapper;
import com.ecc.myzhxy.pojo.LoginFrom;
import com.ecc.myzhxy.pojo.Student;
import com.ecc.myzhxy.service.StudentService;
import com.ecc.myzhxy.util.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sunyc
 * @create 2022-06-13 16:16
 */
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student getOne(LoginFrom loginFrom) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",loginFrom.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginFrom.getPassword()));

        return baseMapper.selectOne(queryWrapper);

    }

    @Override
    public Page getPages(Page page, String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if(name!=null){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}
