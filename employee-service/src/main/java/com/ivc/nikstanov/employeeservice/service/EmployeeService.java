package com.ivc.nikstanov.employeeservice.service;

import com.ivc.nikstanov.employeeservice.entity.Employee;
import com.ivc.nikstanov.employeeservice.entity.FullEmployee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    FullEmployee findEmployeeById(Long id);
    List<FullEmployee> findEmployees();
    List<FullEmployee> findEmployeesByDepartment(String departmentId);
    List<FullEmployee> findEmployeesByOrganization(String organizationId);
    List<FullEmployee> findEmployeesByOrganizationAndDepartment(String organizationId, String departmentId);
    Employee updateEmployee(Employee employee, Long id);

}
