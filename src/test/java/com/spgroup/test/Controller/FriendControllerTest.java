package com.spgroup.test.Controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String normal;

    @Before
    public void init() {
        normal = "{" +
                "\"friends\" : [\"1@1.com\",\"2@2.com\"]" +
                "}";
    }

    @Test
    public void create() {

        try {
            // normal request
            MvcResult result = mockMvc.perform(post("/friend/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(normal)).andReturn();
            Assert.assertEquals(200, result.getResponse().getStatus());


        } catch (Exception e) {
            Assert.fail();
        }
    }

}



