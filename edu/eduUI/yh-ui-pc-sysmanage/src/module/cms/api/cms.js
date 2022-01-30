import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre;
//请求服务器查询cmsPage列表的方法
export const page_list=(page,size,params)=>{
//http.requestQuickGet("http://localhost:31001/cms/page/list"+page+"/"+size)
  let query = querystring.stringify(params);
  return http.requestQuickGet("http://localhost:11000/api/cms/page/list"+"/"+page+"/"+size+"?"+query)

}
export const site_list=()=>{
  return http.requestQuickGet("http://localhost:11000/api/cms/site/list")
}
export const page_add=(pageForm)=>{
  return http.requestPost("api/cms/page/add",pageForm);
}
export const page_edit=(pageId,pageForm)=>{
  return http.requestPut("api/cms/page/edit/"+pageId,pageForm);
}
export const page_get=pageId=>{
  return http.requestQuickGet("api/cms/page/get/"+pageId);
}
export const page_del=pageId=>{
  return http.requestDelete("api/cms/page/del/"+pageId);
}
export const page_postPage=pageId => {
  return http.requestPost(apiUrl+'/cms/page/postPage/'+pageId)
}

