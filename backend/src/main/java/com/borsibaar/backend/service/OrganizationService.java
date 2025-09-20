package com.borsibaar.backend.service;

import com.borsibaar.backend.dto.OrganizationRequest;
import com.borsibaar.backend.dto.OrganizationResponse;
import com.borsibaar.backend.entity.Organization;
import com.borsibaar.backend.mapper.OrganizationMapper;
import com.borsibaar.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }


    @Transactional
    public OrganizationResponse create(OrganizationRequest request) {
        Organization organization = organizationMapper.toEntity(request);
        organization.setCreatedAt(OffsetDateTime.now());
        Organization saved = organizationRepository.save(organization);
        return organizationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrganizationResponse getById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Organization not found: " + id));
        return organizationMapper.toResponse(organization);
    }
}
