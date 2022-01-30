<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
${stu1.name}---${stu1.age}
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>出生日期</td>
    </tr>
    <#list stuList as stu>
        <tr>
            <td>${stu_index+1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.birthday?date}</td>
        </tr>
    </#list>
</table>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
    </tr>
    <#list stuMap?keys as key>
        <tr <#if key_index%2==0> style="background-color: aquamarine" </#if>>
            <td>${key_index+1}</td>
            <td>${stuMap[key].name}</td>
            <td>${stuMap[key].age}</td>
            <td>${stuMap[key].money}</td>
        </tr>
    </#list>
</table>
</body>
</html>