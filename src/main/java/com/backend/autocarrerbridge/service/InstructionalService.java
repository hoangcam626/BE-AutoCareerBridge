package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import java.util.List;
import com.backend.autocarrerbridge.dto.request.instructional.InstructionalRequest;
import com.backend.autocarrerbridge.dto.response.instructional.InstructionalResponse;

public interface InstructionalService {

  InstructionalResponse createInstructional(InstructionalRequest request);

  InstructionalResponse updateInstructional(int id, InstructionalRequest request);

  InstructionalResponse setInstructionalInactive(int id);

  InstructionalResponse setInstructionalActive(int id);

  List<InstructionalResponse> deleteInstructional(List<Integer> ids);

  PagingResponse<InstructionalResponse> getAllInstructional(Integer universityId, int page, int size);

  List<InstructionalResponse> getInstructionalById(int id);

  PagingResponse<InstructionalResponse> getALlInstructionalActive( Integer universityId, int page, int size);

  PagingResponse<InstructionalResponse> getALlInstructionalInactive(Integer universityId, int page, int size);

 long countInstructional(Integer universityId);
}
