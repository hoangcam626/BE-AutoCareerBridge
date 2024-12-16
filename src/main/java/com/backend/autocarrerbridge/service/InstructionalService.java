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

  PagingResponse<InstructionalResponse> getAllInstructional(int page, int size);

  List<InstructionalResponse> getInstructionalById(int id);

  PagingResponse<InstructionalResponse> getALlInstructionalActive(int page, int size);

  PagingResponse<InstructionalResponse> getALlInstructionalInactive(int page, int size);


}
