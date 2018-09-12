package cn.tyrion.pagemonitor.controller;

import cn.tyrion.pagemonitor.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private WechatService wechatService;

    @RequestMapping("/check")
    public String checkSignature(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                                 @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        String publicAccountToken = "d75a1656c07611e7abc4cec278b6b50a";
        String openId = "onnPV1T4Z9PiBTGwvCx3cDn_6EAY";

        if (wechatService.validate(signature, timestamp, nonce, publicAccountToken)) {
            return echostr;
        }
        return "Validation Signature Failure.";
    }

}
