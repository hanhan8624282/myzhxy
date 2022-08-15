package com.ecc.myzhxy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecc.myzhxy.mapper.UserMapper;
import com.ecc.myzhxy.pojo.Admin;
import com.ecc.myzhxy.pojo.LoginFrom;
import com.ecc.myzhxy.pojo.User;
import com.ecc.myzhxy.service.AdminService;
import freemarker.ext.beans.TemplateAccessible;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class MyzhxyApplicationTests {

    @Autowired
    AdminService adminService;
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        LoginFrom loginFrom=new LoginFrom();
        loginFrom.setPassword("admin");
        loginFrom.setUsername("admin");
        System.out.println(loginFrom.getPassword());
        Admin login = adminService.login(loginFrom);
        System.out.println(login);
    }

    @Test
    void queryList(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.between("age",10,100);
        queryWrapper.select("id","name","age");
        System.out.println(userMapper.selectList(queryWrapper));

    }
    @Test
    void add(){
        User user = new User();
        user.setAge(1100);
        user.setEmail("sunchao@11.com");
        user.setName("sunchao");
        userMapper.insert(user);
        System.out.println(user);
    }

    @Test
    void update(){
        User user = new User();
        user.setId(1536885378156392453L);
        user.setAge(111);
        user.setEmail("sunchao@11.com");
        user.setName("sunchao");
        userMapper.updateById(user);
    }
    @Test
    void delete(){
        User user = new User();
        user.setId(1L);
        user.setAge(11);
        user.setEmail("sunchao@11.com");
        user.setName("sunchao");
        userMapper.deleteById(1536885378156392449L);
    }
    @Test //测试乐观锁  先查询在修改
    void happyupdate(){
        User user = userMapper.selectById(1536885378156392453L);
        user.setAge(2222);
        userMapper.updateById(user);
    }
    @Test//测试乐观锁  先查询在修改 查出来的version比数据库的低
    void testupdate(){
        User user = userMapper.selectById(1536885378156392453L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");
        user.setVersion(user.getVersion()-1);
        userMapper.updateById(user);
    }
    @Test//批量删除
    void delete2(){
        List<Integer> list=new LinkedList();
        list.add(2);
        list.add(3);
        userMapper.deleteBatchIds(list);
    }

    @Test //page 分页
    void page(){
        Page<User> page = new Page<>(2,5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);

        System.out.println(page.getCurrent());

        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

}
