var {add}=require('./module1');
var Vue=require('./vue.min');
<!--  定义VM部分-->
<!--  定义VM部分-->
var vm = new Vue(
    {
        el:"#welcome", //MV代表接管了welcome区域
        data:{
            name:"欢迎大家来到Java课堂",
            num1:0,
            num2:0,
            result:0,
            url:"http://www.baidu.com",
            size:18
        },
        methods:{
            count:function () {
                this.result=add(Number.parseInt(this.num1),Number.parseInt(this.num2));
                alert(this.result);
            }
        }
    }
);