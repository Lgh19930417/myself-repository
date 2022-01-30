var {add} = require('./module1.js');
var Vue = require('./vue.min');

var vm = new Vue({
    el:"#school",//vm接管的视图view部分
    data:{
        schoolName: "河南大学",
        num1: 0,
        num2: 0,
        res: 0,
        url: "http://www.henu.edu.cn/",
        size: 18,
        myList: [11,22,33,44,55,66],
        user: {name:"丁晨",age:21,id:1},
        userList:[
            {user: {name:"丁晨",age:21,id:1}},
            {user: {name:"潘永康",age:21,id:2}},
            {user: {name:"李翰霖",age:19,id:3}},
            {user: {name:"杨露露",age:18,id:4}},
            {user: {name:"郭淑婧",age:17,id:5}},
            {user: {name:"吴蕊",age:16,id:6}}
        ]
    },
    methods:{
        count: function(){
            this.res= add(Number.parseInt(this.num1),Number.parseInt(this.num2));
            //this.res = Number.parseInt(this.num1)+Number.parseInt(this.num2);
            //alert(1);
        }
    }
});