package com.ivc.nikstanov.employeeservice.service.impl;

import com.ivc.nikstanov.employeeservice.dto.DepartmentDto;
import com.ivc.nikstanov.employeeservice.dto.DepartmentEvent;
import com.ivc.nikstanov.employeeservice.dto.OrganizationDto;
import com.ivc.nikstanov.employeeservice.dto.OrganizationEvent;
import com.ivc.nikstanov.employeeservice.entity.Employee;
import com.ivc.nikstanov.employeeservice.entity.FullEmployee;
import com.ivc.nikstanov.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ivc.nikstanov.employeeservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.employeeservice.repository.DepartmentRedisRepository;
import com.ivc.nikstanov.employeeservice.repository.EmployeeRepository;
import com.ivc.nikstanov.employeeservice.repository.OrganizationRedisRepository;
import com.ivc.nikstanov.employeeservice.service.DepartmentAPIClient;
import com.ivc.nikstanov.employeeservice.service.EmployeeService;
import com.ivc.nikstanov.employeeservice.service.OrganizationAPIClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private OrganizationRedisRepository organizationRedisRepository;
    private DepartmentRedisRepository departmentRedisRepository;
    private DepartmentAPIClient departmentApiClient;
    private OrganizationAPIClient organizationAPIClient;

    @Transactional
    @Override
    public Employee saveEmployee(Employee employee) {

        employeeRepository.findByEmail(employee.getEmail()).ifPresent(value -> {
            throw new EmployeeAlreadyExistsException(String.format("Employee with email: %s already exists", employee.getEmail()));
        });

        DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
        if(departmentDto == null){
            throw new ResourceNotFoundException("DEPARTMENT", "department", employee.getDepartmentCode());
        }
        departmentRedisRepository.save(departmentDto);

        OrganizationDto organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());
        if(organizationDto == null){
            throw new ResourceNotFoundException("ORGANIZATION", "organization", employee.getOrganizationCode());
        }
        organizationRedisRepository.save(organizationDto);

        return employeeRepository.save(employee);
    }

    @Override
    public FullEmployee findEmployeeById(Long id) {

        Employee resultemployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", Long.toString(id)));

        DepartmentDto departmentDto;
        Optional<DepartmentDto> cachedDepartment = departmentRedisRepository.findById(resultemployee.getDepartmentCode());
        if(cachedDepartment.isPresent()){
            departmentDto = cachedDepartment.get();
        }
        else {
            departmentDto =  departmentApiClient.getDepartment(resultemployee.getDepartmentCode());
            if(departmentDto != null) departmentRedisRepository.save(departmentDto);
        }

        OrganizationDto organizationDto;
        Optional<OrganizationDto> cachedOrganization = organizationRedisRepository.findById(resultemployee.getOrganizationCode());
        if(cachedOrganization.isPresent()){
            organizationDto = cachedOrganization.get();
        }
        else{
            organizationDto = organizationAPIClient.getOrganization(resultemployee.getOrganizationCode());
            if(organizationDto != null) organizationRedisRepository.save(organizationDto);
        }

        return new FullEmployee(resultemployee, departmentDto, organizationDto);
    }

    @Override
    public List<FullEmployee> findEmployees() {
        return employeeRepository.findAll().stream().map(employee -> {

            DepartmentDto departmentDto;
            Optional<DepartmentDto> cachedDepartment = departmentRedisRepository.findById(employee.getDepartmentCode());
            if(cachedDepartment.isPresent()){
                departmentDto = cachedDepartment.get();
            }
            else {
                departmentDto =  departmentApiClient.getDepartment(employee.getDepartmentCode());
                if(departmentDto != null) departmentRedisRepository.save(departmentDto);
            }

            OrganizationDto organizationDto;
            Optional<OrganizationDto> cachedOrganization = organizationRedisRepository.findById(employee.getOrganizationCode());
            if(cachedOrganization.isPresent()){
                organizationDto = cachedOrganization.get();
            }
            else{
                organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());
                if(organizationDto != null) organizationRedisRepository.save(organizationDto);
            }

            return new FullEmployee(employee, departmentDto, organizationDto);
        }).toList();
    }

    @Override
    public List<FullEmployee> findEmployeesByDepartment(String departmentId) {
        return employeeRepository.findAllByDepartmentCode(departmentId).stream().map(employee -> {
            DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
            OrganizationDto organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());
            return new FullEmployee(employee, departmentDto, organizationDto);
        }).toList();
    }

    @Override
    public List<FullEmployee> findEmployeesByOrganization(String organizationId) {
        return employeeRepository.findAllByOrganizationCode(organizationId).stream().map(employee -> {
            DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
            OrganizationDto organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());
            return new FullEmployee(employee, departmentDto, organizationDto);
        }).toList();
    }

    @Override
    public List<FullEmployee> findEmployeesByOrganizationAndDepartment(String organizationId, String departmentId) {
        return employeeRepository.findAllByOrganizationCodeAndDepartmentCode(organizationId, departmentId).stream().map(employee -> {
            DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
            OrganizationDto organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());
            return new FullEmployee(employee, departmentDto, organizationDto);
        }).toList();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        Employee currentEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", Long.toString(id)));
        if(employee.getFirstName() != null){
            currentEmployee.setFirstName(employee.getFirstName());
        }
        if(employee.getLastName() != null){
            currentEmployee.setLastName(employee.getLastName());
        }
        if(employee.getOrganizationCode() != null){
            OrganizationDto organizationDto = organizationAPIClient.getOrganization(employee.getOrganizationCode());
            if(organizationDto == null){
                throw new ResourceNotFoundException("ORGANIZATION", "organization", employee.getOrganizationCode());
            }
            currentEmployee.setOrganizationCode(employee.getOrganizationCode());
        }
        if(employee.getDepartmentCode() != null){
            DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
            if(departmentDto == null){
                throw new ResourceNotFoundException("DEPARTMENT", "department", employee.getDepartmentCode());
            }
            currentEmployee.setDepartmentCode(employee.getDepartmentCode());
        }
        return currentEmployee;
    }

    @KafkaListener(topics = "${spring.kafka.topic.organization}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrganization(OrganizationEvent event){
        log.info("Organization event received => {}", event.toString());
        organizationRedisRepository.deleteById(event.getOrganizationCode());
    }

    @KafkaListener(topics = "${spring.kafka.topic.department}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeDepartment(DepartmentEvent event){
        log.info("Department event received => {}", event.toString());
        departmentRedisRepository.deleteById(event.getDepartmentCode());
    }
}
