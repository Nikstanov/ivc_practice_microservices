package com.ivc.nikstanov.departmentservice.service;

import com.ivc.nikstanov.departmentservice.dto.DepartmentDto;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentByCode(String code);
    void deleteDepartmentByCode(String code);
}
