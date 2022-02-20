package com.dahye.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping //가장 기본은 ("") 작성하지 않도록 한다.
    public String index(){

        return  "index";
    }

}
