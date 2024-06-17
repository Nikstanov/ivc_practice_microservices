package com.ivc.nikstanov.organizationservice.service;

import com.ivc.nikstanov.organizationservice.entity.Organization;

public interface OrganizationService {

    Organization saveOrganization(Organization organizationDto);
    Organization findByCode(String code);
}
