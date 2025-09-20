package com.borsibaar.backend.controller;

import com.borsibaar.backend.dto.OrganizationRequest;
import com.borsibaar.backend.dto.OrganizationResponse;
import com.borsibaar.backend.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationResponse create(@RequestBody @Valid OrganizationRequest request) {
        return organizationService.create(request);
    }

    @GetMapping("/{id}")
    public OrganizationResponse get(@PathVariable Long id) {
        return organizationService.getById(id);
    }

}
