package com.ivc.nikstanov.organizationservice.utill;

import com.ivc.nikstanov.organizationservice.dto.OrganizationDto;
import com.ivc.nikstanov.organizationservice.entity.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {

    OrganizationDto toOrganizationDto(Organization organization);
    Organization toOrganization(OrganizationDto organization);
}
