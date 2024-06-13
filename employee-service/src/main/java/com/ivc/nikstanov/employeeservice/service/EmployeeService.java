package com.ivc.nikstanov.employeeservice.service;

import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    EmployeeDto findEmployeeById(Long id);
}
