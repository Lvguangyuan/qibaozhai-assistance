package cn.tyrion.pagemonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/page")
@Controller
public class PageController {

    @RequestMapping("/index")
    public String toIndex() {
        return "/index";
    }
}
