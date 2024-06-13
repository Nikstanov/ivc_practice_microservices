package com.ivc.nikstanov.departmentservice.controller;

import com.ivc.nikstanov.departmentservice.dto.DepartmentDto;
import com.ivc.nikstanov.departmentservice.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Schema(description = "CRUD operations with departments")
@RequestMapping("api/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @Operation(
            summary = "Save department REST API",
            description = "Save department REST API is used to save a given department in the database"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 ACCEPTED"
    )
    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody @Validated(DepartmentDto.Save.class) DepartmentDto departmentDto){
        DepartmentDto savingResult = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savingResult, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Get Department by code REST API",
            description = "Get Department by code REST API is used to get a single department from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@Parameter(required = true, description = "Code of department that must be found") @PathVariable("department-code") String departmentCode){
        DepartmentDto result = departmentService.getDepartmentByCode(departmentCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
