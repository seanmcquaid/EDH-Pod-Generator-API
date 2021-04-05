package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.models.User;
import com.edh.pod.generator.api.services.UsersService;
import com.edh.pod.generator.api.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    private final TestUtils testUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersControllerTests() {
        this.testUtils = new TestUtils();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void registerAlreadyExistingUserTest() throws Exception {
        User testUser = new User();
        testUser.setId(1);
        testUser.setUsername("sean");
        testUser.setPassword("password");

        when(usersService.doesUserExist("sean")).thenReturn(true);

        mockMvc.perform(post("/users/register")
                .content(testUtils.asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("This username has already been created, please try another one"));
    }


    @Test
    public void registerNewUserTest() throws Exception {
        User testUser = new User();
        testUser.setId(1);
        testUser.setUsername("sean");
        testUser.setPassword("password");

        when(usersService.doesUserExist("sean")).thenReturn(false);
        when(usersService.encodePassword(any(String.class))).thenReturn(bCryptPasswordEncoder.encode(testUser.getPassword()));
        when(usersService.addUser(any(String.class), any(String.class))).thenReturn(testUser);
        when(usersService.generateToken(any(String.class))).thenReturn("sean");

        mockMvc.perform(post("/users/register")
                .content(testUtils.asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("sean"));
    }

    @Test
    public void loginUserDoesNotExistText() throws Exception {
        User testUser = new User();
        testUser.setId(1);
        testUser.setUsername("sean");
        testUser.setPassword("password");

        when(usersService.doesUserExist("sean")).thenReturn(false);

        mockMvc.perform(post("/users/login")
                .content(testUtils.asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("This user doesn't exist, please try another one"));
    }

    @Test
    public void loginPasswordIsNotCorrectTest() throws Exception {
        User testUser = new User();
        testUser.setId(1);
        testUser.setUsername("sean");
        testUser.setPassword("password1");

        User testUserWithEncodedPassword = new User();
        testUserWithEncodedPassword.setId(1);
        testUserWithEncodedPassword.setUsername("sean");
        testUserWithEncodedPassword.setPassword(bCryptPasswordEncoder.encode("password"));

        when(usersService.doesUserExist("sean")).thenReturn(true);
        when(usersService.getUserInfoByUsername("sean")).thenReturn(testUserWithEncodedPassword);
        when(usersService.isPasswordCorrect(any(String.class), any(String.class))).thenReturn(false);

        mockMvc.perform(post("/users/login")
                .content(testUtils.asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("This password isn't correct, please try another one"));
    }

    @Test
    public void loginExistingUserTest() throws Exception {
        User testUser = new User();
        testUser.setId(1);
        testUser.setUsername("sean");
        testUser.setPassword("password");

        User testUserWithEncodedPassword = new User();
        testUserWithEncodedPassword.setId(1);
        testUserWithEncodedPassword.setUsername("sean");
        testUserWithEncodedPassword.setPassword(bCryptPasswordEncoder.encode("password"));

        when(usersService.doesUserExist("sean")).thenReturn(true);
        when(usersService.getUserInfoByUsername("sean")).thenReturn(testUserWithEncodedPassword);
        when(usersService.isPasswordCorrect(any(String.class), any(String.class))).thenReturn(true);
        when(usersService.generateToken(any(String.class))).thenReturn("sean");

        mockMvc.perform(post("/users/login")
                .content(testUtils.asJsonString(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("sean"));
    }
}
