package com.yh.controller;

import com.yh.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(Map<String,Object> map){
        map.put("name","张三");

        Student student1 = new Student();
        Student student2 = new Student();
        student1.setName("张三");
        student1.setAge(13);
        student1.setMoney(100L);
        student1.setBirthday(new Date());
        student2.setName("李四");
        student2.setAge(14);
        student2.setMoney(150L);
        student2.setBirthday(new Date());

        ArrayList<Student> stuList = new ArrayList<>();
        stuList.add(student1);
        stuList.add(student2);

        map.put("stuList",stuList);

        map.put("stu1",student1);


        HashMap<String, Student> stringStudentHashMap = new HashMap<>();
        stringStudentHashMap.put("stu1",student1);
        stringStudentHashMap.put("stu2",student2);

        map.put("stuMap",stringStudentHashMap);

        //Map<String, List<Student>> stuMap=new HashMap<>();
        //stuMap.put("好友列表",stuList);
        return "student";
    }
    @GetMapping("/test1")
    public String toBanner(Map map){
        ResponseEntity<Map> entity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = entity.getBody();
        System.out.println(body);
        map.putAll(body);
        return "index_banner";
    }
}
