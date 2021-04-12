package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.PodMember;
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
        List<PodMember> podMembers = new ArrayList<>();

        PodMember podMemberOneMember = new PodMember();
        podMemberOneMember.setId(1);
        podMemberOneMember.setOwner("sean");
        podMemberOneMember.setMember("terrell");
        podMemberOneMember.setMemberEmail("memberemail@gmail.com");
        podMemberOneMember.setName("name1");

        PodMember podMemberTwoMember = new PodMember();
        podMemberTwoMember.setId(1);
        podMemberTwoMember.setOwner("sean");
        podMemberTwoMember.setMember("victor");
        podMemberTwoMember.setMemberEmail("memberemail@gmail.com");
        podMemberTwoMember.setName("name2");

        podMembers.add(podMemberOneMember);
        podMembers.add(podMemberTwoMember);

        List<PodMember> podMemberOne = new ArrayList<>();
        podMemberOne.add(podMembers.get(0));

        List<PodMember> podMemberTwo = new ArrayList<>();
        podMemberTwo.add(podMembers.get(1));

        List<List<PodMember>> expectedResults = new ArrayList<>();
        expectedResults.add(podMemberOne);
        expectedResults.add(podMemberTwo);

        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.getPods(any(String.class))).thenReturn(podMembers);
        when(podsService.sortIntoPods(any())).thenReturn(expectedResults);

        mockMvc.perform(get("/pods")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pods").isArray());
    }

    @Test
    public void addPodMemberAuthNotValidTest() throws Exception {
        PodMember podMember = new PodMember();
        podMember.setId(1);
        podMember.setOwner("sean");
        podMember.setMember("terrell");
        podMember.setMemberEmail("memberemail@gmail.com");
        podMember.setName("name1");

        when(usersService.isTokenValid(any(String.class))).thenReturn(false);

        mockMvc.perform(post("/pods/member")
                .header("Authorization", "token")
                .content(testUtils.asJsonString(podMember))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("The provided token isn't valid, please login again"));
    }

    @Test
    public void addPodMemberAuthValidTest() throws Exception {
        PodMember podMember = new PodMember();
        podMember.setId(1);
        podMember.setOwner("sean");
        podMember.setMember("terrell");
        podMember.setMemberEmail("memberemail@gmail.com");
        podMember.setName("name1");

        List<PodMember> podMembers = new ArrayList<>();
        podMembers.add(podMember);

        List<List<PodMember>> sortedPods = new ArrayList<>();
        sortedPods.add(podMembers);

        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.addPodMember(any(), any())).thenReturn(podMembers);
        when(podsService.sortIntoPods(any())).thenReturn(sortedPods);

        mockMvc.perform(post("/pods/member")
                .header("Authorization", "token")
                .content(testUtils.asJsonString(podMember))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pods").isArray());
    }
}
