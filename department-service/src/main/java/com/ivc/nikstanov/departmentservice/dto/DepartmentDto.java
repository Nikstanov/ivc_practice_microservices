package com.ivc.nikstanov.departmentservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(
        description = "DepartmentDto Model Information"
)
public class DepartmentDto {

    @Schema(
            accessMode = Schema.AccessMode.READ_ONLY,
            description = "Id of department"
    )
    private Long id;

    @Schema(
            description = "Department name"
    )
    @NotEmpty(message = "Department name should not be empty", groups = {Save.class})
    private String departmentName;

    @Schema(
            description = "Department description"
    )
    @NotEmpty(message = "Department description should not be empty", groups = {Save.class})
    private String departmentDescription;

    @Schema(
            description = "Department status code"
    )
    @NotEmpty(message = "Department code should not be empty", groups = {Save.class})
    private String departmentCode;

    public interface Save{}
    public interface Update{}
}
