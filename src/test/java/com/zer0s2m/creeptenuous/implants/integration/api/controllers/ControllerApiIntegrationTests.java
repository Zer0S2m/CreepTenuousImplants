package com.zer0s2m.creeptenuous.implants.integration.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zer0s2m.creeptenuous.implants.db.models.DeletedObjectStatistic;
import com.zer0s2m.creeptenuous.implants.db.repository.DeletedObjectStatisticRepository;
import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;
import com.zer0s2m.creeptenuous.implants.helpers.UtilsToken;
import com.zer0s2m.creeptenuous.implants.integration.api.data.DataAuthenticationApi;
import com.zer0s2m.creeptenuous.implants.integration.api.exceptions.messages.InvalidTokenMsg;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ControllerApiIntegrationTests {

    @Autowired
    private DeletedObjectStatisticRepository deletedObjectStatisticRepository;

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
                        .content(objectMapper.writeValueAsString(new DataAuthenticationApi(
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
                        .content(objectMapper.writeValueAsString(new DataAuthenticationApi(
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

    @Test
    @Rollback
    public void getStatistics_success() throws Exception {
        deletedObjectStatisticRepository.save(new DeletedObjectStatistic(
                "systemName", "systemPath", TypeObjectDeleted.DIRECTORY));

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/integration/statistics")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DataAuthenticationApi(
                                UtilsToken.generateToken(utilsToken.getPrivateKey()))))
                )
                .andExpect(status().isOk());
    }

}
