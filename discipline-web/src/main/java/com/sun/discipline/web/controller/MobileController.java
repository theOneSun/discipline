package com.sun.discipline.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjian.
 */
@RequestMapping("/mobile")
@RestController
public class MobileController
{
    @RequestMapping("/hello")
    public String helloMobile(){
        return "hello mobile";
    }
}
