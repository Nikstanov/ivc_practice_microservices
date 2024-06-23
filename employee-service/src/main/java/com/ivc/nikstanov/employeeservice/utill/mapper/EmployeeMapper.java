package com.ivc.nikstanov.employeeservice.utill.mapper;

import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;
import com.ivc.nikstanov.employeeservice.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeDto mapToEmployeeDto(Employee department);
    Employee mapToEmployee(EmployeeDto departmentDto);
}
