package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.Pod;
import com.edh.pod.generator.api.services.PodsService;
import com.edh.pod.generator.api.services.UsersService;
import com.edh.pod.generator.api.utils.TestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest(PodsController.class)
public class PodsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private PodsService podsService;

    private final TestUtils testUtils;

    public PodsControllerTests() {
        this.testUtils = new TestUtils();
    }

    @Test
    public void getPodsAuthNotValidTest() throws Exception {
        when(usersService.isTokenValid(any(String.class))).thenReturn(false);

        mockMvc.perform(get("/pods")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("The provided token isn't valid, please login again"));
    }

    @Test
    public void getPodsForUsernameAuthValidTest() throws Exception {
        List<Pod> pods = new ArrayList<>();

        Pod podOneMember = new Pod();
        podOneMember.setId(1);
        podOneMember.setOwner("sean");
        podOneMember.setMember("terrell");
        podOneMember.setMemberEmail("memberemail@gmail.com");
        podOneMember.setSpellTableUrl("spelltable.com");
        podOneMember.setName("name1");

        Pod podTwoMember = new Pod();
        podTwoMember.setId(1);
        podTwoMember.setOwner("sean");
        podTwoMember.setMember("victor");
        podTwoMember.setMemberEmail("memberemail@gmail.com");
        podTwoMember.setSpellTableUrl("spelltable.com");
        podTwoMember.setName("name2");

        pods.add(podOneMember);
        pods.add(podTwoMember);

        List<Pod> podOne = new ArrayList<>();
        podOne.add(pods.get(0));

        List<Pod> podTwo = new ArrayList<>();
        podTwo.add(pods.get(1));

        List<List<Pod>> expectedResults = new ArrayList<>();
        expectedResults.add(podOne);
        expectedResults.add(podTwo);

        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.getPods(any(String.class))).thenReturn(pods);
        when(podsService.sortIntoPods(any())).thenReturn(expectedResults);

        mockMvc.perform(get("/pods")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pods").isArray());
    }

    @Test
    public void addPodMemberAuthNotValidTest() throws Exception {
        Pod pod = new Pod();
        pod.setId(1);
        pod.setOwner("sean");
        pod.setMember("terrell");
        pod.setMemberEmail("memberemail@gmail.com");
        pod.setSpellTableUrl("spelltable.com");
        pod.setName("name1");

        when(usersService.isTokenValid(any(String.class))).thenReturn(false);

        mockMvc.perform(post("/pods/member")
                .header("Authorization", "token")
                .content(testUtils.asJsonString(pod))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("The provided token isn't valid, please login again"));
    }

    @Test
    public void addPodMemberAuthValidTest() throws Exception {
        Pod pod = new Pod();
        pod.setId(1);
        pod.setOwner("sean");
        pod.setMember("terrell");
        pod.setMemberEmail("memberemail@gmail.com");
        pod.setSpellTableUrl("spelltable.com");
        pod.setName("name1");

        List<Pod> pods = new ArrayList<>();
        pods.add(pod);

        List<List<Pod>> sortedPods = new ArrayList<>();
        sortedPods.add(pods);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(podsService.addPodMember(any())).thenReturn(pods);
        when(podsService.sortIntoPods(any())).thenReturn(sortedPods);

        mockMvc.perform(post("/pods/member")
                .header("Authorization", "token")
                .content(testUtils.asJsonString(pod))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pods").isArray());
    }
}
