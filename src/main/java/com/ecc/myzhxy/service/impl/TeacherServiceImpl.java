package com.ecc.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecc.myzhxy.mapper.TeacherMapper;
import com.ecc.myzhxy.pojo.LoginFrom;
import com.ecc.myzhxy.pojo.Teacher;
import com.ecc.myzhxy.service.TeacherService;
import com.ecc.myzhxy.util.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sunyc
 * @create 2022-06-13 16:17
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher getOne(LoginFrom loginFrom) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper();
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
