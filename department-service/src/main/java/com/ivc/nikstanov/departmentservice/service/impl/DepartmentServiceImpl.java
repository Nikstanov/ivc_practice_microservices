package com.ivc.nikstanov.departmentservice.service.impl;

import com.ivc.nikstanov.departmentservice.dto.DepartmentDto;
import com.ivc.nikstanov.departmentservice.entity.Department;
import com.ivc.nikstanov.departmentservice.exception.DepartmentAlreadyExistsException;
import com.ivc.nikstanov.departmentservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.departmentservice.repository.DepartmentRepository;
import com.ivc.nikstanov.departmentservice.service.DepartmentService;
import com.ivc.nikstanov.departmentservice.utill.DepartmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        departmentRepository.findByDepartmentName(departmentDto.getDepartmentName()).ifPresent((value) -> {
            throw new DepartmentAlreadyExistsException(String.format("Department with name: %s already exists", departmentDto.getDepartmentName()));
        });
        return departmentMapper.mapToDepartmentDto(departmentRepository.save(departmentMapper.mapToDepartment(departmentDto)));
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Department department = departmentRepository.findByDepartmentCode(code).orElseThrow(() -> new ResourceNotFoundException("Department", "code", code));
        return departmentMapper.mapToDepartmentDto(department);
    }
}
