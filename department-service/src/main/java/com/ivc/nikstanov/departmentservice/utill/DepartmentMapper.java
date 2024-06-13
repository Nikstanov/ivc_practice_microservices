package com.ivc.nikstanov.departmentservice.utill;

import com.ivc.nikstanov.departmentservice.dto.DepartmentDto;
import com.ivc.nikstanov.departmentservice.entity.Department;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DepartmentMapper {
    DepartmentDto mapToDepartmentDto(Department department);
    Department mapToDepartment(DepartmentDto departmentDto);
}
