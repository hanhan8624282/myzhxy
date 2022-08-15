package com.ecc.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecc.myzhxy.pojo.Clazz;
import com.ecc.myzhxy.service.ClazzService;
import com.ecc.myzhxy.util.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 15:56
 */
@Api(tags = "班级管理-控制")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    ClazzService clazzService;



    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }
    @ApiOperation("班级的分页管理 ")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pagesize}")
    public Result getClazzsByOpr(@PathVariable("pageNo") Integer pageNo,
                                 @PathVariable("pagesize") Integer pagesize,
                                 String name){

        Page page=new Page(pageNo,pagesize);

        Page pages=clazzService.getPages(page,name);
        return Result.ok(pages);
    }
    @ApiOperation("班级的添加和更改")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){

        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }
    @ApiOperation("班级的删除")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids){
        QueryWrapper<Clazz> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        clazzService.remove(queryWrapper);
        return Result.ok();
    }
}
