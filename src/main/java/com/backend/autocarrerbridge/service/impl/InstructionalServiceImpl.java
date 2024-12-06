package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.controller.repository.InstructionalRepository;
import com.backend.autocarrerbridge.converter.InstructionalConverter;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import com.backend.autocarrerbridge.entity.Instructional;
import com.backend.autocarrerbridge.service.InstructionalService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InstructionalServiceImpl implements InstructionalService {

  private final InstructionalRepository instructionalRepository;

  public InstructionalServiceImpl(InstructionalRepository instructionalRepository) {
    this.instructionalRepository = instructionalRepository;
  }

  @Transactional
  @Override
  public InstructionalRequest createInstructional(InstructionalRequest request) {

    return null;
  }

  @Transactional
  @Override
  public InstructionalRequest updateInstructional(int id, InstructionalRequest request) {
    return null;
  }

  @Transactional
  @Override
  public InstructionalRequest deleteInstructional(int id) {
    return null;
  }

  @Override
  public List<InstructionalResponse> getAllInstructional() {
    return List.of();
  }

  @Override
  public List<InstructionalResponse> getInstructionalById(int id) {
    return List.of();
  }
}
