package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.PlayGroup;
import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.models.PodMember;
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
        List<PodMember> podMembers = podsService.getPodMembers(token.getBody().getSubject());
        List<Pod> sortedPods = podsService.sortIntoPods(podMembers);

        Map<String, List<Pod>> body = new HashMap<>();
        body.put("pods", sortedPods);

        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/member")
    public ResponseEntity addPodMember(@RequestHeader("Authorization") String authHeader, @RequestBody PodMember podMember){
        boolean isTokenValid = usersService.isTokenValid(authHeader);
        if(!isTokenValid){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "The provided token isn't valid, please login again");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        Jws<Claims> token = usersService.decodeToken(authHeader);

        List<PodMember> podMembers = podsService.addPodMember(podMember, token.getBody().getSubject());
        List<Pod> sortedPods = podsService.sortIntoPods(podMembers);

        Map<String, List<Pod>> body = new HashMap<>();
        body.put("pods", sortedPods);

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/generate/{name}")
    public ResponseEntity generatePods(@RequestHeader("Authorization") String authHeader, @PathVariable("name") String podName){
        boolean isTokenValid = usersService.isTokenValid(authHeader);
        if(!isTokenValid){
            Map<String, String> body = new HashMap<>();
            body.put("errorMessage", "The provided token isn't valid, please login again");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        Jws<Claims> token = usersService.decodeToken(authHeader);
        List<PodMember> podMembers = podsService.getPodMembers(token.getBody().getSubject());
        List<Pod> sortedPods = podsService.sortIntoPods(podMembers);
        Pod podInfo = podsService.getPodByName(sortedPods, podName);
        List<PlayGroup> playGroups = podsService.sortIntoPlayGroups(podInfo);

        Map<String, List<PlayGroup>> body = new HashMap<>();
        body.put("playGroups", playGroups);

        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPod(@PathVariable("id") String podId){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity editPod(@RequestBody PodMember podMember, @PathVariable("id") String podId){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePod(@PathVariable("id") String podId){
        return ResponseEntity.ok().build();
    }
}
