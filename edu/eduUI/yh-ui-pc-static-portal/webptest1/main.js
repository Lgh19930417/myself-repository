var {add}=require('./module');
var Vue=require('./vue.min');
<!-- 定义mv-->
var vm = new Vue({
    el:"#nameId", //定义vm要管理的区域
    data:{
        name: "高级程序员",
        num1: 0,
        num2: 0,
        result:0,
        size:16,
        url:"http://www.baidu.com",
        mylist:['aa','bb','cc','ee'],
        user:{name:"yangchong",age:21},
        userList:[
            {user:{name:"yangchong",age:21}},
            {user: {name:"zhigao",age:22}},
            {user:{name:"haoran",age:20}}
        ]
    },
    methods:{
        count:function () {
            this.result=add(Number.parseInt(this.num1),Number.parseInt(this.num2));
            //this.result=Number.parseInt(this.num1) + Number.parseInt(this.num2);
        }
    }
});
