package com.github.MeghanshBansal.myJournal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("/ping")
    public static String ping(){
        return "pong";
    }
}
