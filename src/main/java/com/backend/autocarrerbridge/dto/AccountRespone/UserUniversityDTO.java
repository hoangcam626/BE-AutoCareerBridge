package com.backend.autocarrerbridge.dto.AccountRespone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUniversityDTO {

     @NotBlank(message = "Tên không được để trống")
     @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
     String name;

     @NotBlank(message = "Email không được để trống")
     @Email(message = "Email không hợp lệ")
     String email;

     @NotBlank(message = "Số điện thoại không được để trống")
     @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
     String phone;

     @NotBlank(message = "Mật khẩu không được để trống")
     @Size(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 ký tự")
     String password;

     @NotBlank(message = "Vui lòng nhập lại mật khẩu")
     String rePassword;

     @NotBlank(message = "Vui lòng không được để trống")
     String verificationCode;
}
