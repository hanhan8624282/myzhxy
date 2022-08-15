package com.ecc.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecc.myzhxy.mapper.AdminMapper;
import com.ecc.myzhxy.mapper.ClazzMapper;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.pojo.Clazz;
import com.ecc.myzhxy.pojo.Grade;
import com.ecc.myzhxy.service.AdminService;
import com.ecc.myzhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sunyc
 * @create 2022-06-13 16:15
 */
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public Page getPages(Page page, String name) {
        QueryWrapper<Clazz> queryWrapper=new QueryWrapper();
        if(name!=null){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");
        Page page1 = baseMapper.selectPage(page, queryWrapper);
        return page1;
    }
}
