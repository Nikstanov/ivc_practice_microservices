package com.ivc.nikstanov.employeeservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import com.ivc.nikstanov.employeeservice.exception.ErrorDetails;
import com.ivc.nikstanov.employeeservice.exception.GlobalExceptionHandler;
import com.ivc.nikstanov.employeeservice.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://department-service:8080", value = "DEPARTMENT-SERVICE", fallbackFactory = TestFallbackFactory.class)
public interface APIClient {

    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String code);
}

@Component
class TestFallbackFactory implements FallbackFactory<APIClientExceptionHandler> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIClientExceptionHandler create(Throwable cause) {
        String message = cause.getMessage();
        ErrorDetails errorDetails;
        try {
            errorDetails = objectMapper.readValue(message, ErrorDetails.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new APIClientExceptionHandler(errorDetails);
    }

}

@AllArgsConstructor
class APIClientExceptionHandler implements APIClient {

    private ErrorDetails errorDetails;

    @Override
    public DepartmentDto getDepartment(String code) {
        if(errorDetails != null){
            if(errorDetails.getErrorCode().equals(GlobalExceptionHandler.ErrorCodes.RESOURCE_NOT_FOUND.toString())){
                throw new ResourceNotFoundException("department", "departmentCode", code);
            }
        }
        throw new RuntimeException("Department server error");
    }
}
