package com.edh.pod.generator.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/pods")
public class PodsController {

    @GetMapping()
    public ResponseEntity getPods(){
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity addPod(){
        return ResponseEntity.ok().build();
    }
}
