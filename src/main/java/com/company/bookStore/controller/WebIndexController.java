package com.company.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebIndexController {

    @GetMapping("/")
    public String viewHomePage(){
        return "index";
    }
}
