package com.ecc.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecc.myzhxy.pojo.Student;
import com.ecc.myzhxy.pojo.Teacher;
import com.ecc.myzhxy.service.StudentService;
import com.ecc.myzhxy.util.util.MD5;
import com.ecc.myzhxy.util.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunyc
 * @create 2022-06-13 15:57
 */
@Api(tags = "学生管理-控制")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;
    @ApiOperation("学生管理-分页接口")
    @GetMapping("/getStudentByOpr/{pageno}/{pagesize}")
    public Result getStudentByOpr(@PathVariable("pageno") Integer pageno,
                              @PathVariable("pagesize") Integer pagesize,
                              String name){
        Page page=new Page(pageno,pagesize);

        Page pages = studentService.getPages(page, name);
        return Result.ok(pages);
    }

    @ApiOperation("学生管理-添加或更新")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        if(student.getId()==null){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }
    @ApiOperation("学生管理-删除")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("id",ids);
        studentService.remove(queryWrapper);
        return Result.ok();
    }
}
