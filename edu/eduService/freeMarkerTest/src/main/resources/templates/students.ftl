<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<#--<#if name??>
    ${name}
</#if>-->7gts
${point?c}
${name!'wangchen'}
<br/>

    <table>
        <tr><td>序号</td><td>姓名</td><td>钱</td><td>生日</td></tr>

        <#list stuList as stu><#--string('dd.MM.yyyy HH:mm:ss')-->
            <tr><td>${stu_index+1}</td><td>${(stu.name)!''}</td><td>${stu.money}</td><td>${stu.birthday?date}</td></tr>
        </#list>

        <tr><td>&nbsp;</td><td>${(stu1.name)!''}</td><td>${stu1.money}</td><td>${stu1.age}</td></tr>
        <#list stuMap?keys as key>
            <tr <#if key_index%2==0>style="background-color: blue" </#if>><td>${key_index+1}</td><td>${(stuMap[key].name)!''}</td><td>${stuMap[key].money}</td><td>${stuMap[key].age}</td></tr>
        </#list>
       <#-- <tr><td>&nbsp;</td><td>${stuMap.stu1.name}</td><td>${stuMap.stu1.money}</td><td>${stuMap.stu1.age}</td></tr>
        <tr><td>&nbsp;</td><td>${stuMap["stu2"].name}</td><td>${stuMap["stu2"].money}</td><td>${stuMap["stu2"].age}</td></tr>-->
    </table>
<br/>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data=text?eval />
开户行：${data.bank}  账号：${data.account}

</body>
</html>