package com.ecc.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecc.myzhxy.pojo.Clazz;
import com.ecc.myzhxy.pojo.Grade;
import com.ecc.myzhxy.service.GradeService;
import com.ecc.myzhxy.util.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 15:56
 */
@Api(tags = "年级管理-控制")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    GradeService gradeService;


    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> list = gradeService.list();
        return  Result.ok(list);
    }
    @ApiOperation("班级删除")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids){
        QueryWrapper<Grade> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        gradeService.remove(queryWrapper);
        return Result.ok();
    }
    @ApiOperation("年级的分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@PathVariable("pageNo") Integer pageno,
                            @PathVariable("pageSize") Integer pageSize,
                            String gradeName){
        //分页带条件查询
        Page<Grade> page=new Page<>(pageno,pageSize);

        Page pages = gradeService.getPages(page, gradeName);
        return  Result.ok(pages);
    }
    @ApiOperation("年级的修改和添加")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("有id的是更新，无id的为添加")
                                        @RequestBody Grade grade){
        System.out.println(grade);
        gradeService.saveOrUpdate(grade);
        return  Result.ok();
    }
}
