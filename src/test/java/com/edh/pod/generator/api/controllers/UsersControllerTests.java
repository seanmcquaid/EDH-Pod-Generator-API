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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    private TestUtils testUtils;

    public UsersControllerTests() {
        this.testUtils = new TestUtils();
    }

    @Test
    public void registerAlreadyExistingUserTest() throws Exception {
        when(usersService.doesUserExist("sean")).thenReturn(true);

        mockMvc.perform(post("/users/register")
                .content(testUtils.asJsonString(new User(1, "sean", "password")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("This username has already been created, please try another one"));
    }


    @Test
    public void registerNewUserTest(){}
}
