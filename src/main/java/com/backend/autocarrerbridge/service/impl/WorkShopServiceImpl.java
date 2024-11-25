package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopResponse;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.util.List;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_CONTENT;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_WORK_SHOP_DATE;
import static com.backend.autocarrerbridge.util.enums.State.PENDING;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkShopServiceImpl implements WorkShopService {

    // Khai báo các repository và service cần thiết
    WorkShopRepository workShopRepository;
    ModelMapper modelMapper;
    ImageService imageService;
    UniversityService universityService;

    /**
     * Lấy danh sách tất cả các Workshop với phân trang.
     * @param pageable Thông tin phân trang
     * @return Danh sách các Workshop phản hồi
     */
    @Override
    public List<WorkShopResponse> getAllWorkShop(Pageable pageable) {
        List<Workshop> list = workShopRepository.getAllWorkShop(pageable).getContent();
        return list.stream().map(workshop -> modelMapper.map(workshop, WorkShopResponse.class)).toList();
    }

    /**
     * Lấy danh sách Workshop của một trường đại học cụ thể với phân trang.
     * @param pageable Thông tin phân trang
     * @param universityId ID của trường đại học
     * @return Danh sách các Workshop của trường đại học
     * @throws AppException Nếu không có nội dung
     */
    @Override
    public List<WorkShopResponse> getAllWorkShopByUniversity(Pageable pageable, Integer universityId) {
        List<Workshop> list = workShopRepository.getAllWorkShopByUniversity(pageable, universityId).getContent();
        if (list.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        return list.stream().map(workshop -> modelMapper.map(workshop, WorkShopResponse.class)).toList();
    }

    /**
     * Lấy danh sách Workshop theo vị trí (hiện tại trả về danh sách rỗng).
     * @return Danh sách Workshop theo vị trí
     */
    @Override
    public List<WorkShopResponse> getAllWorkShopByLocation() {
        return List.of();
    }

    /**
     * Tạo một Workshop mới.
     * @param workShopRequest Thông tin Workshop cần tạo
     * @return Thông tin phản hồi của Workshop vừa tạo
     * @throws AppException Nếu có lỗi về trạng thái hoặc trường đại học không tồn tại
     */
    @Override
    public WorkShopResponse createWorkShop(WorkShopRequest workShopRequest) {
        validateWorkShop(workShopRequest); // Kiểm tra tính hợp lệ của Workshop
        Integer imageId = imageService.uploadFile(workShopRequest.getImageWorkshop()); // Tải ảnh lên
        workShopRequest.setStatus(Status.ACTIVE); // Đặt trạng thái ACTIVE
        workShopRequest.setStatusBrowse(PENDING); // Đặt trạng thái duyệt là PENDING
        University university = universityService.findById(workShopRequest.getUniversityId()); // Tìm trường đại học
        if (university == null) {
            throw new AppException(ERROR_NO_CONTENT); // Ném lỗi nếu trường không tồn tại
        }
        // Ánh xạ từ request sang entity và lưu vào cơ sở dữ liệu
        Workshop workshop = modelMapper.map(workShopRequest, Workshop.class);
        workshop.setWorkshopImageId(imageId); // Gắn ảnh cho Workshop
        workshop.setUniversity(university); // Gắn trường đại học cho Workshop
        return modelMapper.map(workShopRepository.save(workshop), WorkShopResponse.class);
    }

    /**
     * Lấy danh sách Workshop đã được phê duyệt theo trạng thái cụ thể với phân trang.
     * @param pageable Thông tin phân trang
     * @param state Trạng thái cần lọc
     * @return Danh sách Workshop đã được phê duyệt
     * @throws AppException Nếu không có nội dung
     */
    @Override
    public List<WorkShopResponse> getAllWorkShopByState(Pageable pageable, State state) {
        List<Workshop> list = workShopRepository.getAllApprovedWorkshop(pageable, state).getContent();
        if (list.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        return list.stream().map(workshop -> modelMapper.map(workshop, WorkShopResponse.class)).toList();
    }

    /**
     * Cập nhật thông tin một Workshop theo ID.
     * @param id ID của Workshop cần cập nhật
     * @param workShopRequest Thông tin Workshop mới
     * @return Thông tin phản hồi của Workshop đã cập nhật
     * @throws AppException Nếu Workshop không tồn tại hoặc các ngày không hợp lệ
     */
    @Override
    public WorkShopResponse updateWordShop(Integer id, WorkShopRequest workShopRequest) {
        // Kiểm tra tính hợp lệ của yêu cầu cập nhật Workshop
        validateWorkShop(workShopRequest);

        // Lấy thông tin Workshop hiện tại từ cơ sở dữ liệu, nếu không tìm thấy thì ném lỗi
        Workshop workshop = workShopRepository.findById(id).orElse(null);
        if (workshop == null) {
            throw new AppException(ERROR_NO_CONTENT); // Ném lỗi nếu Workshop không tồn tại
        }

        // Kiểm tra xem ngày bắt đầu và kết thúc của Workshop có hợp lệ với yêu cầu không
        if (workshop.getStartDate().isAfter(workShopRequest.getEndDate()) ||
                workshop.getEndDate().isBefore(workShopRequest.getStartDate())) {
            throw new AppException(ERROR_WORK_SHOP_DATE); // Ném lỗi nếu ngày không hợp lệ
        }

        // Kiểm tra xem ngày hết hạn có nằm trong khoảng ngày của Workshop không
        if (workshop.getExpireDate().isBefore(workShopRequest.getStartDate().toLocalDate()) ||
                workshop.getExpireDate().isAfter(workShopRequest.getEndDate().toLocalDate())) {
            throw new AppException(ERROR_WORK_SHOP_DATE); // Ném lỗi nếu ngày hết hạn không hợp lệ
        }

        // Kiểm tra nếu ngày hết hạn yêu cầu nằm ngoài khoảng thời gian của Workshop
        if (workShopRequest.getExpireDate().isAfter(workshop.getEndDate().toLocalDate()) ||
                workShopRequest.getExpireDate().isBefore(workshop.getStartDate().toLocalDate())) {
            throw new AppException(ERROR_WORK_SHOP_DATE); // Ném lỗi nếu ngày hết hạn yêu cầu không hợp lệ
        }

        // Lưu lại ID của ảnh Workshop cũ để xóa sau này nếu có ảnh mới
        Integer oldImageId = workshop.getWorkshopImageId();

        // Cấu hình Mapper để bỏ qua các giá trị null trong quá trình ánh xạ
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // Nếu yêu cầu có ảnh mới, tiến hành tải lên và cập nhật ID ảnh cho Workshop
        if (workShopRequest.getImageWorkshop() != null) {
            Integer imageId = imageService.uploadFile(workShopRequest.getImageWorkshop());
            workshop.setWorkshopImageId(imageId); // Cập nhật ID ảnh mới
        }

        // Ánh xạ các trường từ yêu cầu vào đối tượng Workshop
        modelMapper.map(workShopRequest, workshop);

        // Lưu Workshop đã cập nhật vào cơ sở dữ liệu
        Workshop savedWorkShop = workShopRepository.save(workshop);

        // Xóa ảnh cũ nếu có
        imageService.delete(oldImageId);

        // Trả về đối tượng phản hồi với thông tin Workshop đã cập nhật
        return modelMapper.map(savedWorkShop, WorkShopResponse.class);
    }

    /**
     * Kiểm tra tính hợp lệ của Workshop.
     * @param workShopRequest Thông tin Workshop cần kiểm tra
     * @throws AppException Nếu ngày tháng không hợp lệ
     */
    public void validateWorkShop(WorkShopRequest workShopRequest) {
        if (workShopRequest.getStartDate().isAfter(workShopRequest.getEndDate()) ||
                workShopRequest.getEndDate().isBefore(workShopRequest.getStartDate())) {
            throw new AppException(ERROR_WORK_SHOP_DATE); // Kiểm tra ngày bắt đầu và kết thúc
        }
        LocalDate endDate = workShopRequest.getEndDate().toLocalDate();
        LocalDate startDate = workShopRequest.getStartDate().toLocalDate();
        if (workShopRequest.getExpireDate().isAfter(endDate) || workShopRequest.getExpireDate().isBefore(startDate)) {
            throw new AppException(ERROR_WORK_SHOP_DATE); // Kiểm tra ngày hết hạn
        }
    }
    /**
     * Cập nhật thông tin một Workshop theo ID.
     * @param id ID của Workshop cần xoá mềm
     * @return Thông tin phản hồi của Workshop đã xoá mềm
     * @throws AppException Nếu Workshop không tồn tại
     */
    @Override
    public WorkShopResponse removeWorkShop(Integer id) {
        Workshop workshop = workShopRepository.findById(id).orElse(null);
        if (workshop == null) {
            throw new AppException(ERROR_NO_CONTENT); // Ném lỗi nếu Workshop không tồn tại
        }
        workshop.setStatus(Status.INACTIVE);
        workShopRepository.save(workshop);
        return modelMapper.map(workshop,WorkShopResponse.class);
    }
    /**
     * : Lấy  thông tin một Workshop theo ID.
     * @param id ID của Workshop
     * @return Thông tin phản hồi của Workshop
     * @throws AppException Nếu Workshop không tồn tại
     */
    @Override
    public WorkShopResponse getWorkShopById(Integer id) {
        Workshop workshopById = workShopRepository.findById(id).orElse(null);
        if(workshopById == null) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        return modelMapper.map(workshopById,WorkShopResponse.class);
    }

}