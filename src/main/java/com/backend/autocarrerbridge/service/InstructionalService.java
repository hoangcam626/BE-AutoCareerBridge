package com.backend.autocarrerbridge.service;

import java.util.List;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;

public interface InstructionalService {

  InstructionalResponse createInstructional(InstructionalRequest request);

  InstructionalResponse updateInstructional(int id, InstructionalRequest request);

  InstructionalResponse deleteInstructional(int id);

  List<InstructionalResponse> getAllInstructional();

  List<InstructionalResponse> getInstructionalById(int id);
}
