package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.Pod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/pods")
public class PodsController {

    @GetMapping()
    public ResponseEntity getPods(){
        return ResponseEntity.ok().build();
    }

    @PostMapping()
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
