package com.yh.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString

public class Student {
    public String name;
    public int age;
    public long money;
    public Date birthday;
    private List<Student> friends;//朋友列表
    private Student bestFriend;//最好的朋友
}
