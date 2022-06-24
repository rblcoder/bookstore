package com.company.bookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebIndexController {

    @GetMapping(value = {"", "home"})
    public String viewHomePage() {
        return "index";
    }


}
