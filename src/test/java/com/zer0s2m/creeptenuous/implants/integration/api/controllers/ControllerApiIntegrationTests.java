package com.zer0s2m.creeptenuous.implants.integration.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zer0s2m.creeptenuous.implants.helpers.UtilsToken;
import com.zer0s2m.creeptenuous.implants.integration.api.data.DataRunCleanStorageApi;
import com.zer0s2m.creeptenuous.implants.integration.api.exceptions.messages.InvalidTokenMsg;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ControllerApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private final UtilsToken utilsToken = new UtilsToken();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void runClean_success() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/integration/run-clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DataRunCleanStorageApi(
                                UtilsToken.generateToken(utilsToken.getPrivateKey()))))
                )
                .andExpect(status().isAccepted());
    }

    @Test
    public void runClean_fail_invalidToken() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/integration/run-clean")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DataRunCleanStorageApi(
                                "invalidToken")))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(
                                new InvalidTokenMsg(
                                        "Invalid token",
                                        HttpStatus.UNAUTHORIZED.value()))
                ));
    }

}
