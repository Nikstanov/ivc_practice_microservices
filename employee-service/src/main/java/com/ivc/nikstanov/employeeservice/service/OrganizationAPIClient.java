package com.ivc.nikstanov.employeeservice.service;

import com.ivc.nikstanov.employeeservice.dto.OrganizationDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface OrganizationAPIClient {

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DepartmentAPIClient.class);

    @GetMapping("api/organizations/{organization-code}")
    @CircuitBreaker(name = "ORGANIZATION-SERVICE", fallbackMethod = "getOrganizationExceptionHandler")
    OrganizationDto getOrganization(@PathVariable("organization-code") String code);

    default OrganizationDto getOrganizationExceptionHandler(String code, Throwable cause){
        log.error("Can't reach organization-service or organization with code {} not exists, cause {}", code, cause.getCause());
        return null;
    }
}
