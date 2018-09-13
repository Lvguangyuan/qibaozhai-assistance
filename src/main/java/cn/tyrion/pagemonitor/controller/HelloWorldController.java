package cn.tyrion.pagemonitor.controller;

import cn.tyrion.pagemonitor.domain.ParamTemplate;
import cn.tyrion.pagemonitor.domain.WeChatMsgTemplate;
import cn.tyrion.pagemonitor.service.WechatService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, ParamTemplate> data = new HashMap<>();
        data.put("first", new ParamTemplate("《Java編程思想》", ""));
        data.put("keyword1", new ParamTemplate("78元", ""));
        data.put("keyword2", new ParamTemplate("44元", ""));
        data.put("remark", new ParamTemplate("34元", ""));

        WeChatMsgTemplate weChatMsgTemplate = new WeChatMsgTemplate();
        weChatMsgTemplate.setTemplateId("QOgL8pfPBbT73oXj48cw319fmXtc-vPt_q7EpJzPIQg");
        weChatMsgTemplate.setToUser("onnPV1T4Z9PiBTGwvCx3cDn_6EAY");
        weChatMsgTemplate.setData(data);

        RestTemplate restTemplate = new RestTemplate();
        String access_token = wechatService.getGlobalAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";


        UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("access_token", access_token);
        url = urlBuilder.build().toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<WeChatMsgTemplate> entity = new HttpEntity<>(weChatMsgTemplate, httpHeaders);

        String response = restTemplate.postForObject(url, entity, String.class);

        System.out.println(response);


        return "Success";
    }
}
