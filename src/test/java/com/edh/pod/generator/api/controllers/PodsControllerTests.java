package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.Pod;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

        List<PodMember> podOneMembers = new ArrayList<>();
        podOneMembers.add(podMembers.get(0));
        Pod podOne = new Pod(podOneMembers, "name1");

        List<PodMember> podTwoMembers = new ArrayList<>();
        podTwoMembers.add(podMembers.get(1));
        Pod podTwo = new Pod(podTwoMembers, "name2");

        List<Pod> expectedResults = new ArrayList<>();
        expectedResults.add(podOne);
        expectedResults.add(podTwo);

        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.getPodMembers(any(String.class))).thenReturn(podMembers);
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

        List<Pod> sortedPods = new ArrayList<>();
        sortedPods.add(new Pod(podMembers, "name1"));

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

    @Test
    public void generatePlayGroupAuthNotValidTest() throws Exception {
        when(usersService.isTokenValid(any(String.class))).thenReturn(false);


        mockMvc.perform(get("/pods/generate/name1")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("The provided token isn't valid, please login again"));
    }

    @Test
    public void generatePlayGroupAuthValidTest() throws Exception {
        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.getPodMembers(any())).thenReturn(new ArrayList<>());
        when(podsService.sortIntoPods(any())).thenReturn(new ArrayList<>());
        when(podsService.getPodByName(any(), any())).thenReturn(new Pod(new ArrayList<>(), "Pod1"));
        when(podsService.sortIntoPlayGroups(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/pods/generate/name1")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.playGroups").isArray());
    }

    @Test
    public void deletePodAuthNotValidTest() throws Exception {
        when(usersService.isTokenValid(any(String.class))).thenReturn(false);

        mockMvc.perform(delete("/pods/name1")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("The provided token isn't valid, please login again"));
    }

    @Test
    public void deletePodAuthValidTest() throws Exception {
        PodMember podMember = new PodMember();
        podMember.setId(1);
        podMember.setOwner("sean");
        podMember.setMember("terrell");
        podMember.setMemberEmail("memberemail@gmail.com");
        podMember.setName("name1");

        List<PodMember> podMembers = new ArrayList<>();
        podMembers.add(podMember);

        List<Pod> sortedPods = new ArrayList<>();
        sortedPods.add(new Pod(podMembers, "name1"));

        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.deletePod(any(), any())).thenReturn(podMembers);
        when(podsService.sortIntoPods(any())).thenReturn(sortedPods);

        mockMvc.perform(delete("/pods/name1")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pods").isArray());
    }

    @Test
    public void deletePodMemberAuthNotValidTest() throws Exception {
        when(usersService.isTokenValid(any(String.class))).thenReturn(false);

        mockMvc.perform(delete("/pods/name1/member/memberName")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("The provided token isn't valid, please login again"));
    }

    @Test
    public void deletePodMemberAuthValidTest() throws Exception {
        PodMember podMember = new PodMember();
        podMember.setId(1);
        podMember.setOwner("sean");
        podMember.setMember("terrell");
        podMember.setMemberEmail("memberemail@gmail.com");
        podMember.setName("name1");

        List<PodMember> podMembers = new ArrayList<>();
        podMembers.add(podMember);

        List<Pod> sortedPods = new ArrayList<>();
        sortedPods.add(new Pod(podMembers, "name1"));

        String encodedToken = testUtils.generateToken("sean");
        Jws<Claims> decodedToken = testUtils.decodeToken(encodedToken);

        when(usersService.isTokenValid(any(String.class))).thenReturn(true);
        when(usersService.decodeToken(any())).thenReturn(decodedToken);
        when(podsService.deletePodMember(any(), any(), any())).thenReturn(podMembers);
        when(podsService.sortIntoPods(any())).thenReturn(sortedPods);

        mockMvc.perform(delete("/pods/name1/member/sean")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.pods").isArray());
    }
}
