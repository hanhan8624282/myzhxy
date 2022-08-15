package com.ecc.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecc.myzhxy.pojo.Teacher;
import com.ecc.myzhxy.service.TeacherService;
import com.ecc.myzhxy.util.util.MD5;
import com.ecc.myzhxy.util.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 15:57
 */
@Api(tags = "老师管理-控制")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @ApiOperation("教师管理-分页接口")
    @GetMapping("/getTeachers/{pageno}/{pagesize}")
    public Result getTeachers(@PathVariable("pageno") Integer pageno,
                              @PathVariable("pagesize") Integer pagesize,
                              String name){
        Page page=new Page(pageno,pagesize);

        Page pages = teacherService.getPages(page, name);
        return Result.ok(pages);
    }

    @ApiOperation("教师管理-添加或更新")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        if(teacher.getId()==null){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }
    @ApiOperation("教师管理-删除")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("id",ids);
        teacherService.remove(queryWrapper);
        return Result.ok();
    }
}

