package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;
import java.util.List;

public interface InstructionalService {

  InstructionalRequest createInstructional(InstructionalRequest instructionalRequest);

  InstructionalRequest updateInstructional(int id, InstructionalRequest instructionalRequest);

  InstructionalRequest deleteInstructional(int id);

  List<InstructionalResponse> getAllInstructional();

  List<InstructionalResponse> getInstructionalById(int id);
}
