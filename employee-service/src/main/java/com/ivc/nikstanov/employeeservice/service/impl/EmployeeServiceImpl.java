package com.ivc.nikstanov.employeeservice.service.impl;

import com.ivc.nikstanov.employeeservice.dto.APIResponseDto;
import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;
import com.ivc.nikstanov.employeeservice.entity.Employee;
import com.ivc.nikstanov.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ivc.nikstanov.employeeservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.employeeservice.repository.EmployeeRepository;
import com.ivc.nikstanov.employeeservice.service.APIClient;
import com.ivc.nikstanov.employeeservice.service.EmployeeService;
import com.ivc.nikstanov.employeeservice.utill.EmployeeMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeMapper employeeMapper;
    private EmployeeRepository employeeRepository;
    private APIClient departmentApiClient;

    @Override
    @CircuitBreaker(name = "DEPARTMENT-SERVICE", fallbackMethod = "saveEmployerExceptionHandler")
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        employeeRepository.findByEmail(employeeDto.getEmail()).ifPresent((value) -> {
            throw new EmployeeAlreadyExistsException(String.format("Employee with email: %s already exists", employeeDto.getEmail()));
        });

        departmentApiClient.getDepartment(employeeDto.getDepartmentCode());

        return employeeMapper.mapToEmployeeDto(employeeRepository.save(employeeMapper.mapToEmployee(employeeDto)));
    }

    public EmployeeDto saveEmployerExceptionHandler(EmployeeDto employeeDto, Throwable cause){
        log.info("Exception when try to get department from department-service with cause: {}, with code {}", cause.getCause(), employeeDto.getDepartmentCode());
        throw new ResourceNotFoundException("DEPARTMENT", "separtment", employeeDto.getDepartmentCode());
    }

    @Override
    @CircuitBreaker(name = "DEPARTMENT-SERVICE", fallbackMethod = "findEmployerExceptionHandler")
    public APIResponseDto findEmployeeById(Long id) {

        Employee resultemployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", Long.toString(id)));

        DepartmentDto departmentDto = departmentApiClient.getDepartment(resultemployee.getDepartmentCode());

        return new APIResponseDto(employeeMapper.mapToEmployeeDto(resultemployee), departmentDto);
    }

    public APIResponseDto findEmployerExceptionHandler(Long id, Throwable cause) {
        Employee resultemployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", Long.toString(id)));
        log.error("Can't reach department-service or department with code {} not exists, cause {}", resultemployee.getDepartmentCode(), cause.getCause());
        return new APIResponseDto(employeeMapper.mapToEmployeeDto(resultemployee), null);
    }


}
