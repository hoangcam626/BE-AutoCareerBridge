package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_INVALID_WORKSHOP_STATE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_CONTENT;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_WORKSHOP_ALREADY_APPROVED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_WORKSHOP_ALREADY_REJECTED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_WORK_SHOP_DATE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_WORK_SHOP_DATE_OUT_DATE;
import static com.backend.autocarrerbridge.util.Constant.APPROVED_WORKSHOP;
import static com.backend.autocarrerbridge.util.Constant.DELETE_WORK_SHOP;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_WORKSHOP;
import static com.backend.autocarrerbridge.util.Validation.sanitizeKeyword;
import static com.backend.autocarrerbridge.util.enums.State.APPROVED;
import static com.backend.autocarrerbridge.util.enums.State.PENDING;
import static com.backend.autocarrerbridge.util.enums.Status.ACTIVE;

import com.backend.autocarrerbridge.dto.response.workshop.WorkshopCountResponse;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.dto.request.notification.ContentDeleteWorkShopRequest;
import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopApprovedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopRejectedRequest;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.dto.response.workshop.AdminWorkshopResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopPortalResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopStateBusiness;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopUniversityResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopApprovedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopRejectedResponse;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.service.LocationService;
import com.backend.autocarrerbridge.service.NotificationService;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopResponse;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.Workshop;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.WorkShopRepository;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.UniversityService;
import com.backend.autocarrerbridge.service.WorkShopService;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkShopServiceImpl implements WorkShopService {

    // Khai báo các repository và service cần thiết
    WorkShopRepository workShopRepository;
    ModelMapper modelMapper;
    ImageService imageService;
    UniversityService universityService;
    LocationService locationService;
    SendEmail sendEmail;
    NotificationService notificationService;

    /**
     * Lấy danh sách tất cả các Workshop với phân trang.
     * @param pageable Thông tin phân trang
     * @return Danh sách các Workshop phản hồi
     */
    @Override
    public List<WorkshopResponse> getAllWorkShop(Pageable pageable, String keyword) {
        List<Workshop> list =
                workShopRepository.getAllWorkShop(keyword, pageable).getContent();
        return list.stream()
                .map(workshop -> modelMapper.map(workshop, WorkshopResponse.class))
                .toList();
    }

    /**
     * Lấy danh sách Workshop của một trường đại học cụ thể với phân trang.
     * @param pageable Thông tin phân trang
     * @param universityId ID của trường đại học
     * @return Danh sách các Workshop của trường đại học
     * @throws AppException Nếu không có nội dung
     */
    @PreAuthorize("hasAuthority('SCOPE_UNIVERSITY')")
    @Override
    public WorkshopUniversityResponse getAllWorkShopByUniversity(Pageable pageable, Integer universityId, String keyword) {
        Page<Workshop> list = workShopRepository
                .getAllWorkShopByUniversity(pageable, universityId, keyword);

        if (list.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        List<WorkshopResponse> workshops = list.stream()
                .map(workshop -> modelMapper.map(workshop, WorkshopResponse.class))
                .toList();
        WorkshopUniversityResponse workShopUniversityResponse = new WorkshopUniversityResponse();
        workShopUniversityResponse.setWorkshops(workshops);
        workShopUniversityResponse.setTotalRecords((int) list.getTotalElements());
        workShopUniversityResponse.setUniversity(modelMapper.map(universityService.findById(universityId), UniversityResponse.class));
        return workShopUniversityResponse;
    }


    /**
     * Tạo một Workshop mới.
     * @param workShopRequest Thông tin Workshop cần tạo
     * @return Thông tin phản hồi của Workshop vừa tạo
     * @throws AppException Nếu có lỗi về trạng thái hoặc trường đại học không tồn tại
     */
    @PreAuthorize("hasAuthority('SCOPE_UNIVERSITY')")
    @Override
    public WorkshopResponse createWorkShop(WorkShopRequest workShopRequest) {
        validateWorkShop(workShopRequest); // Kiểm tra tính hợp lệ của Workshop
        Integer imageId = imageService.uploadFile(workShopRequest.getImageWorkshop()); // Tải ảnh lên
        workShopRequest.setStatus(ACTIVE); // Đặt trạng thái ACTIVE
        workShopRequest.setStatusBrowse(PENDING); // Đặt trạng thái duyệt là PENDING
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setDistrictId(workShopRequest.getIdDistrict());
        locationRequest.setProvinceId(workShopRequest.getIdProvince());
        locationRequest.setWardId(workShopRequest.getIdWard());
        locationRequest.setDescription(workShopRequest.getAddressDescription());
        Location location = locationService.saveLocation(locationRequest);
        University university = universityService.findById(workShopRequest.getUniversityId()); // Tìm trường đại học
        if (Objects.isNull(university)) {
            throw new AppException(ERROR_NO_CONTENT); // Ném lỗi nếu trường không tồn tại
        }

        // Ánh xạ từ request sang entity và lưu vào cơ sở dữ liệu
        Workshop workshop = modelMapper.map(workShopRequest, Workshop.class);
        workshop.setWorkshopImageId(imageId); // Gắn ảnh cho Workshop
        workshop.setUniversity(university);// Gắn trường đại học cho Workshop
        workshop.setLocation(location);
        return modelMapper.map(workShopRepository.save(workshop), WorkshopResponse.class);
    }

    /**
     * Lấy danh sách Workshop đã được phê duyệt theo trạng thái cụ thể với phân trang.
     * @param pageable Thông tin phân trang
     * @param state Trạng thái cần lọc
     * @return Danh sách Workshop đã được phê duyệt
     * @throws AppException Nếu không có nội dung
     */
    @Override
    public List<WorkshopResponse> getAllWorkShopByState(Pageable pageable, State state, String keyword) {
        List<Workshop> list = workShopRepository
                .getAllWorkshopByState(pageable, state, keyword)
                .getContent();
        if (list.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        return list.stream()
                .map(workshop -> modelMapper.map(workshop, WorkshopResponse.class))
                .toList();
    }

    @Override
    public List<WorkshopResponse> getAllWorkShopByLocation(Pageable pageable, Integer provinceId) {
        List<Workshop> list = workShopRepository
                .getAllWorkShopByLocation(pageable, provinceId)
                .getContent();
        if (list.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        return list.stream()
                .map(workshop -> modelMapper.map(workshop, WorkshopResponse.class))
                .toList();
    }

    /**
     * Cập nhật thông tin một Workshop theo ID.
     * @param id ID của Workshop cần cập nhật
     * @param workShopRequest Thông tin Workshop mới
     * @return Thông tin phản hồi của Workshop đã cập nhật
     * @throws AppException Nếu Workshop không tồn tại hoặc các ngày không hợp lệ
     */
    @PreAuthorize("hasAuthority('SCOPE_UNIVERSITY')")
    @Override
    public WorkshopResponse updateWordShop(Integer id, WorkShopRequest workShopRequest) {
        // Kiểm tra tính hợp lệ của yêu cầu cập nhật Workshop
        validateWorkShop(workShopRequest);

        // Lấy thông tin Workshop hiện tại từ cơ sở dữ liệu
        Workshop workshop = findWorkshopById(id);


        // Cập nhật thông tin địa điểm nếu có thay đổi
        Location location = updateLocationIfNeeded(workshop, workShopRequest);

        // Lưu lại ID của ảnh Workshop cũ để xóa sau này nếu có ảnh mới
        Integer oldImageId = workshop.getWorkshopImageId();

        // Cập nhật ảnh nếu có ảnh mới
        Integer newImageId = updateWorkshopImage(workShopRequest);

        // Ánh xạ các trường từ yêu cầu vào đối tượng Workshop
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(workShopRequest, workshop);
        workshop.setLocation(location);
        if (newImageId != null) {
            workshop.setWorkshopImageId(newImageId);
            deleteOldImageIfExists(oldImageId, newImageId);
        }
        workshop.setStatusBrowse(PENDING);
        // Lưu Workshop đã cập nhật vào cơ sở dữ liệu
        Workshop savedWorkShop = workShopRepository.save(workshop);

        // Xóa ảnh cũ nếu có

        // Trả về đối tượng phản hồi với thông tin Workshop đã cập nhật
        return modelMapper.map(savedWorkShop, WorkshopResponse.class);
    }

    private Workshop findWorkshopById(Integer id) {
        return workShopRepository.findById(id)
                .orElseThrow(() -> new AppException(ERROR_NO_CONTENT));
    }



    private Location updateLocationIfNeeded(Workshop workshop, WorkShopRequest workShopRequest) {
        LocationRequest locationRequest = new LocationRequest();
        boolean isUpdated = false;

        // Kiểm tra nếu có sự thay đổi về district
        if (!Objects.equals(workshop.getLocation().getDistrict().getId(), workShopRequest.getIdDistrict())) {
            locationRequest.setDistrictId(workShopRequest.getIdDistrict());
            isUpdated = true;
        }
        else{
            locationRequest.setDistrictId(workshop.getLocation().getDistrict().getId());
        }

        // Kiểm tra nếu có sự thay đổi về province
        if (!Objects.equals(workshop.getLocation().getProvince().getId(), workShopRequest.getIdProvince())) {
            locationRequest.setProvinceId(workShopRequest.getIdProvince());
            isUpdated = true;
        }
        else{
            locationRequest.setProvinceId(workshop.getLocation().getProvince().getId());
        }

        // Kiểm tra nếu có sự thay đổi về ward
        if (!Objects.equals(workshop.getLocation().getWard().getId(), workShopRequest.getIdWard())) {
            locationRequest.setWardId(workShopRequest.getIdWard());
            isUpdated = true;
        }
        else{
            locationRequest.setWardId(workshop.getLocation().getWard().getId());
        }
        if (!Objects.equals(workshop.getLocation().getDescription(), workShopRequest.getAddressDescription())) {
            locationRequest.setDescription(workShopRequest.getAddressDescription());
            isUpdated = true;
        }
        else{
            locationRequest.setDescription(workshop.getLocation().getDescription());
        }

        // Chỉ gọi saveLocation nếu có thay đổi
        if (isUpdated) {
            return locationService.saveLocation(locationRequest);
        }

        // Nếu không có thay đổi, trả lại địa điểm cũ
        return workshop.getLocation();
    }


    private Integer updateWorkshopImage(WorkShopRequest workShopRequest) {
        if (!Objects.isNull(workShopRequest.getImageWorkshop())) {
            return imageService.uploadFile(workShopRequest.getImageWorkshop());
        }
        return null;
    }

    private void deleteOldImageIfExists(Integer oldImageId, Integer newImageId) {
        if (oldImageId != null && !oldImageId.equals(newImageId)) {
            imageService.delete(oldImageId);
        }
    }

    /**
     * Kiểm tra tính hợp lệ của Workshop.
     * @param workShopRequest Thông tin Workshop cần kiểm tra
     * @throws AppException Nếu ngày tháng không hợp lệ
     */
    public void validateWorkShop(WorkShopRequest workShopRequest) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if(workShopRequest.getEndDate().isBefore(localDateTime)){
            throw new AppException(ERROR_WORK_SHOP_DATE_OUT_DATE);
        }
        // Kiểm tra ngày bắt đầu và kết thúc
        if (workShopRequest.getStartDate().isAfter(workShopRequest.getEndDate())) {
            throw new AppException(ERROR_WORK_SHOP_DATE);
        }

        LocalDate startDate = workShopRequest.getStartDate().toLocalDate();
        LocalDate endDate = workShopRequest.getEndDate().toLocalDate();
        LocalDate expireDate = workShopRequest.getExpireDate(); // Đảm bảo rằng expireDate là LocalDate

        // Kiểm tra ngày hết hạn
        if (expireDate.isAfter(endDate) || expireDate.isBefore(startDate)) {
            throw new AppException(ERROR_WORK_SHOP_DATE);
        }
    }

    /**
     * Cập nhật thông tin một Workshop theo ID.
     *
     * @param id ID của Workshop cần xoá mềm
     * @return Thông tin phản hồi của Workshop đã xoá mềm
     * @throws AppException Nếu Workshop không tồn tại
     */
    @PreAuthorize("hasAuthority('SCOPE_UNIVERSITY')")
    @Override
    public WorkshopResponse removeWorkShop(Integer id, ContentDeleteWorkShopRequest content) throws ParseException {
        Workshop workshop = workShopRepository.findById(id).orElse(null);
        if (Objects.isNull(workshop)) {
            throw new AppException(ERROR_NO_CONTENT); // Ném lỗi nếu Workshop không tồn tại
        }
        workshop.setStatus(Status.INACTIVE);
        List<String> listEmail = workShopRepository.listEmailJoinWorkShop(id);
        if (listEmail != null && !listEmail.isEmpty()) {
            String message = String.format("%s: %s", workshop.getTitle(),DELETE_WORK_SHOP);
            notificationService.send(NotificationSendRequest.of(listEmail, message, content.getContent()));
        }
        workShopRepository.save(workshop);
        return modelMapper.map(workshop, WorkshopResponse.class);
    }

    /**
     * : Lấy  thông tin một Workshop theo ID.
     *
     * @param id ID của Workshop
     * @return Thông tin phản hồi của Workshop
     * @throws AppException Nếu Workshop không tồn tại
     */
    @Override
    public WorkshopResponse getWorkShopById(Integer id) {
        Workshop workshopById = workShopRepository.findById(id).orElse(null);
        if (Objects.isNull(workshopById)) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        return modelMapper.map(workshopById, WorkshopResponse.class);
    }

    /**
     * Phê duyệt một Workshop.
     *
     * @param req Dữ liệu yêu cầu phê duyệt Workshop.
     * @return Phản hồi phê duyệt Workshop.
     * @throws ParseException Nếu xảy ra lỗi định dạng dữ liệu.
     */
    @Override
    public WorkshopApprovedResponse approved(WorkshopApprovedRequest req) throws ParseException {

        Workshop workshop = findWorkshopById(req.getId());
        validateWorkshopForStateChange(workshop, State.APPROVED);
        workshop.setStatusBrowse(State.APPROVED);
        workShopRepository.save(workshop);

        String emailBusiness = workshop.getUniversity().getEmail();

        EmailDTO emailDTO = new EmailDTO(emailBusiness, APPROVED_WORKSHOP, "");
        sendEmail.sendApprovedWorkshopNotification(emailDTO,
                workshop.getTitle());

        String message = String.format("%s: %s", APPROVED_WORKSHOP, workshop.getTitle());
        notificationService.send(NotificationSendRequest.of(Collections.singletonList(emailBusiness), APPROVED_WORKSHOP, message));
        return WorkshopApprovedResponse.of(Boolean.TRUE);
    }

    /**
     * Từ chối một Workshop.
     *
     * @param req Dữ liệu yêu cầu từ chối Workshop.
     * @return Phản hồi từ chối Workshop.
     * @throws ParseException Nếu xảy ra lỗi định dạng dữ liệu.
     */
    @Override
    public WorkshopRejectedResponse rejected(WorkshopRejectedRequest req) throws ParseException {

        Workshop workshop = findWorkshopById(req.getId());
        validateWorkshopForStateChange(workshop, State.REJECTED);
        workshop.setStatusBrowse(State.REJECTED);
        workShopRepository.save(workshop);

        // Gửi thông báo email
        String emailUniversity = workshop.getUniversity().getEmail();
        EmailDTO emailDTO = new EmailDTO(emailUniversity, REJECTED_WORKSHOP, "");
        sendEmail.sendRRejectedWorkshopNotification(emailDTO, workshop.getTitle(), req.getMessage());

        // Gửi thông báo hệ thống
        String message = String.format("%s: %s", REJECTED_WORKSHOP, workshop.getTitle());
        notificationService.send(NotificationSendRequest.of(Collections.singletonList(emailUniversity), REJECTED_WORKSHOP, message));
        return WorkshopRejectedResponse.of(req.getMessage());
    }

    /**
     * Lấy danh sách Workshop phân trang theo trạng thái.
     *
     * @param info  Thông tin phân trang.
     * @param state Trạng thái của Workshop.
     * @return Trang kết quả chứa danh sách Workshop.
     */
    @Override
    public PagingResponse<AdminWorkshopResponse> getPagingByState(PageInfo info, State state) {
        Pageable pageable = PageRequest.of(info.getPageNo(), info.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(info.getKeyword());
        Page<Workshop> workshops = workShopRepository.findAllByState(pageable, state, keyword) ;
        Page<AdminWorkshopResponse> res = workshops.map(w -> modelMapper.map(w, AdminWorkshopResponse.class));
        return new PagingResponse<>(res);
    }

    @Override
    public PagingResponse<AdminWorkshopResponse> getPagingWorkshop(PageInfo req) {
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<Workshop> workshops = workShopRepository.findAll(pageable, keyword) ;
        Page<AdminWorkshopResponse> res = workshops.map(w -> modelMapper.map(w, AdminWorkshopResponse.class));
        return new PagingResponse<>(res);
    }

    @Override
    public AdminWorkshopResponse detail(Integer id) {
        Workshop workshop = findWorkshopById(id);
        return modelMapper.map(workshop, AdminWorkshopResponse.class);
    }

    @Override
    public List<Workshop> findAll() {
        return workShopRepository.findAll();
    }

    @Override
    public Long countTotalWorkShop() {
        return workShopRepository.countWorkShop(ACTIVE,APPROVED);
    }

    @PreAuthorize("hasAuthority('SCOPE_BUSINESS')")
    @Override
    public PagingResponse<WorkshopStateBusiness> getAllWorkShopByPracticeBusiness(Pageable pageable, Integer businessID, String keyword,State state) {
        Page<WorkshopStateBusiness> workshops = workShopRepository.findAllWorkShopByBusinessId(ACTIVE,businessID,state,sanitizeKeyword(keyword),pageable);
        if(Objects.isNull(workshops)){
            throw new AppException(ERROR_NO_CONTENT);
        }
        return new PagingResponse<>(workshops);
    }

    @Override
    public PagingResponse<WorkshopPortalResponse> getAllWorkShopApprovedAndLocation(Pageable pageable, LocalDate startDate, LocalDate endDate, Integer provinceId, Integer universityId, String keyword) {
        // Chuyển đổi LocalDate thành LocalDateTime
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        String sanitizedKeyword = sanitizeKeyword(keyword);
        Page<WorkshopPortalResponse> workshops = workShopRepository.getAllWorkshopApprovedAndLocation(
                pageable,
                APPROVED,
                provinceId,
                startDateTime,
                endDateTime,
                universityId,
                ACTIVE,
                sanitizedKeyword
        );

        return new PagingResponse<>(workshops);
    }

    @Override
    public WorkshopPortalResponse getWorkShopPortalById(Integer workShopId) {
        WorkshopPortalResponse workShopPortalResponse = workShopRepository.getWorkShopDetailsById(workShopId,APPROVED);
        if(Objects.isNull(workShopPortalResponse)){
            throw new AppException(ERROR_NO_CONTENT);
        }
        return workShopPortalResponse;
    }


    /**
     * Kiểm tra tính hợp lệ của trạng thái Workshop khi chuyển đổi.
     *
     * @param req         Thông tin Workshop.
     * @param targetState Trạng thái mục tiêu cần chuyển.
     * @throws AppException Nếu trạng thái không hợp lệ hoặc không thể chuyển đổi.
     */
    private void validateWorkshopForStateChange(Workshop req, State targetState) {
        // Kiểm tra nếu trạng thái hiện tại giống với trạng thái mục tiêu
        if (req.getStatusBrowse() == targetState) {
            throw new AppException(targetState == State.APPROVED
                    ? ERROR_WORKSHOP_ALREADY_APPROVED
                    : ERROR_WORKSHOP_ALREADY_REJECTED);
        }

        // Kiểm tra trạng thái không hợp lệ (chỉ cho phép thay đổi từ PENDING)
        if (req.getStatusBrowse() != State.PENDING) {
            throw new AppException(ERROR_INVALID_WORKSHOP_STATE);
        }
    }
    @Override
    public long countWorkShop(Integer universityId) {
        return workShopRepository.countWorkShopByUniversityId(universityId);
    }

    @Override
    public List<WorkshopCountResponse> countWorkshopByApproved(Integer universityId, State state) {
        return workShopRepository.countWorkshopByApproved(universityId, state);
    }
}
