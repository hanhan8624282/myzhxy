package com.ecc.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.service.AdminService;
import com.ecc.myzhxy.util.util.MD5;
import com.ecc.myzhxy.util.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 15:56
 */
@Api(tags = "admin管理员-控制")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    AdminService adminService;

    @ApiOperation("管理员分页的接口")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pagesize,
                              String adminName) {
        Page page = new Page(pageNo, pagesize);
        Page pages = adminService.getPages(page, adminName);
        return Result.ok(pages);
    }

    @ApiOperation("管理员的添加和更改接口")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        //如果id为空那就是添加，并把密码加密
        if(admin.getId()==null){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }
    @ApiOperation("管理员的删除接口")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids){

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("id",ids);
        adminService.remove(queryWrapper);
        return Result.ok();
    }


}
