package com.edh.pod.generator.api.controllers;

import com.edh.pod.generator.api.services.PodsService;
import com.edh.pod.generator.api.services.UsersService;
import com.edh.pod.generator.api.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    public void getPodsAuthNotValidTest(){}

    @Test
    public void getPodsForUsernameAuthValidTest(){}

    @Test
    public void addPodMemberAuthNotValidTest(){}

    @Test
    public void addPodMemberAuthValidTest(){}
}
