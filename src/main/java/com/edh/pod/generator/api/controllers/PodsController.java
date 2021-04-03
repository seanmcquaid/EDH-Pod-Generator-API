package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.services.PodsService;
import com.edh.pod.generator.api.services.UsersService;
import com.edh.pod.generator.api.utils.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pods")
public class PodsController {

    @Autowired
    private PodsService podsService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity getPods(@RequestHeader("Authorization") String authHeader){
        System.out.println();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity addPod(@RequestBody Pod pod){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getPod(@PathVariable("id") String podId){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity editPod(@RequestBody Pod pod, @PathVariable("id") String podId){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePod(@PathVariable("id") String podId){
        return ResponseEntity.ok().build();
    }
}
