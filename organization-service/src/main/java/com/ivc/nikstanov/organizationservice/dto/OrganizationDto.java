package com.ivc.nikstanov.organizationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {

    private Long id;

    @Schema(
            description = "Organization's name"
    )
    @NotEmpty(message = "Organization's name should not be empty", groups = {Save.class})
    private String organizationName;

    @Schema(
            description = "Organization's description"
    )
    @NotEmpty(message = "Organization's description should not be empty", groups = {Save.class})
    private String organizationDescription;

    @Schema(
            description = "Organization's code"
    )
    @NotEmpty(message = "Organization's code should not be empty", groups = {Save.class})
    private String organizationCode;

    private LocalDateTime creationDate;

    public interface Save{}
    public interface Update{}
}
