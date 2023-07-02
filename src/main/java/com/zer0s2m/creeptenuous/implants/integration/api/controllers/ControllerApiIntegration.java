package com.zer0s2m.creeptenuous.implants.integration.api.controllers;

import com.zer0s2m.creeptenuous.implants.integration.services.JwtService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The main controller responsible for integration with the main system module
 * <p>Adjustable via environment variables to enable/disable</p>
 */
@RestController
@RequestMapping("/api/v1/integration")
@ConditionalOnExpression("${integration.main-system:false}")
public class ControllerApiIntegration {

    private final JwtService jwtService;

    @Autowired
    public ControllerApiIntegration(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/run-clean")
    @ResponseStatus(code = HttpStatus.OK)
    public final @NotNull String runCleanStorage() {
        jwtService.verifyToken("token");
        return "Hello";
    }

}
