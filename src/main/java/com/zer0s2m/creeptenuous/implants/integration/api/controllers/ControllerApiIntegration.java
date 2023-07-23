package com.zer0s2m.creeptenuous.implants.integration.api.controllers;

import com.zer0s2m.creeptenuous.implants.containers.ContainerDeletedObjectStatistic;
import com.zer0s2m.creeptenuous.implants.integration.api.data.DataAuthenticationApi;
import com.zer0s2m.creeptenuous.implants.integration.api.exceptions.messages.InvalidTokenMsg;
import com.zer0s2m.creeptenuous.implants.integration.services.JwtService;
import com.zer0s2m.creeptenuous.implants.services.ServiceDeletedObjectStatistic;
import com.zer0s2m.creeptenuous.implants.tasks.RedisCleanTask;
import com.zer0s2m.creeptenuous.implants.tasks.StorageCleanTask;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * The main controller responsible for integration with the main system module
 * <p>Adjustable via environment variables to enable/disable</p>
 */
@RestController
@RequestMapping("/api/v1/integration")
@ConditionalOnExpression("${integration.main-system:false}")
public class ControllerApiIntegration {

    private final JwtService jwtService;

    private final ServiceDeletedObjectStatistic serviceDeletedObjectStatistic;

    private final RedisCleanTask redisCleanTask;

    private final StorageCleanTask storageCleanTask;

    @Autowired
    public ControllerApiIntegration(
            JwtService jwtService,
            ServiceDeletedObjectStatistic serviceDeletedObjectStatistic,
            RedisCleanTask redisCleanTask,
            StorageCleanTask storageCleanTask) {
        this.jwtService = jwtService;
        this.serviceDeletedObjectStatistic = serviceDeletedObjectStatistic;
        this.redisCleanTask = redisCleanTask;
        this.storageCleanTask = storageCleanTask;
    }

    /**
     * Force cleanup of main system components
     * @param data authentication data
     * @throws IOException I/O error
     */
    @PostMapping("/run-clean")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public final void runCleanStorage(
            final @Valid @RequestBody @NotNull DataAuthenticationApi data) throws IOException {
        jwtService.verifyToken(data.token());

        storageCleanTask.storageCleanTask();
        redisCleanTask.clean();
    }

    /**
     * Get statistics on deleted objects
     * @param data authentication data
     * @return statistics
     */
    @PostMapping("/statistics")
    @ResponseStatus(code = HttpStatus.OK)
    public final List<ContainerDeletedObjectStatistic> getStatistics(
            final @Valid @RequestBody @NotNull DataAuthenticationApi data) {
        jwtService.verifyToken(data.token());
        return serviceDeletedObjectStatistic.getStatistics();
    }

    @ExceptionHandler({
            ExpiredJwtException.class,
            UnsupportedJwtException.class,
            MalformedJwtException.class,
            SignatureException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public InvalidTokenMsg handleExceptionValidateToken(@NotNull Exception ignored) {
        return new InvalidTokenMsg("Invalid token", HttpStatus.UNAUTHORIZED.value());
    }

}
