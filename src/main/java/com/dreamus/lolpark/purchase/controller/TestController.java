package com.dreamus.lolpark.purchase.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class TestController {

    @RequestMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Collections.singletonMap("test" , "1"));
    }
}
