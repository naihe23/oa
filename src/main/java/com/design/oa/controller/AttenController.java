package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.activiti.config.MyExecutionListener;
import com.design.oa.activiti.vo.MyProcessDefinition;
import com.design.oa.model.*;
import com.design.oa.service.AttenAdminService;
import com.design.oa.service.AttenService;
import com.design.oa.service.LeaveNoteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/atten")
@RestController
public class AttenController {

    @Resource
    AttenService attenService;

    @Resource
    AttenAdminService attenAdminService;
    @Autowired
    RepositoryService repositoryService;
    @Resource
    LeaveNoteService leaveNoteService;
    @Resource
    ProcessEngine processEngine;

    @PostMapping("/worktime")
    public String initialWorkTime(String workTime, String address) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        Date date = new Date(workTime);
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setAttDate(new Timestamp(date.getTime()));
        attendanceRecord.setUserId(user.getUserId());
        attendanceRecord.setAttWorkTime(new Timestamp(date.getTime()));
        int state = attenService.initialWorkTime(attendanceRecord, address);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/offtime")
    public String initialOffTime(String offtime, String address) throws Exception {
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        Date date = new Date(offtime);
        Date date1 = df.parse(df.format(date));
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setUserId(user.getUserId());
        attendanceRecord.setAttDate(new Timestamp(date1.getTime()));
        attendanceRecord.setAttOffTime(new Timestamp(date.getTime()));
        int state = attenService.initialOffTime(attendanceRecord, address);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @GetMapping("/worktime")
    public String getWorkTime() {
        User user = null;
        JSONObject jsonObject = new JSONObject();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
            AttendanceRecord attendanceRecord = attenService.getWorkTime(user.getUserId());
            jsonObject.put("msg", 201);
            jsonObject.put("attenRecord", attendanceRecord);
        } else
            jsonObject.put("msg", 401);
        return jsonObject.toString();
    }

    @GetMapping("/records")
    public String getRecords() {
        User user = null;
        JSONObject jsonObject = new JSONObject();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
            List<AttendanceRecord> recordList = attenService.selectRecordsByUser(user.getUserId());
            jsonObject.put("msg", 201);
            jsonObject.put("recordList", recordList);
        } else
            jsonObject.put("msg", 401);
        return jsonObject.toString();
    }

    @GetMapping("/setting")
    public String getAttenSetting() {
        JSONObject jsonObject = new JSONObject();
        AttendanceAdmin attendanceAdmin = attenAdminService.getAttenSetting();
        if (attendanceAdmin != null) {
            jsonObject.put("msg", 201);
            jsonObject.put("attenSetting", attendanceAdmin);
        } else {
            jsonObject.put("msg", 401);
        }
        return jsonObject.toString();
    }

    @PostMapping("/setting")
    public String editAttenAdmin(@RequestBody AttendanceAdmin attendanceAdmin) {
        JSONObject jsonObject = new JSONObject();
        int state = attenAdminService.editAttenAdmin(attendanceAdmin);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/leaveform")
    public String leaveForm(@RequestParam("userId") int userId,
                            @RequestParam("leaveUserDepartment") String leaveUserDepartment,
                            @RequestParam("leaveType") String leaveType,
                            @RequestParam("leaveStartTime") String leaveStartTime,
                            @RequestParam("leaveEndTime") String leaveEndTime,
                            @RequestParam("leaveReason") String leaveReason,
                            @RequestParam("leaveNoteId") String leaveNoteId
    ) {
        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date leaveStartTime1 = sdf.parse(leaveStartTime);
            Date leaveEndTime1 = sdf.parse(leaveEndTime);
            LeaveNote leaveNote = new LeaveNote();
            leaveNote.setUserId(userId);
            leaveNote.setLeaveReason(leaveReason);
            leaveNote.setLeaveStartTime(leaveStartTime1);
            leaveNote.setLeaveEndTime(leaveEndTime1);
            leaveNote.setLeaveType(leaveType);
            leaveNote.setLeaveUserDepartment(leaveUserDepartment);
            User user = null;
            Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            if (o instanceof User) {
                user = (User) o;
            }
            if(leaveNoteId!=null&&!leaveNoteId.equals("undefined")){
                leaveNote.setLeaveNoteId(Integer.parseInt(leaveNoteId));
                int state = leaveNoteService.updateLeaveNote(leaveNote);
                ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                        .processInstanceBusinessKey(leaveNoteId).singleResult();
                processEngine.getRuntimeService().setVariableLocal(processInstance.getId(),"reject_reason",null);
                jsonObject.put("msg",state);
                return jsonObject.toString();
            }
            int state = leaveNoteService.leaveForm(leaveNote);
            List<ProcessDefinition> processDefinitionLast = repositoryService.createProcessDefinitionQuery()
                    .orderByProcessDefinitionVersion().desc()
                    .processDefinitionKey("leaveBill").latestVersion().list();
            MyProcessDefinition myProcessDefinition = null;
            for (ProcessDefinition definition : processDefinitionLast) {
                myProcessDefinition = new MyProcessDefinition(definition);
            }

            String key = myProcessDefinition.getKey();
            String businessId = String.valueOf(leaveNote.getLeaveNoteId());
            Map<String, Object> map = new HashMap<>();
            map.put("leaveNote", leaveNote);
            map.put("start_person", user.getUserId());
            map.put("myAttenRecordListener", new MyExecutionListener());
            ProcessInstance processInstance = processEngine.getRuntimeService()
                    .startProcessInstanceByKey(key, businessId, map);
            leaveNote.setProinsId(processInstance.getId());
            leaveNoteService.updateLeaveNote(leaveNote);
            Task task = processEngine.getTaskService().createTaskQuery()
                    .processInstanceId(processInstance.getId()).singleResult();
            task.setAssignee(String.valueOf(user.getUserId()));
            processEngine.getTaskService().saveTask(task);
            processEngine.getTaskService().complete(task.getId());
            jsonObject.put("msg", state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @GetMapping("/leaverecord")
    public HashMap<String, Object> leaveRecord(int curPage) {
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
            PageHelper.startPage(curPage, 4);
            List<LeaveNote> leaveNoteList = leaveNoteService.getLeaveRecord(user.getUserId());
            PageInfo<LeaveNote> pageList = new PageInfo<>(leaveNoteList);
            PageHelper.clearPage();
            map.put("msg", 201);
            map.put("leaveRecord", pageList);
            return map;
        } else
            return null;
    }

    @PostMapping("/leavecancel")
    public String leaveCancel(int leaveNoteId) {
        JSONObject jsonObject = new JSONObject();
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceBusinessKey(String.valueOf(leaveNoteId)).singleResult();
        if (processInstance != null) {
            jsonObject.put("msg", 401);
            return jsonObject.toString();
        }
        int state = leaveNoteService.leaveCancel(leaveNoteId);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/repairatten")
    public String repairAtten(int userId, String attDate, String attRepairReason, String attRepairType) {
        JSONObject jsonObject = new JSONObject();
        int state = attenService.repairAtten(userId, attDate, attRepairReason, attRepairType);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @GetMapping("/recordMessage")
    public String getRecodeMessage(String curDate) throws Exception {
        User user = null;
        JSONObject jsonObject = new JSONObject();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
            HashMap<String, Integer> recordMessage = attenService.getRecordMessage(user.getUserId(), curDate);
            jsonObject.put("msg", 201);
            jsonObject.put("recordMessage", recordMessage);
        } else
            jsonObject.put("msg", 401);
        return jsonObject.toString();
    }
}
