package com.flinter.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class BasicController {

    @GetMapping("/")
    public String hello() {
        return "index.html";
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        return "about";
    }

    @GetMapping("/mypage")
    @ResponseBody
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/date")
    @ResponseBody
    public String date() {
        return LocalDateTime.now().toString();
    }


}
