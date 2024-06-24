package com.ivc.nikstanov.organizationservice.service;

import com.ivc.nikstanov.organizationservice.entity.Organization;

public interface OrganizationService {

    Organization saveOrganization(Organization organizationDto);
    void deleteOrganizationByCode(String code);
    Organization findByCode(String code);
}
