package com.borsibaar.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record OrganizationRequest(
        @NotBlank String name
) {
}
