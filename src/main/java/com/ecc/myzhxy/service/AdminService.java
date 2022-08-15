package com.ecc.myzhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.pojo.LoginFrom;


import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-10 13:22
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginFrom loginFrom);


    Page<Admin> getPages(Page page, String adminName);
}
