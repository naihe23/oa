package com.design.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/test")
    public void editor(HttpServletResponse response)throws Exception{
        System.out.println("testest");
    }
}
