package com.ivc.nikstanov.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "information of employee"
)
public class APIResponseDto {

    @Schema(
            description = "Employee's personal info"
    )
    private EmployeeDto employee;

    @Schema(
            description = "Employee's department info"
    )
    private DepartmentDto department;

    @Schema(
            description = "Employee's organization info"
    )
    private OrganizationDto organization;
}
