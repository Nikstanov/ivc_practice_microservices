package com.ivc.nikstanov.organizationservice.service.impl;

import com.ivc.nikstanov.organizationservice.dto.OrganizationEvent;
import com.ivc.nikstanov.organizationservice.entity.Organization;
import com.ivc.nikstanov.organizationservice.exception.OrganizationAlreadyExistsException;
import com.ivc.nikstanov.organizationservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.organizationservice.repository.OrganizationRepository;
import com.ivc.nikstanov.organizationservice.service.OrganizationService;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {



    @Value("${spring.kafka.topic.name}")
    private String topic;

    private final OrganizationRepository organizationRepository;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrganizationEvent> kafkaTemplate;

    @Override
    public Organization saveOrganization(Organization organization) {
        organizationRepository.findByOrganizationCode(organization.getOrganizationCode()).ifPresent(value -> {
            throw new OrganizationAlreadyExistsException(String.format("Organization with code %s already exists", value.getOrganizationCode()));
        });
        return organizationRepository.save(organization);
    }

    @Override
    public void deleteOrganizationByCode(String code) {
        Organization organization = organizationRepository.findByOrganizationCode(code).orElseThrow(() -> new ResourceNotFoundException("ORGANIZATION", "organizationCode", code));
        organizationRepository.deleteById(organization.getId());
        OrganizationEvent event = new OrganizationEvent(
                OrganizationEvent.class.getTypeName(),
                OrganizationEvent.ActionsEnum.DELETED.toString(),
                code,
                tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : ""
        );
        kafkaTemplate.send(topic, event);
    }

    @Override
    public Organization findByCode(String code) {
        return organizationRepository.findByOrganizationCode(code).orElseThrow(()-> new ResourceNotFoundException("ORGANIZATIONS", "organizationCode", code));
    }
}
