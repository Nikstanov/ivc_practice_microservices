package com.ivc.nikstanov.employeeservice.service;

import com.ivc.nikstanov.employeeservice.dto.APIResponseDto;
import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto findEmployeeById(Long id);
}
