package com.ecc.myzhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecc.myzhxy.pojo.Clazz;
import com.ecc.myzhxy.pojo.LoginFrom;
import com.ecc.myzhxy.pojo.Teacher;

/**
 * @author sunyc
 * @create 2022-06-13 16:14
 */
public interface TeacherService extends IService<Teacher> {
    Teacher getOne(LoginFrom loginFrom);

    Page getPages(Page page, String name);
}
