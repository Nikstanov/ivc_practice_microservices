package com.ivc.nikstanov.organizationservice.controller;

import com.ivc.nikstanov.organizationservice.dto.OrganizationDto;
import com.ivc.nikstanov.organizationservice.entity.Organization;
import com.ivc.nikstanov.organizationservice.service.OrganizationService;
import com.ivc.nikstanov.organizationservice.utill.OrganizationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/organizations")
@Schema(description = "CRUD operations with organizations")
public class OrganizationController {

    private OrganizationService organizationService;
    private OrganizationMapper organizationMapper;

    @Operation(
            summary = "Save organization REST API",
            description = "Save organization REST API is used to save a given organization in the database"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 ACCEPTED"
    )
    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody @Validated({OrganizationDto.Save.class}) OrganizationDto organizationDto){
        Organization result = organizationService.saveOrganization(organizationMapper.toOrganization(organizationDto));
        return new ResponseEntity<>(organizationMapper.toOrganizationDto(result), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get organization by code REST API",
            description = "Get organization by code REST API is used to get a single organization from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("{code}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable("code") String organizationCode){
        Organization result = organizationService.findByCode(organizationCode);
        return ResponseEntity.ok(organizationMapper.toOrganizationDto(result));
    }

    @Operation(
            summary = "Delete organization by code REST API",
            description = "Delete organization by code REST API is used to delete a single organization from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("{code}")
    public ResponseEntity<String> deleteOrganization(@PathVariable("code") String organizationCode){
        organizationService.deleteOrganizationByCode(organizationCode);
        return ResponseEntity.ok("Successfully deleted");
    }

}
