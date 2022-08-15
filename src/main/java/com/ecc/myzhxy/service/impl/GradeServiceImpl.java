package com.ecc.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecc.myzhxy.mapper.AdminMapper;
import com.ecc.myzhxy.mapper.GradeMapper;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.pojo.Grade;
import com.ecc.myzhxy.service.AdminService;
import com.ecc.myzhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 16:16
 */
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public Page getPages(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper=new QueryWrapper();
        if(gradeName!=null){
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByDesc("id");
        Page page1 = baseMapper.selectPage(page, queryWrapper);
        return page1;
    }

    @Override
    public void deleteId(List<Integer> ids) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in ("id",ids);
        baseMapper.delete(queryWrapper);
    }

}
