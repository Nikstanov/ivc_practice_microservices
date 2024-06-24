package com.ivc.nikstanov.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentEvent {

    public enum ActionsEnum{
        DELETED
    }

    private String type;
    private String action;
    private String departmentCode;
    private String correlationId;
}
