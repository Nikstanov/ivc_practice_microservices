package com.ivc.nikstanov.departmentservice.service.impl;

import com.ivc.nikstanov.departmentservice.dto.DepartmentDto;
import com.ivc.nikstanov.departmentservice.dto.DepartmentEvent;
import com.ivc.nikstanov.departmentservice.entity.Department;
import com.ivc.nikstanov.departmentservice.exception.DepartmentAlreadyExistsException;
import com.ivc.nikstanov.departmentservice.exception.ResourceNotFoundException;
import com.ivc.nikstanov.departmentservice.repository.DepartmentRepository;
import com.ivc.nikstanov.departmentservice.service.DepartmentService;
import com.ivc.nikstanov.departmentservice.utill.DepartmentMapper;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Value("${spring.kafka.topic.name}")
    private String topic;

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final Tracer tracer;
    private final KafkaTemplate<String, DepartmentEvent> kafkaTemplate;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        departmentRepository.findByDepartmentName(departmentDto.getDepartmentName()).ifPresent(value -> {
            throw new DepartmentAlreadyExistsException(String.format("Department with name: %s already exists", departmentDto.getDepartmentName()));
        });
        return departmentMapper.mapToDepartmentDto(departmentRepository.save(departmentMapper.mapToDepartment(departmentDto)));
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Department department = departmentRepository.findByDepartmentCode(code).orElseThrow(() -> new ResourceNotFoundException("Department", "code", code));
        return departmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public void deleteDepartmentByCode(String code) {
        Department department = departmentRepository.findByDepartmentCode(code).orElseThrow(() -> new ResourceNotFoundException("DEPARTMENT", "departmentCode", code));
        departmentRepository.deleteById(department.getId());
        DepartmentEvent event = new DepartmentEvent(
                DepartmentEvent.class.getTypeName(),
                DepartmentEvent.ActionsEnum.DELETED.toString(),
                code,
                tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : ""
        );
        kafkaTemplate.send(topic, event);
    }
}
