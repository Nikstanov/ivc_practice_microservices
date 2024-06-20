package com.ivc.nikstanov.employeeservice.service;

import com.ivc.nikstanov.employeeservice.dto.APIResponseDto;
import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto findEmployeeById(Long id);
    List<APIResponseDto> findEmployeesByValues(Map<String, String> value);
}
