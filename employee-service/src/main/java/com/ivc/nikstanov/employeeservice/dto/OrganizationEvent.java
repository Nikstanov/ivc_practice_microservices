package com.ivc.nikstanov.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationEvent {

    public enum ActionsEnum{
        DELETED
    }

    private String type;
    private String action;
    private String organizationCode;
    private String correlationId;
}
