package com.design.oa;

import com.alibaba.fastjson.JSON;
import com.design.oa.dao.AttendanceAdminMapper;
import com.design.oa.dao.DepartmentMapper;
import com.design.oa.dao.RoleMapper;
import com.design.oa.model.AttendanceRecord;
import com.design.oa.model.Department;
import com.design.oa.service.AdminService;
import com.design.oa.service.AttenService;
import com.design.oa.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.design.oa.model.Menu;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests {
    @Resource
    AttendanceAdminMapper attendanceAdminMapper;
    @Resource
    AdminService adminService;
    @Resource
    RoleMapper roleMapper;
    @Resource
    EmailService emailService;
    @Resource
    DepartmentMapper departmentMapper;
    @Resource
    AttenService attenService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllMenu() {
        List<Menu> menuList = adminService.searchUserTreeById(0);
        System.out.println("menuList:" + JSON.toJSON(menuList));
    }

    @Test
    public void test() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Department> departmentList = departmentMapper.getAllDepartment("IT运维部");
        for (Department department : departmentList) {
            System.out.println("d:" + department.getDepartmentName());
        }

    }

    @Test
    public void mail() throws Exception {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.setTime(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH,1);
        System.out.println(calendar.getTime());
    }


    @Test
    public void test1() throws Exception {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("EEE MMM d hh:mm:ss z yyyy", java.util.Locale.US);
        SimpleDateFormat bartDateFormat_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;
        Date d = new Date();
        d = bartDateFormat.parse("Sun Apr 14 2019 22:14:11 GMT+0800");
        date = bartDateFormat_1.format(d);
        System.out.println(date);
    }

    @Test
    public void test2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String attDate = null;
        try {
            attDate = sdf.format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(attDate);
    }

    @Test
    public void test3() {
        try {
            SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            AttendanceRecord attendanceRecord = new AttendanceRecord();
            attendanceRecord.setAttWorkTime(new Timestamp(date.getTime()));
            Date signInTime = attendanceRecord.getAttWorkTime();
            String date1 = df1.format(signInTime);
            Date date2 = df1.parse(date1);
            Time time = new Time(date2.getTime());
            Time time1 =   attendanceAdminMapper.selectWorkTime();
            if(time.after(time1)){
                System.out.println("迟到");
            }
            System.out.println(time1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
