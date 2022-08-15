package com.ecc.myzhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecc.myzhxy.pojo.Clazz;

/**
 * @author sunyc
 * @create 2022-06-13 16:13
 */
public interface ClazzService extends IService<Clazz> {
    Page getPages(Page page, String name);
}
