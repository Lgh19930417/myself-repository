var {count} = require('./model01.js');
var Vue=require('../js/vue/vue.min')
let VM=new Vue({
    el: "#app",
    data:{
        name: '计算器',
        num1: 0,
        num2: 0,
        result: 0,
        url: 'http://www.jd.com',
        list: ['张三','李四','王五'],
        user:{'name':'张三','age':13},
        users:[{user:{name:'李四',age:14}},{user:{name:'王五',age:15}}]
    },
    methods: {
        count:function(){
            this.result= Number.parseInt(this.num1)+Number.parseInt(this.num2);
        }
    }
});