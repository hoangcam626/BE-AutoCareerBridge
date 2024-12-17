package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_FAIL_WORK_SHOP;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_CONTENT;
import static com.backend.autocarrerbridge.util.Constant.REJECT_ACCEPT_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.REQUEST_TO_ATTEND_WORKSHOP;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_ACCEPT_MESSAGE;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopBusinessRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessColabResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopBusinessReponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Workshop;
import com.backend.autocarrerbridge.entity.WorkshopBusiness;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.WorkShopBusinessRepository;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.WorkShopBusinessService;
import com.backend.autocarrerbridge.service.WorkShopService;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.RequiredArgsConstructor;

/**
 * Service thực hiện các chức năng liên quan đến kết nối giữa Workshop và Business.
 * - Lấy thông tin các doanh nghiệp đã tham gia workshop.
 * - Xử lý yêu cầu tham gia workshop của doanh nghiệp.
 */
@Service
@RequiredArgsConstructor
public class WorkShopBusinessServiceImpl implements WorkShopBusinessService {

    // Các đối tượng repository và service cần thiết để thực hiện các chức năng
    private final WorkShopBusinessRepository workShopBussinessRepository;
    private final WorkShopService workShopService;
    private final ModelMapper modelMapper;
    private final BusinessService businessService;

    /**
     * Lấy tất cả các doanh nghiệp tham gia một workshop cụ thể.
     * @param workshopId - ID của workshop.
     * @param pageable - Đối tượng phân trang.
     * @param state - Trạng thái kết nối.
     * @return - Thông tin về workshop và danh sách các doanh nghiệp tham gia.
     */
    public WorkShopBusinessReponse getAllColabBusiness(Integer workshopId, Pageable pageable, State state) {
        if (state != State.PENDING && state != State.APPROVED && state != State.REJECTED) {
            throw new AppException(ERROR_NO_CONTENT);
        }

        // Lấy thông tin workshop theo ID
        WorkShopResponse workShopResponse = workShopService.getWorkShopById(workshopId);

        // Lấy danh sách các doanh nghiệp tham gia workshop
        List<Business> businesses = workShopBussinessRepository
                .getAllBusiness(workshopId, state, pageable)
                .getContent();
        if (businesses.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }
        // Chuyển đổi danh sách doanh nghiệp thành BusinessResponse
        List<BusinessColabResponse> businessResponses = businesses.stream()
                .map(business -> modelMapper.map(business, BusinessColabResponse.class))
                .toList();

        // Tạo và trả về response chứa thông tin workshop và danh sách doanh nghiệp
        WorkShopBusinessReponse workShopBusinessReponse = new WorkShopBusinessReponse();
        workShopBusinessReponse.setWorkshop(workShopResponse);
        workShopBusinessReponse.setBusinessList(businessResponses);

        return workShopBusinessReponse;
    }

    /**
     * Xử lý yêu cầu tham gia workshop của doanh nghiệp.
     * @param workShopBusinessRequest - Thông tin yêu cầu tham gia workshop của doanh nghiệp.
     * @return - Thông báo kết quả yêu cầu tham gia.
     */
    @Override
    public String requestToAttend(WorkShopBusinessRequest workShopBusinessRequest) {
        // Kiểm tra xem doanh nghiệp đã tham gia workshop chưa
        if (!Objects.isNull(workShopBussinessRepository.checkExistWorkShop(
                        workShopBusinessRequest.getWorkshopID(), workShopBusinessRequest.getBusinessID()))) {
            throw new AppException(ERROR_FAIL_WORK_SHOP);
        }

        // Lấy thông tin workshop và doanh nghiệp
        Workshop workshop = modelMapper.map(
                workShopService.getWorkShopById(workShopBusinessRequest.getWorkshopID()), Workshop.class);
        Business business = businessService.getBusinessById(workShopBusinessRequest.getBusinessID());

        // Kiểm tra nếu workshop hoặc doanh nghiệp không tồn tại
        if (Objects.isNull(business) || Objects.isNull(workshop)) {
            throw new AppException(ErrorCode.ERROR_NO_CONTENT);
        }

        // Tạo mới đối tượng WorkshopBusiness và lưu vào database
        WorkshopBusiness workshopBusiness = new WorkshopBusiness();
        workshopBusiness.setBusiness(business);
        workshopBusiness.setWorkshop(workshop);
        workshopBusiness.setStatusConnected(State.PENDING);

        workShopBussinessRepository.save(workshopBusiness);

        return REQUEST_TO_ATTEND_WORKSHOP;
    }

    /**
     * Chấp nhận kết nối giữa workshop và business.
     * Kiểm tra xem workshop và business có tồn tại và trạng thái hiện tại có phù hợp để chấp nhận không.
     * Nếu đã tồn tại và được phê duyệt trước đó, ném ngoại lệ. Nếu không, cập nhật trạng thái kết nối thành "APPROVED".
     *
     * @param workShopBusinessRequest Đối tượng chứa thông tin workshopID và businessID.
     * @return Thông báo thành công nếu chấp nhận kết nối thành công.
     * @throws AppException Nếu không tìm thấy kết nối hoặc kết nối đã được chấp nhận trước đó.
     */
    @Override
    public String acceptBusiness(WorkShopBusinessRequest workShopBusinessRequest) {

        // Kiểm tra sự tồn tại của kết nối giữa workshop và business dựa vào workshopID và businessID.
        WorkshopBusiness workshopBusiness = workShopBussinessRepository.checkExistWorkShop(
                workShopBusinessRequest.getWorkshopID(), workShopBusinessRequest.getBusinessID());

        // Nếu không tìm thấy kết nối, ném ngoại lệ với mã lỗi NOT_FOUNDED.
        if (Objects.isNull(workshopBusiness)) {
            throw new AppException(ErrorCode.NOT_FOUNDED);
        }

        // Nếu kết nối đã ở trạng thái "APPROVED", ném ngoại lệ với mã lỗi ERROR_ALREADY_ACCEPT.
        if (workshopBusiness.getStatusConnected().equals(State.APPROVED)) {
            throw new AppException(ErrorCode.ERROR_ALREADY_ACCEPT);
        }

        // Cập nhật trạng thái kết nối thành "APPROVED".
        workshopBusiness.setStatusConnected(State.APPROVED);

        // Lưu trạng thái kết nối đã cập nhật vào cơ sở dữ liệu.
        workShopBussinessRepository.save(workshopBusiness);

        // Trả về thông báo thành công sau khi cập nhật trạng thái.
        return SUCCESS_ACCEPT_MESSAGE;
    }

    @Override
    public String rejectBusiness(WorkShopBusinessRequest workShopBusinessRequest) {
        WorkshopBusiness workshopBusiness = workShopBussinessRepository.checkExistWorkShop(
                workShopBusinessRequest.getWorkshopID(), workShopBusinessRequest.getBusinessID());

        // Nếu không tìm thấy kết nối, ném ngoại lệ với mã lỗi NOT_FOUNDED.
        if (Objects.isNull(workshopBusiness)) {
            throw new AppException(ErrorCode.NOT_FOUNDED);
        }

        // Nếu kết nối đã ở trạng thái "APPROVED", ném ngoại lệ với mã lỗi ERROR_ALREADY_ACCEPT.
        if (workshopBusiness.getStatusConnected().equals(State.APPROVED)) {
            throw new AppException(ErrorCode.ERROR_ALREADY_ACCEPT);
        }

        // Cập nhật trạng thái kết nối thành "APPROVED".
        workshopBusiness.setStatusConnected(State.REJECTED);

        // Lưu trạng thái kết nối đã cập nhật vào cơ sở dữ liệu.
        workShopBussinessRepository.save(workshopBusiness);

        // Trả về thông báo thành công sau khi cập nhật trạng thái.
        return REJECT_ACCEPT_MESSAGE;
    }
}
