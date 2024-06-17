package com.ivc.nikstanov.employeeservice.service.impl;

import com.ivc.nikstanov.employeeservice.dto.APIResponseDto;
import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;
import com.ivc.nikstanov.employeeservice.dto.OrganizationDto;
import com.ivc.nikstanov.employeeservice.entity.Employee;
import com.ivc.nikstanov.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ivc.nikstanov.employeeservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.employeeservice.repository.EmployeeRepository;
import com.ivc.nikstanov.employeeservice.service.DepartmentAPIClient;
import com.ivc.nikstanov.employeeservice.service.EmployeeService;
import com.ivc.nikstanov.employeeservice.service.OrganizationAPIClient;
import com.ivc.nikstanov.employeeservice.utill.EmployeeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeMapper employeeMapper;
    private EmployeeRepository employeeRepository;
    private DepartmentAPIClient departmentApiClient;
    private OrganizationAPIClient organizationAPIClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        employeeRepository.findByEmail(employeeDto.getEmail()).ifPresent((value) -> {
            throw new EmployeeAlreadyExistsException(String.format("Employee with email: %s already exists", employeeDto.getEmail()));
        });

        DepartmentDto departmentDto = departmentApiClient.getDepartment(employeeDto.getDepartmentCode());
        if(departmentDto == null){
            throw new ResourceNotFoundException("DEPARTMENT", "department", employeeDto.getDepartmentCode());
        }

        OrganizationDto organizationDto = organizationAPIClient.getOrganization(employeeDto.getOrganizationCode());
        if(organizationDto == null){
            throw new ResourceNotFoundException("ORGANIZATION", "organization", employeeDto.getOrganizationCode());
        }

        return employeeMapper.mapToEmployeeDto(employeeRepository.save(employeeMapper.mapToEmployee(employeeDto)));
    }

    @Override
    public APIResponseDto findEmployeeById(Long id) {

        Employee resultemployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", Long.toString(id)));

        DepartmentDto departmentDto = departmentApiClient.getDepartment(resultemployee.getDepartmentCode());
        OrganizationDto organizationDto = organizationAPIClient.getOrganization(resultemployee.getOrganizationCode());

        return new APIResponseDto(employeeMapper.mapToEmployeeDto(resultemployee), departmentDto, organizationDto);
    }


}
