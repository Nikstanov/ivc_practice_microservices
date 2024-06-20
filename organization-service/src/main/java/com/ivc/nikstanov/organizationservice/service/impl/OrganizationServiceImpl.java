package com.ivc.nikstanov.organizationservice.service.impl;

import com.ivc.nikstanov.organizationservice.entity.Organization;
import com.ivc.nikstanov.organizationservice.exception.OrganizationAlreadyExistsException;
import com.ivc.nikstanov.organizationservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.organizationservice.repository.OrganizationRepository;
import com.ivc.nikstanov.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Override
    public Organization saveOrganization(Organization organization) {
        organizationRepository.findByOrganizationCode(organization.getOrganizationCode()).ifPresent(value -> {
            throw new OrganizationAlreadyExistsException(String.format("Organization with code %s already exists", value.getOrganizationCode()));
        });
        return organizationRepository.save(organization);
    }

    @Override
    public Organization findByCode(String code) {
        return organizationRepository.findByOrganizationCode(code).orElseThrow(()-> new ResourceNotFoundException("ORGANIZATIONS", "organizationCode", code));
    }
}
