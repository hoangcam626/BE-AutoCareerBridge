package com.backend.autocarrerbridge.dto.AccountRespone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBusinessDTO {

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    String name;

    @NotBlank(message = "Mã số thuế không được để trống")
    @Size(min = 10, max = 15, message = "Mã số thuế phải từ 10 đến 15 ký tự")
    String taxCode;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    String password;

    @NotBlank(message = "Vui lòng xác nhận lại mật khẩu")
    String rePassword;

    @NotNull(message = "Hình ảnh giấy phép không được để trống")
    MultipartFile licenseImage;

    //  @NotBlank(message = "Vui lòng không được để trống")
    String verificationCode;
}
