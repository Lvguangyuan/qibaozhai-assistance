package cn.tyrion.pagemonitor.controller;

import cn.tyrion.pagemonitor.domain.ParamTemplate;
import cn.tyrion.pagemonitor.domain.WeChatMsgTemplate;
import cn.tyrion.pagemonitor.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class HelloWorldController {

    @Autowired
    private WechatService wechatService;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }


    @RequestMapping("/send")
    public String send() {

        List<ParamTemplate> paramTemplates = new ArrayList<>();
        paramTemplates.add(new ParamTemplate("first","《Java編程思想》",""));
        paramTemplates.add(new ParamTemplate("keyword1","78元",""));
        paramTemplates.add(new ParamTemplate("keyword2","44元",""));
        paramTemplates.add(new ParamTemplate("remark","降价34元",""));

        WeChatMsgTemplate weChatMsgTemplate = new WeChatMsgTemplate();
        weChatMsgTemplate.setTemplateId("QOgL8pfPBbT73oXj48cw319fmXtc-vPt_q7EpJzPIQg");
        weChatMsgTemplate.setToUser("onnPV1T4Z9PiBTGwvCx3cDn_6EAY");
        weChatMsgTemplate.setParamTemplateList(paramTemplates);

        RestTemplate restTemplate = new RestTemplate();
        String access_token = wechatService.getGlobalAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";


        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("access_token", access_token);
        url = urlBuilder.build().toUriString();


        System.out.println(weChatMsgTemplate.toJSON());
        String response = restTemplate.postForObject(url, weChatMsgTemplate.toJSON(), String.class);
        System.out.println(response);


        return "Success";
    }
}
