package com.ivc.nikstanov.employeeservice.repository;

import com.ivc.nikstanov.employeeservice.dto.OrganizationDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRedisRepository extends CrudRepository<OrganizationDto, String> {
}
