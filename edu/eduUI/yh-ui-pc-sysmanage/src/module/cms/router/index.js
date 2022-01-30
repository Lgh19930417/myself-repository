import Home from '@/module/home/page/home.vue';
import pageList from '@/module/cms/page/page_list.vue';
import pageAdd from '@/module/cms/page/page_add.vue';
import pageEdit from '@/module/cms/page/page_edit.vue';
export default [{
    path: '/',
    component: Home,
    name: '系统管理首页',
    hidden: false,
  children:[
    {path: 'cms/page/list',component:pageList,name:'cms列表',hidden:false},
    {path: '/cms/page/add/',component:pageAdd,name:'cms添加',hidden:true},
    {path: '/cms/page/edit/:pageId',component:pageEdit,name:'cms修改',hidden:true}
  ]

  }
]
