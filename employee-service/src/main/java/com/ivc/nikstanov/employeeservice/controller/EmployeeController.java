package com.ivc.nikstanov.employeeservice.controller;

import com.ivc.nikstanov.employeeservice.dto.APIResponseDto;
import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;
import com.ivc.nikstanov.employeeservice.entity.Employee;
import com.ivc.nikstanov.employeeservice.entity.FullEmployee;
import com.ivc.nikstanov.employeeservice.service.EmployeeService;
import com.ivc.nikstanov.employeeservice.utill.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
@Schema(description = "CRUD operations with employees")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;

    @Operation(
            summary = "Save employee REST API",
            description = "Save employee REST API is used to save a given employee in the database"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 ACCEPTED"
    )
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody  @Validated(EmployeeDto.Save.class) EmployeeDto employeeDto){
        Employee result = employeeService.saveEmployee(employeeMapper.mapToEmployee(employeeDto));
        return new ResponseEntity<>(employeeMapper.mapToEmployeeDto(result), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Save employee REST API",
            description = "Save employee REST API is used to save a given employee in the database"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 ACCEPTED"
    )
    @PostMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody  @Validated(EmployeeDto.Update.class) EmployeeDto employeeDto, @PathVariable("id") Long id){
        Employee result = employeeService.updateEmployee(employeeMapper.mapToEmployee(employeeDto), id);
        return new ResponseEntity<>(employeeMapper.mapToEmployeeDto(result), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Get employee by id REST API",
            description = "Get employee by id REST API is used to get a single employee from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getAllInfoOfEmployeeById(@PathVariable("id") @Validated @Min(0) long id){
        FullEmployee result = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(new APIResponseDto(employeeMapper.mapToEmployeeDto(result.getEmployee()), result.getDepartment(), result.getOrganization()), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all employees REST API",
            description = "Get all employees REST API is used to get all employees from the database with specific department or organization"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<List<APIResponseDto>> getAllEmployees(@RequestParam(name = "departmentId", required = false) String departmentId, @RequestParam(name = "organizationId", required = false) String organizationId){
        List<FullEmployee> fullEmployees;
        if(departmentId != null && organizationId != null){
            fullEmployees = employeeService.findEmployeesByOrganizationAndDepartment(organizationId, departmentId);
        }
        else{
            if(departmentId != null){
                fullEmployees = employeeService.findEmployeesByDepartment(departmentId);
            }
            else if(organizationId != null){
                fullEmployees = employeeService.findEmployeesByOrganization(organizationId);
            }
            else {
                fullEmployees = employeeService.findEmployees();
            }
        }
        return new ResponseEntity<>(
                fullEmployees
                        .stream()
                        .map(fullEmployee -> new APIResponseDto(employeeMapper.mapToEmployeeDto(fullEmployee.getEmployee()), fullEmployee.getDepartment(), fullEmployee.getOrganization()))
                        .toList(),
                HttpStatus.OK);
    }
}
