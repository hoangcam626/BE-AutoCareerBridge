package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.service.DistrictService;
import com.backend.autocarrerbridge.service.ProvinceService;
import com.backend.autocarrerbridge.service.WardService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller xử lý các API liên quan đến quản lý đơn vị hành chính bao gồm tỉnh, huyện và xã.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/administrative")
public class AdministrativeController {
    private final ProvinceService provinceService;
    private final DistrictService districtService;
    private final WardService wardService;

    /**
     * API lấy danh sách tất cả các tỉnh.
     *
     * @return ApiResponse chứa danh sách các tỉnh.
     */
    @GetMapping("/get-all-provinces")
    public ApiResponse<List<ProvinceResponse>> getAllProvinces() {
        var res = provinceService.getAll();
        return new ApiResponse<>(res);
    }

    /**
     * API lấy thông tin chi tiết của một tỉnh theo ID.
     *
     * @param id - ID của tỉnh cần lấy thông tin.
     * @return ApiResponse chứa thông tin chi tiết của tỉnh.
     */
    @GetMapping("/get-province")
    public ApiResponse<ProvinceResponse> getProvinceById(@RequestParam("id")
                                                             @NotBlank(message = "ERROR_PROVINCE_NOT_BLANK")  Integer id) {
        var res = provinceService.getById(id);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách tất cả các huyện thuộc một tỉnh.
     *
     * @param provinceId - ID của tỉnh để lấy danh sách huyện.
     * @return ApiResponse chứa danh sách các huyện thuộc tỉnh.
     */
    @GetMapping("/get-all-districts")
    public ApiResponse<List<DistrictResponse>> getAllDistricts(@RequestParam(value = "provinceId")
                                                                   @NotBlank(message = "ERROR_PROVINCE_NOT_BLANK") Integer provinceId) {
        var res = districtService.getAllByProvinceId(provinceId);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy thông tin chi tiết của một huyện theo ID.
     *
     * @param id ID của huyện cần lấy thông tin.
     * @return ApiResponse chứa thông tin chi tiết của huyện.
     */
    @GetMapping("/get-district")
    public ApiResponse<DistrictResponse> getDistrictById(@RequestParam("id") @NotBlank(message = "ERROR_DISTRICT_NOT_BLANK")  Integer id) {
        var res = districtService.getById(id);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy danh sách tất cả các xã/phường thuộc một huyện.
     *
     * @param districtId - ID của huyện để lấy danh sách xã/phường.
     * @return ApiResponse chứa danh sách các xã/phường thuộc huyện.
     */
    @GetMapping("/get-all-wards")
    public ApiResponse<List<WardResponse>> getAllWards(@RequestParam("districtId") @NotBlank(message = "ERROR_DISTRICT_NOT_BLANK")  Integer districtId) {
        var res = wardService.getAllByDistrictId(districtId);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy thông tin chi tiết của một xã/phường theo ID.
     *
     * @param id - ID của xã/phường cần lấy thông tin.
     * @return ApiResponse chứa thông tin chi tiết của xã/phường.
     */
    @GetMapping("/get-ward")
    public ApiResponse<WardResponse> getWardById(@RequestParam("id") @NotBlank(message = "ERROR_WARD_NOT_BLANK")  Integer id) {
        var res = wardService.getById(id);
        return new ApiResponse<>(res);
    }
}
