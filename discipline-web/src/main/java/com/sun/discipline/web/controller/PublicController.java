package com.sun.discipline.web.controller;

import com.sun.discipline.utils.math.UUIDUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunjian.
 */
@RestController
@RequestMapping("/public")
public class PublicController
{
    @RequestMapping("/hello")
    public String sayHello(){
        return "This is public Hello World!      "+ UUIDUtils.getUUID();
    }
}
