package com.ivc.nikstanov.employeeservice.repository;

import com.ivc.nikstanov.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Optional<Employee> findByEmail(String email);
    List<Employee> findAllByDepartmentCode(String code);
    List<Employee> findAllByOrganizationCode(String code);
    List<Employee> findAllByOrganizationCodeAndDepartmentCode(String organizationCode, String departmentCode);
}
