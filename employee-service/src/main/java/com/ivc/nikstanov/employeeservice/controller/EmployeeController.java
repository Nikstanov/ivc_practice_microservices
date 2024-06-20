package com.ivc.nikstanov.employeeservice.controller;

import com.ivc.nikstanov.employeeservice.dto.APIResponseDto;
import com.ivc.nikstanov.employeeservice.dto.EmployeeDto;
import com.ivc.nikstanov.employeeservice.service.EmployeeService;
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
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employees")
@Schema(description = "CRUD operations with employees")
public class EmployeeController {

    private EmployeeService employeeService;

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
        EmployeeDto result = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
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
        APIResponseDto result = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all employees REST API",
            description = "Get all employees REST API is used to get all employees from the database with specific parameter (path variable with value - ?userId = 1&organizationCode=000T)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<List<APIResponseDto>> getAllEmployeesWithFiltering(@RequestParam Map<String,String> allParams){
        return new ResponseEntity<>(employeeService.findEmployeesByValues(allParams), HttpStatus.OK);
    }
}
