package com.whize.search.controller;

import com.google.gson.Gson;
import com.whize.search.config.EmbeddedRedisConfiguration;
import com.whize.search.dto.main.UserDto;
import com.whize.search.entity.User;
import com.whize.search.repo.UserJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        userJpaRepo.save(User.builder().uid("whize").name("test").password(passwordEncoder.encode("1234")).roles(Collections.singletonList("ROLE_USER")).build());
    }

    @Test
    public void signin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UserDto userDto = UserDto.builder()
                .id("whize")
                .password("1234")
                .build();

        mockMvc.perform(post("/v1/signin").content(gson.toJson(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists());
    }

}
