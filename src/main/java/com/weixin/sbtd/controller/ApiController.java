package com.weixin.sbtd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/docs")
    public String getDocs(@RequestParam(required = false) String docName) {
        return "ok";
    }
}
