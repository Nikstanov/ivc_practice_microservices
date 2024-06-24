package com.ivc.nikstanov.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("department")
public class DepartmentDto {

    private Long id;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
}
