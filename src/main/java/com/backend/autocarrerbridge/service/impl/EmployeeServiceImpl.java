package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.util.Validation;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.employee.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.employee.EmployeeResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.mapper.EmployeeMapper;
import com.backend.autocarrerbridge.mapper.UserAccountMapper;
import com.backend.autocarrerbridge.repository.EmployeeRepository;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.EmployeeService;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.RoleService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    // Các dependency được tiêm vào thông qua constructor
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    UserAccountService userAccountService;
    TokenService tokenService;
    BusinessService businessService;
    UserAccountMapper userAccountMapper;
    RoleService roleService;
    ImageService imageService;

    public String generateEmployeeCode(String emailBusiness, int lastEmployeeId) {
        // Lấy phần trước dấu @
        String usernamePart = emailBusiness.split("@")[0];

        // Đảm bảo usernamePart có ít nhất 3 ký tự
        String initials = usernamePart.length() >= 3
                ? usernamePart.substring(0, 3).toUpperCase()
                : (usernamePart + "X").substring(0, 3).toUpperCase();

        int nextId = lastEmployeeId + 1;
        // Tạo mã nhân viên theo định dạng + employeeId
        return initials + String.format("%05d", nextId);
    }


    @Override
    public List<EmployeeResponse> getListEmployeee() throws ParseException {
        // Lấy thông tin xác thực của người dùng hiện tại từ SecurityContextHolder
        var emailAccountLogin = tokenService.getClaim(tokenService.getJWT(), SUB);

        // Lấy danh sách nhân viên theo email doanh nghiệp
        var employees = employeeRepository.findEmployeesByBusinessEmail(emailAccountLogin);

        // Chuyển đổi danh sách nhân viên sang danh sách EmployeeResponse
        return employees.stream()
                .map(employee -> {
                    EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(employee);
                    employeeResponse.setBusinessId(employee.getBusiness().getId()); // Thêm thông tin BusinessId
                    return employeeResponse;
                })
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Integer id) {
        // Tìm nhân viên theo ID, nếu không tìm thấy thì ném ngoại lệ
        var employee =
                employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));

        // Chuyển đổi Employee sang EmployeeResponse và thêm thông tin BusinessId
        EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(employee);
        employeeResponse.setBusinessId(employee.getBusiness().getId());
        return employeeResponse;
    }

    @Transactional
    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request) {
        // Chuyển đổi từ EmployeeRequest sang Employee Entity
        Employee employee = employeeMapper.toEmployee(request);
        if (Objects.nonNull(employeeRepository.findByUsername(employee.getEmail()))) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }
        if(Objects.nonNull(employeeRepository.findByPhone(request.getPhone())))
            throw new AppException(ErrorCode.ERROR_PHONE_EXIST);
        try {
            // Lấy email doanh nghiệp từ token và gán doanh nghiệp cho nhân viên
            String emailBusiness = tokenService.getClaim(tokenService.getJWT(), SUB);
            Business business = businessService.findByEmail(emailBusiness);
            employee.setBusiness(business);
            employee.setCreatedBy(emailBusiness);

            // set anh cho nhan vien
            if (Objects.nonNull(request.getEmployeeImage()))
                employee.setEmployeeImageId(imageService.uploadFile(request.getEmployeeImage()));

            // set ma tu gen cho nhan vien
            int idLast;
            if (Objects.isNull(employeeRepository.getLastEmployee())) {
                idLast = 0;
            } else idLast = employeeRepository.getLastEmployee();

            employee.setEmployeeCode(generateEmployeeCode(emailBusiness, idLast));
        } catch (ParseException e) {
            // Ném ngoại lệ nếu token không hợp lệ
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }

        // Tạo tài khoản người dùng cho nhân viên
        UserAccount userAccount = UserAccount.builder()
                .username(employee.getEmail()) // Sử dụng email của nhân viên làm tên đăng nhập
                .password("Matkhau123!") // Mật khẩu mặc định (cần mã hóa trước khi lưu)
                .role(roleService.findById(PredefinedRole.EMPLOYEE.getValue())) // Gán vai trò mặc định là EMPLOYEE
                .state(State.APPROVED) // Trạng thái tài khoản là được phê duyệt
                .build();

        // Đăng ký tài khoản người dùng qua UserAccountService
        UserAccount accountEmployee = userAccountService.registerUser(userAccount);
        employee.setUserAccount(accountEmployee); // Gán tài khoản người dùng vào nhân viên

        try {
            // Lưu nhân viên vào cơ sở dữ liệu
            employee = employeeRepository.save(employee);
        } catch (DataIntegrityViolationException exception) {
            // Ném ngoại lệ nếu nhân viên đã tồn tại
            throw new AppException(ErrorCode.ERROR_USER_EXITED);
        }

        // Chuyển đổi Employee sang EmployeeResponse và thêm thông tin liên quan
        EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(employee);
        employeeResponse.setBusinessId(employee.getBusiness().getId());
        userAccount.setRole(employee.getUserAccount().getRole()); // Gán vai trò vào tài khoản
        employeeResponse.setUserAccount(userAccountMapper.toUserAccountResponse(employee.getUserAccount()));

        return employeeResponse; // Trả về thông tin nhân viên vừa tạo
    }

    @Transactional
    @Override
    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest request) {
        // Tìm nhân viên theo ID, nếu không tồn tại thì ném ngoại lệ
        Employee employee =
                employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));
        // Check phone

        if (!Objects.isNull(request.getEmployeeImage())
                && !request.getEmployeeImage().isEmpty()) {
            if (!Objects.isNull(employee.getEmployeeImageId())) imageService.delete(employee.getEmployeeImageId());
            employee.setEmployeeImageId(imageService.uploadFile(request.getEmployeeImage()));
        }

        // Cập nhật thông tin nhân viên từ request
        employeeMapper.updateEmployee(employee, request);

        try {
            var emailAccountLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
            employee.setUpdatedBy(emailAccountLogin);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }
        // Lưu nhân viên đã cập nhật vào cơ sở dữ liệu và chuyển đổi thành EmployeeResponse
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    @Override
    public void restoreEmployee(Integer id) throws ParseException {
        Employee employee=employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));

        // Lấy email doanh nghiệp từ token và gán doanh nghiệp cho nhân viên
        String emailBusiness = tokenService.getClaim(tokenService.getJWT(), SUB);
        employee.setUpdatedBy(emailBusiness);

        //cập nhât trạng thái của nhân viên
        employee.setStatus(Status.ACTIVE);
        //cập nhật trạng thái tài khoản hệ thống của nhân viên
        employee.getUserAccount().setStatus(Status.ACTIVE);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Integer id) {
        // Tìm nhân viên theo ID, nếu không tồn tại thì ném ngoại lệ
        Employee employee =
                employeeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_USER_NOT_FOUND));

        // Cập nhật trạng thái của nhân viên thành INACTIVE
        employee.setStatus(Status.INACTIVE);
        employee.getUserAccount().setStatus(Status.INACTIVE);
        employeeRepository.save(employee); // Lưu thay đổi vào cơ sở dữ liệu
        employeeRepository.flush(); // Đồng bộ dữ liệu với cơ sở dữ liệu
    }

    @Override
    public PagingResponse<EmployeeResponse> getAllEmployeeOfBusinessPage( String keyword, Status status, Pageable pageable) {
        String emailAccountLogin;
        try {
            emailAccountLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }
        // Lấy dữ liệu phân trang từ repository
        String keywordValidated = Validation.escapeKeywordForQuery(keyword);
        var employees = employeeRepository.getEmployeeForPaging(emailAccountLogin, keywordValidated, status,pageable);

        // Chuyển đổi danh sách các employee sang Page<EmployeeResponse>
        Page<EmployeeResponse> list = employees.map(employee -> {
            EmployeeResponse employeeResponse = employeeMapper.toEmployeeResponse(employee);
            // Thêm thông tin businessId vào employeeResponse
            employeeResponse.setBusinessId(employee.getBusiness().getId());
            return employeeResponse;
        });
        return new PagingResponse<>(list);
    }
}
