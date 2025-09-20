package com.borsibaar.backend.mapper;

import com.borsibaar.backend.dto.OrganizationRequest;
import com.borsibaar.backend.dto.OrganizationResponse;
import com.borsibaar.backend.entity.Organization;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)  // set in service
    @Mapping(target = "updatedAt", ignore = true)  // set in service
    Organization toEntity(OrganizationRequest request);

    OrganizationResponse toResponse(Organization organization);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Organization target, OrganizationRequest source);
}
