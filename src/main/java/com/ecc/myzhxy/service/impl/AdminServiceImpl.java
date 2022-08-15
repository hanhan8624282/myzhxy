package com.ecc.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecc.myzhxy.mapper.AdminMapper;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.pojo.Clazz;
import com.ecc.myzhxy.pojo.LoginFrom;
import com.ecc.myzhxy.service.AdminService;

import com.ecc.myzhxy.util.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-10 13:24
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(LoginFrom loginFrom) {

        QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",loginFrom.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginFrom.getPassword()));
        Admin admin = baseMapper.selectOne(queryWrapper);
        System.out.println("验证用户数据");
        return admin;
    }

    @Override
    public Page getPages(Page page, String name) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper();
        if(name!=null){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        Page page1 = baseMapper.selectPage(page, queryWrapper);
        return page1;
    }


}
