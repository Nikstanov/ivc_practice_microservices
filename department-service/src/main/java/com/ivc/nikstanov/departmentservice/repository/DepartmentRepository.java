package com.ivc.nikstanov.departmentservice.repository;

import com.ivc.nikstanov.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentName(String name);
    Optional<Department> findByDepartmentCode(String code);
}
