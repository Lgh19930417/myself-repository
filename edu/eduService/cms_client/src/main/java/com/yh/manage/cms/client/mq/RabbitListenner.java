package com.yh.manage.cms.client.mq;

import com.alibaba.fastjson.JSON;
import com.lxw.framework.exception.ExceptionCast;
import com.lxw.framework.model.response.CommonCode;
import com.yh.manage.cms.client.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitListenner {
    @Autowired
    private PageService pageService;

    @RabbitListener(queues = "${yh.mq.queue}")
    public void postPage(String msg){
        if(StringUtils.isEmpty(msg)){
            ExceptionCast.cast(CommonCode.ILLARGS);
        }
        System.out.println(msg);
        Map map = JSON.parseObject(msg, Map.class);
        String  pageId = map.get("pageId").toString();
        System.out.println(pageId);
        pageService.downloadHtmlByCmsPageId(pageId);
    }
}
