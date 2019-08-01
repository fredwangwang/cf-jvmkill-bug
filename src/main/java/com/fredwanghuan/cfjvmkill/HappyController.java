package com.fredwanghuan.cfjvmkill;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HappyController {
    @GetMapping("/")
    public String home() {
        return "hello!";
    }
}
