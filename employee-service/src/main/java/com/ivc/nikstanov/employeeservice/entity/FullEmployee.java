package com.ivc.nikstanov.employeeservice.entity;

import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import com.ivc.nikstanov.employeeservice.dto.OrganizationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullEmployee {
    private Employee employee;
    private DepartmentDto department;
    private OrganizationDto organization;
}
