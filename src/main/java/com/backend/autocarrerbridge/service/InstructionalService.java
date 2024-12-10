package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import java.util.List;

public interface InstructionalService {

  InstructionalResponse createInstructional(InstructionalRequest request);

  InstructionalResponse updateInstructional(int id, InstructionalRequest request);

  InstructionalResponse deleteInstructional(int id);

  List<InstructionalResponse> getAllInstructional();

  List<InstructionalResponse> getInstructionalById(int id);
}
