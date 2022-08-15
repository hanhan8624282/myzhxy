package com.ecc.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecc.myzhxy.pojo.Clazz;
import com.ecc.myzhxy.pojo.Grade;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 16:14
 */
public interface GradeService extends IService<Grade> {
    Page getPages(Page<Grade> page, String gradeName);

    void deleteId(List<Integer> ids);
}
