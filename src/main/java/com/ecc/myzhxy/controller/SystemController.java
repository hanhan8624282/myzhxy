package com.ecc.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.pojo.LoginFrom;
import com.ecc.myzhxy.pojo.Student;
import com.ecc.myzhxy.pojo.Teacher;
import com.ecc.myzhxy.service.AdminService;
import com.ecc.myzhxy.service.StudentService;
import com.ecc.myzhxy.service.TeacherService;
import com.ecc.myzhxy.util.util.CreateVerifiCodeImage;
import com.ecc.myzhxy.util.util.JwtHelper;
import com.ecc.myzhxy.util.util.MD5;
import com.ecc.myzhxy.util.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * @author sunyc
 * @create 2022-06-13 15:57
 */
@Api(tags = "系统管理控制")
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    //Swagger 打开网址的方式
//http://localhost:8080/swagger-ui.html


    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldpassword}/{newpassword}")
    public Result updatePwd(@PathVariable("oldpassword") String oldpassword,
                            @PathVariable("newpassword") String newpassword,
                            @RequestHeader("token") String token){
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",userId);
        switch (userType){
            case 1:
                Admin admin = adminService.getOne(queryWrapper);
                //密码变更为新密码
                admin.setPassword(MD5.encrypt(newpassword));
                //更新
                adminService.updateById(admin);
                return Result.ok();
            case 2:
                Student student = studentService.getOne(queryWrapper);
                //密码变更为新密码
                student.setPassword(MD5.encrypt(newpassword));
                //更新
                studentService.updateById(student);
                return Result.ok();
            case 3:
                Teacher teacher = teacherService.getOne(queryWrapper);
                //密码变更为新密码
                teacher.setPassword(MD5.encrypt(newpassword));
                //更新
                teacherService.updateById(teacher);
                return Result.ok();
        }
        return Result.fail().message("用户错误");

    }

    @ApiOperation("图片上传接口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile){
        //生成一个唯一的uudid
        String uuid = UUID.randomUUID().toString().toLowerCase().toString();
        //获取上传图片的名字
        String originalFilename = multipartFile.getOriginalFilename();
        //截取图片的后缀名的位置
        int i = originalFilename.lastIndexOf(".");
        //新的一个图片名
        String newName=uuid.concat(originalFilename.substring(i));
        //图片的存放地址
        String newNamePath="D:\\java_students\\java\\myzhxy\\target\\classes\\public\\upload\\".concat(newName);
        //图片的存放地址
        try {
            multipartFile.transferTo(new File(newNamePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //浏览器相应文件的路劲以及文件名
        String path="upload/".concat(newName);
        return Result.ok(path);
    }

    @ApiOperation("获取用户的类型，登陆后显示不同的页面")
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token){
        //判断token是够过期
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            Result.fail().message("token 口令过期");
        }
        //从token口令中获取用户类型，并根据用户的id返回该用户的数据
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //创建一个map 存放返回的数据
        Map<String,Object> map=new LinkedHashMap<>();
        //sql 条件
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        switch (userType){
            case 1:
                Admin admin = adminService.getOne(queryWrapper);
                if(admin!=null){
                    map.put("userType",1);
                    map.put("user",admin);
                    return  Result.ok(map);
                }else{
                    return Result.fail().message("无此用户..");
                }
            case 2:
                Student student = studentService.getOne(queryWrapper);
                if(student!=null){
                    map.put("userType",2);
                    map.put("user",student);
                    return  Result.ok(map);
                }else{
                    return Result.fail().message("无此用户..");
                }
            case 3:
                Teacher teacher = teacherService.getOne(queryWrapper);
                if(teacher!=null){
                    map.put("userType",3);
                    map.put("user",teacher);
                    return  Result.ok(map);
                }else{
                    return Result.fail().message("无此用户..");
                }
        }
        return Result.fail().message("查无此用户");
    }
    @PostMapping("/login")
    @ApiOperation("登陆验证接口")
    public Result login(@RequestBody LoginFrom loginFrom,HttpServletRequest request){
        //获取用户输入的验证码
        String loginCode = loginFrom.getVerifiCode();
        //获取保存在session域中的验证码
        String sessionCode = (String)request.getSession().getAttribute("verifiCode");
        System.out.println("sessionCode="+sessionCode+",loginCode="+loginCode);
        //判断验证码是否为空
        if(loginCode==null || sessionCode==null){
            Result.fail().message("验证码为空，或无效。。");
        }
        if(!sessionCode.equalsIgnoreCase(loginCode)){
            Result.fail().message("验证码输入错误请再次输入。。。");
        }
        //验证通过后清除掉session域中的验证码
        request.getSession().removeAttribute("verifiCode");
        //获取用户的登陆类型
        Integer userType = loginFrom.getUserType();
        //创建一个存放用户数据map，用来返给前端
        Map<String,String> map=new LinkedHashMap<>();
        System.out.println(123456);
        //用户条件拼sql
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",loginFrom.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginFrom.getPassword()));

        //根据用户登陆的类型不同做不同的登陆验证
        switch (userType){
            case 1:
                try {
                    Admin admin = adminService.getOne(queryWrapper);
                    //Admin admin = adminService.login(loginFrom);
                    if(admin!=null){
                        //用户id和用户类型存的名字是token
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(),1));
                    }else{
                        throw new RuntimeException("无该用户的数据");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                //Student student = studentService.getOne(loginFrom);
                try {
                    Student student = studentService.getOne(queryWrapper);
                    if(student!=null){
                        //用户id和用户类型存的名字是token
                        map.put("token", JwtHelper.createToken(student.getId().longValue(),2));
                    }else{
                        throw new RuntimeException("无该用户的数据");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                //Teacher teacher = teacherService.getOne(loginFrom);
                try {
                    Teacher teacher = teacherService.getOne(queryWrapper);
                    if(teacher!=null){
                        //用户id和用户类型存的名字是token
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(),3));
                    }else{
                        throw new RuntimeException("无该用户的数据");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return   Result.fail().message("无用户数据") ;
    }

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public Result getVerifiCodeImage(@ApiParam("生成图片验证码") HttpServletRequest request, HttpServletResponse response){
        //生成一个验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //从图片中获取验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //存在session域中，方便登陆的时候做验证
        request.getSession().setAttribute("verifiCode",verifiCode);
        //将图片返回给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  Result.ok();

    }
}
