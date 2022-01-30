package com.yh.test;

import com.yh.pojo.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    //根据模板生成静态HTML文件
    @Test
    public void getStaticHtmlByString() throws Exception {
        //创建Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建String模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        //创建模板内容
        String template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "${stu1.name}---${stu1.age}\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <td>序号</td>\n" +
                "        <td>姓名</td>\n" +
                "        <td>年龄</td>\n" +
                "        <td>出生日期</td>\n" +
                "    </tr>\n" +
                "    <#list stuList as stu>\n" +
                "        <tr>\n" +
                "            <td>${stu_index+1}</td>\n" +
                "            <td>${stu.name}</td>\n" +
                "            <td>${stu.age}</td>\n" +
                "            <td>${stu.birthday?date}</td>\n" +
                "        </tr>\n" +
                "    </#list>\n" +
                "</table>\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <td>序号</td>\n" +
                "        <td>姓名</td>\n" +
                "        <td>年龄</td>\n" +
                "        <td>金额</td>\n" +
                "    </tr>\n" +
                "    <#list stuMap?keys as key>\n" +
                "        <tr <#if key_index%2==0> style=\"background-color: aquamarine\" </#if>>\n" +
                "            <td>${key_index+1}</td>\n" +
                "            <td>${stuMap[key].name}</td>\n" +
                "            <td>${stuMap[key].age}</td>\n" +
                "            <td>${stuMap[key].money}</td>\n" +
                "        </tr>\n" +
                "    </#list>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
        //加载模板
        stringTemplateLoader.putTemplate("template", template);
        //配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        Template template1 = configuration.getTemplate("template");
        //获取模型数据
        Map modelData = FreemarkerTest.getModelData();
        //获取静态页面内容
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template1, modelData);
        //生成静态页面
        InputStream is = IOUtils.toInputStream(htmlContent);
        FileOutputStream fos = new FileOutputStream("d:\\b.html");
        IOUtils.copy(is, fos);
        fos.close();
        is.close();


    }

    private void getStaticHtmlByFile() throws IOException, TemplateException {
        //创建Configuration对象,必须指定版本
        Configuration configuration = new Configuration(Configuration.getVersion());
        //模板所在路径
        String path = this.getClass().getResource("/templates").getPath();
        //设置模板路径
        configuration.setDirectoryForTemplateLoading(new File(path));
        //设置编码
        configuration.setDefaultEncoding("UTF-8");
        //获取模板
        Template template = configuration.getTemplate("student.ftl");
        //获取模型数据
        Map modelData = FreemarkerTest.getModelData();
        //通过freemarker引擎生成静态页面的字符串结果
        String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, modelData);
        //通过流的形式输出到磁盘中
        InputStream inputStream = IOUtils.toInputStream(result, "UTF-8");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\a.html"));
        IOUtils.copy(inputStream, fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
    }


    public static Map getModelData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");

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

        map.put("stuList", stuList);

        map.put("stu1", student1);


        HashMap<String, Student> stringStudentHashMap = new HashMap<>();
        stringStudentHashMap.put("stu1", student1);
        stringStudentHashMap.put("stu2", student2);

        map.put("stuMap", stringStudentHashMap);
        return map;
    }
}
