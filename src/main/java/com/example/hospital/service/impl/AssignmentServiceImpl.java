package com.example.hospital.service.impl;

import com.example.hospital.repository.AssignmentRepository;
import com.example.hospital.service.AssignmentService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class AssignmentServiceImpl implements AssignmentService {
    private static final Logger LOGGER = getLogger(AssignmentService.class);

    @Resource
    private AssignmentRepository assignmentRepository;


}
