package com.borsibaar.backend.dto;

import java.time.OffsetDateTime;

public record OrganizationResponse(
        Long id,
        String name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
