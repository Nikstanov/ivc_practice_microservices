package com.ivc.nikstanov.employeeservice.service;

import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentAPIClient {

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DepartmentAPIClient.class);

    @GetMapping("api/departments/{department-code}")
    @CircuitBreaker(name = "DEPARTMENT-SERVICE", fallbackMethod = "getDepartmentExceptionHandler")
    DepartmentDto getDepartment(@PathVariable("department-code") String code);

    default DepartmentDto getDepartmentExceptionHandler(String code, Throwable cause){
        log.error("Can't reach department-service or department with code {} not exists, cause {}", code, cause.getCause());
        return null;
    }
}
