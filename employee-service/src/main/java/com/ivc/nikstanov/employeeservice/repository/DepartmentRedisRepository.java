package com.ivc.nikstanov.employeeservice.repository;

import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRedisRepository extends CrudRepository<DepartmentDto, String> {
}
