package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.notification.ContentDeleteWorkShopRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopCountResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.workshop.WorkShopBusinessRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.service.WorkShopBusinessService;
import com.backend.autocarrerbridge.service.WorkShopService;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.time.LocalDate;

@RestController
@RequestMapping("api/work-shop")
@RequiredArgsConstructor
public class WorkShopController {

  private final WorkShopService workShopService;
  private final WorkShopBusinessService workShopBusinessService;

  /**
   * Lấy danh sách tất cả các workshop theo phân trang.
   *
   * @param page Số trang cần lấy (mặc định là 0).
   * @param size Số lượng workshop trên mỗi trang (mặc định là 5).
   * @return ApiResponse chứa danh sách các workshop.
   */
  @GetMapping
  public ApiResponse<Object> getAllWorkShop(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(required = false) String keyword) {
    return ApiResponse.builder()
        .data(workShopService.getAllWorkShop(PageRequest.of(page, size), keyword))
        .build();
  }

  /**
   * Tạo mới một workshop.
   *
   * @param workShopRequest Thông tin chi tiết của workshop cần tạo.
   * @return ApiResponse chứa workshop vừa được tạo.
   */
  @PostMapping
  public ApiResponse<Object> createWorkShop(
      @ModelAttribute @Valid WorkShopRequest workShopRequest) {
    return ApiResponse.builder()
        .data(workShopService.createWorkShop(workShopRequest))
        .build();
  }

  /**
   * Lấy danh sách các workshop theo trạng thái với phân trang.
   *
   * @param page  Số trang cần lấy (mặc định là 0).
   * @param size  Số lượng workshop trên mỗi trang (mặc định là 5).
   * @param state Trạng thái của workshop để lọc.
   * @return ApiResponse chứa danh sách các workshop có trạng thái tương ứng.
   */
  @GetMapping("/state/{state}")
  public ApiResponse<Object> getAllWorkShopByState(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(required = false) String keyword,
      @PathVariable("state") State state) {

    return ApiResponse.builder()
        .data(workShopService.getAllWorkShopByState(PageRequest.of(page, size), state, keyword))
        .build();
  }

    /**
     * Lấy danh sách các workshop theo trạng thái với phân trang.
     *
     * @param page Số trang cần lấy (mặc định là 0).
     * @param size Số lượng workshop trên mỗi trang (mặc định là 5).
     * @return ApiResponse chứa danh sách các workshop có trạng thái tương ứng.
     */
    @GetMapping("/province/{provinceId}")
    public ApiResponse<Object> getAllWorkShopByLocation(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @PathVariable("provinceId") Integer provincesId) {

        return ApiResponse.builder()
                .data(workShopService.getAllWorkShopByLocation(PageRequest.of(page, size),provincesId))
                .build();
    }


    /**
     * Lấy danh sách các workshop theo trường đại học với phân trang.
     *
     * @param page Số trang cần lấy (mặc định là 0).
     * @param size Số lượng workshop trên mỗi trang (mặc định là 5).
     * @param universityId ID của trường đại học để lọc workshop.
     * @return ApiResponse chứa danh sách các workshop của trường đại học đã chọn.
     */
    @GetMapping("/university/{universityId}")
    public ApiResponse<Object> getAllWorkShopByUniversity(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @PathVariable("universityId") Integer universityId) {
        return ApiResponse.builder()
                .data(workShopService.getAllWorkShopByUniversity(PageRequest.of(page, size), universityId, keyword))
                .build();
    }

  /**
   * Lấy danh sách các workshop theo trường đại học với phân trang.
   *
   * @param page Số trang cần lấy (mặc định là 0).
   * @param size Số lượng workshop trên mỗi trang (mặc định là 5).
   * @param universityId ID của trường đại học để lọc workshop.
   * @return ApiResponse chứa danh sách các workshop của trường đại học đã chọn.
   */
  @GetMapping("/get-by-university")
  public ApiResponse<Object> getAllWorkShopByUniversityId(
          @RequestParam(defaultValue = "0") Integer page,
          @RequestParam(defaultValue = "10") Integer size,
          @RequestParam(required = false) String keyword,
          @RequestParam("universityId") Integer universityId) {
    return ApiResponse.builder()
            .data(workShopService.getAllWorkShopByUniversity(PageRequest.of(page, size), universityId, keyword))
            .build();
  }

    /**
     * Cập nhật thông tin của một workshop đã tồn tại.
     *
     * @param idWorkShop ID của workshop cần cập nhật.
     * @param workShopRequest Thông tin mới cho workshop.
     * @return ApiResponse chứa workshop đã được cập nhật.
     */
    @PutMapping("/id/{idWorkShop}")
    public ApiResponse<Object> updateWorkShop(
            @PathVariable("idWorkShop") Integer idWorkShop, @ModelAttribute @Valid WorkShopRequest workShopRequest) {
        return ApiResponse.builder()
                .data(workShopService.updateWordShop(idWorkShop, workShopRequest))
                .build();
    }

  /**
   * Cập nhật thông tin của một workshop đã tồn tại.
   *
   * @param idWorkShop ID của workshop cần xoá .
   * @return ApiResponse chứa workshop đã được xoá.
   */
  @PutMapping("/idRemove/{idWorkShop}")
  public ApiResponse<Object> removeWorkShop(@PathVariable("idWorkShop") Integer idWorkShop, @RequestBody ContentDeleteWorkShopRequest content) throws ParseException {
    return ApiResponse.builder()
            .data(workShopService.removeWorkShop(idWorkShop, content))
            .build();
  }

  /**
   * Lấy danh sách các doanh nghiệp đã được chấp nhận tham gia một workshop cụ thể.
   *
   * @param page       - Số trang (mặc định là 0).
   * @param size       - Số lượng doanh nghiệp mỗi trang (mặc định là 5).
   * @param workShopId - ID của workshop.
   * @param state      - Trạng thái kết nối (Chấp nhận, Chờ duyệt, v.v.).
   * @return - Response chứa danh sách các doanh nghiệp tham gia workshop.
   */
  @GetMapping("/{workShopId}")
  public ApiResponse<Object> getAllAcceptedBusiness(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @PathVariable("workShopId") Integer workShopId,
      @RequestParam State state) {

    // Trả về dữ liệu danh sách doanh nghiệp tham gia workshop với phân trang
    return ApiResponse.builder()
        .data(workShopBusinessService.getAllColabBusiness(workShopId, PageRequest.of(page, size),
            state))
        .build();
  }

    /**
     * Xử lý yêu cầu tham gia workshop của doanh nghiệp.
     * @param workShopBusinessRequest - Thông tin yêu cầu tham gia workshop.
     * @return - Response chứa thông báo kết quả yêu cầu tham gia.
     */
    @PostMapping("/request")
    public ApiResponse<Object> requestAttendWorkShop(@RequestBody WorkShopBusinessRequest workShopBusinessRequest) {
        // Gửi yêu cầu tham gia workshop và trả về thông báo kết quả
        return ApiResponse.builder()
                .data(workShopBusinessService.requestToAttend(workShopBusinessRequest))
                .build();
    }
    /**
     * Chấp nhận yêu cầu tham gia workshop của doanh nghiệp.
     * @param workShopBusinessRequest - Thông tin yêu cầu tham gia workshop.
     * @return - Response chứa thông báo kết quả yêu cầu tham gia.
     */
    @PostMapping("/accept-request")
    public ApiResponse<Object> acceptRequestWorkShop(@RequestBody WorkShopBusinessRequest workShopBusinessRequest) throws ParseException {
        // Gửi yêu cầu tham gia workshop và trả về thông báo kết quả
        return ApiResponse.builder()
                .data(workShopBusinessService.acceptBusiness(workShopBusinessRequest))
                .build();
    }
    /**
     * Chấp nhận yêu cầu tham gia workshop của doanh nghiệp.
     * @param workShopBusinessRequest - Thông tin yêu cầu tham gia workshop.
     * @return - Response chứa thông báo kết quả yêu cầu tham gia.
     */
    @PostMapping("/reject-request")
    public ApiResponse<Object> rejectRequestWorkShop(@RequestBody WorkShopBusinessRequest workShopBusinessRequest) throws ParseException {
        // Gửi yêu cầu tham gia workshop và trả về thông báo kết quả
        return ApiResponse.builder()
                .data(workShopBusinessService.rejectBusiness(workShopBusinessRequest))
                .build();
    }
    @GetMapping("/display")
    public ApiResponse<Object> getWorkShopApprovedAndLocation(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "7") Integer size,
            @RequestParam(value = "startDate" ,required = false ) LocalDate startDate,
            @RequestParam(value = "endDate",required = false) LocalDate endDate,
            @RequestParam(value = "provinceId", required = false) Integer provinceId,
            @RequestParam(value = "universityId", required = false) Integer universityId,
            @RequestParam(required = false)String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.builder()
                .data(workShopService.getAllWorkShopApprovedAndLocation(pageable,startDate,endDate,provinceId,universityId,keyword)).build();
    }

    @GetMapping("/display/{workShopId}")
    public ApiResponse<Object> getWorkShopPortalById(@PathVariable("workShopId") Integer workShopId){
        return ApiResponse.builder().data(workShopService.getWorkShopPortalById(workShopId)).build();
    }
    @GetMapping("/status")
    public ApiResponse<Object> getWorkShopStatus(@RequestParam Integer workShopId,@RequestParam Integer businessId){
        return ApiResponse.builder().data(workShopBusinessService.getWorkShopStatusBusiness(workShopId,businessId)).build();
    }
    @GetMapping("/count-total/{universityId}")
    public long countWorkShop(@PathVariable Integer universityId) {
        return workShopService.countWorkShop(universityId);
    }

    @GetMapping("/business-details")
    public List<Map<String, Object>> getWorkshopBusinessDetails() {
        return workShopBusinessService.countWorkShopAndStatusConnected();
    }
  @GetMapping("business/{businessId}")
  public ApiResponse<Object> getWorkshopStateBusiness(@RequestParam(defaultValue = "0") Integer page,@RequestParam(defaultValue = "7")Integer size,@RequestParam(required = false) String keyword,@RequestParam(required = false) State state,@PathVariable("businessId") Integer businessId){
    Pageable pageable = PageRequest.of(page, size);
    return ApiResponse.builder().data(workShopService.getAllWorkShopByPracticeBusiness(pageable,businessId,keyword,state)).build();
  }
  @PutMapping("business/cancel")
  public ApiResponse<Object> cancelAttendWorkshop(@RequestParam("businessId") Integer businessId,@RequestParam("workshopId") Integer workshopId) throws ParseException {
    return ApiResponse.builder().data(workShopBusinessService.cancelAttendWorkshop(businessId,workshopId)).build();
  }
  @GetMapping("/countTotalWorkshop")
  public ApiResponse<Object> getTotalWorkshop() {
    return ApiResponse.builder().data(workShopService.countTotalWorkShop()).build();
  }
  @GetMapping("/countWorkshopApproved")
  public List<WorkshopCountResponse> countWorkshopByApproved(@RequestParam Integer universityId,@RequestParam State state) {
    return workShopService.countWorkshopByApproved(universityId, state);
  }
}
