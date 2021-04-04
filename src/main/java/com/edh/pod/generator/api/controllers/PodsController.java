package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.services.PodsService;
import com.edh.pod.generator.api.services.UsersService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        boolean isTokenValid = usersService.isTokenValid(authHeader);
        if(!isTokenValid){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "The provided token isn't valid, please login again");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        Jws<Claims> token = usersService.decodeToken(authHeader);
        List<Pod> pods = podsService.getPods(token.getBody().getSubject());
        List<List<Pod>> sortedPods = podsService.sortIntoPods(pods);

        Map<String, List<List<Pod>>> body = new HashMap<>();
        body.put("pods", sortedPods);

        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/member")
    public ResponseEntity addPodMember(@RequestHeader("Authorization") String authHeader, @RequestBody Pod pod){
        boolean isTokenValid = usersService.isTokenValid(authHeader);
        if(!isTokenValid){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "The provided token isn't valid, please login again");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        List<Pod> pods = podsService.addPodMember(pod);
        List<List<Pod>> sortedPods = podsService.sortIntoPods(pods);

        Map<String, List<List<Pod>>> body = new HashMap<>();
        body.put("pods", sortedPods);

        return ResponseEntity.ok().body(body);
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
