<template>
  <div>
    <el-form :model="params">
      <el-select v-model="params.siteId" placeholder="请选择站点">
        <el-option
          v-for="item in siteList"
          :key="item.siteId"
          :label="item.siteName"
          :value="item.siteId">
        </el-option>
      </el-select>
      页面别名：<el-input v-model="params.pageAliase" style="width: 100px"></el-input>
      <el-button type="primary" v-on:click="query" size="small">查询</el-button>
      <router-link class="mui-tab-item" :to="{path:'/cms/page/add/',query:{
          page: this.params.page,
          siteId: this.params.siteId}}">
        <el-button  type="primary" size="small">新增页面</el-button>
      </router-link>
    </el-form>
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="pageName" label="页面名称" width="120">
      </el-table-column>
      <el-table-column prop="pageAliase" label="别名" width="120">
      </el-table-column>
      <el-table-column prop="pageType" label="页面类型" width="150">
      </el-table-column>
      <el-table-column prop="pageWebPath" label="访问路径" width="250">
      </el-table-column>
      <el-table-column prop="pagePhysicalPath" label="物理路径" width="250">
      </el-table-column>
      <el-table-column prop="pageCreateTime" label="创建时间" width="180" >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="cmsPageEdit(scope.row.pageId)" type="text" size="small">编辑</el-button>
          <el-button @click="cmsPageDel(scope.row.pageId)" type="text" size="small">删除</el-button>
          <el-button @click="preview(scope.row.pageId)" type="text" size="small">页面预览</el-button>
          <el-button @click="pagePost(scope.row.pageId)" type="text" size="small">页面发布</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="params.size"
      :current-page="params.page"
      style="float:right"
      @current-change="handleCurrentChange"
    >
    </el-pagination>



  </div>
</template>

<script>
import * as cmsApi from "../api/cms";

export default {
  methods:{
    cmsPageDel:function (pageId){
      this.$confirm('确认删除此页面吗?', '提示', {}).then(() => {
        cmsApi.page_del(pageId).then((res)=>{
        if(res.success){
          this.$message({
            type: 'success', message: '删除成功!'
          });
          //查询页面
          this.query()
        }else{
          this.$message({ type: 'error',
            message: '删除失败!'
          });
        }
      })
      })
    },
    preview:function (pageId){
      window.open("http://www.gs.com:31001/cms/preview/"+pageId);
    },
    cmsPageEdit:function (pageId){
      this.$router.push({
        path: '/cms/page/edit/'+pageId, query: {
          page: this.$route.query.page,
          siteId:this.$route.query.siteId
        }
      })
    },
    pagePost:function (pageId){
      this.$confirm('确认发布该页面吗?','提示',{
      }).then(()=>{
        cmsApi.page_postPage(pageId).then((res)=>{
          if(res.success){
            console.log('发布页面id='+pageId);
            this.$message.success('发布成功，请稍后查看结果');
          }else{
            this.$message.error('发布失败');
          }
        });
      }).catch(()=>{
      });

    },
    query:function () {
      cmsApi.page_list(this.params.page,this.params.size,this.params).then(res=>{
        this.total=res.queryResult.total;
        this.list=res.queryResult.list;

      })
      //alert("query")
    },
    handleCurrentChange(val) {
      this.params.page=val;
      this.query();
      //console.log(`当前页: ${val}`);
    },
    // querySite:function (){
    //   cmsApi.site_list().then(res=>{
    //     this.siteList=
    //   })
    // }
  },
  created() {
    this.params.siteId=this.$route.query.siteId||'';
    this.params.page=Number.parseInt(this.$route.query.page||1);
  },
  mounted() {
    this.siteList=[{"siteId":"5a751fab6abb5044e0d19ea1","siteName":"门户主站"},{"siteId":"616435644b706109ac32cd5a","siteName":"课程详情"}]
    this.query();
  },
  data() {
    return {
      total:0,
      siteList:'',
      params:{
        page:1,
        size:10,
        siteId:'',
        pageAliase:''
      },
      list: []
    }
  }
}
</script>

<style>

</style>
