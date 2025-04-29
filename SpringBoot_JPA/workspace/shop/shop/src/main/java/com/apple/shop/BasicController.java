package com.apple.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@Controller
public class BasicController {
    @GetMapping("/")
    String hello() {
        return "index.html"; // 기본 경로는 static 폴더
    }

    @GetMapping("/about")
    @ResponseBody // html 파일 같은 것을 전송할 때는 제거 필요
    String about() {
        return "피싱사이트에요";
    }

    @GetMapping("/mypage")
    @ResponseBody
    String mypage() {
        return "마이페이지입니다~";
    }

    @GetMapping("/date")
    @ResponseBody
    String date() {
        return ZonedDateTime.now().toString();
    }
}