package com.zer0s2m.creeptenuous.implants.integration.api.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataAuthenticationApi(

        @NotNull(message = "Please enter a token (not NULL)")
        @NotBlank(message = "Please enter a token")
        String token

) { }
