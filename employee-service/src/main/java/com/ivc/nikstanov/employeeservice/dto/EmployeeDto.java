package com.ivc.nikstanov.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "employeeDto Model Information"
)
public class EmployeeDto {

    @Schema(
            accessMode = Schema.AccessMode.READ_ONLY,
            description = "Id of employee"
    )
    private Long id;

    @Schema(
            description = "Employee firstname"
    )
    @NotEmpty(message = "Firstname should not be empty", groups = {Save.class})
    private String firstName;

    @Schema(
            description = "Employee lastname"
    )
    @NotEmpty(message = "Lastname should not be empty", groups = {Save.class})
    private String lastName;

    @Schema(
            description = "Employee email"
    )
    @NotEmpty(message = "Email should not be empty", groups = {Save.class})
    @Email(message = "Incorrect email adress", groups = {Save.class})
    private String email;

    public interface Save{}
    public interface Update{}

}
