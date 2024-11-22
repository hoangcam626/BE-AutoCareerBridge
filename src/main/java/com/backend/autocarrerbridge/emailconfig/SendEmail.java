package com.backend.autocarrerbridge.emailconfig;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class SendEmail {

    private final JavaMailSender mailSender;


    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

    public void sendEmail(Email email, String partner) {
        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email người nhận không được để trống");
            }

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(email, getEmailBody(email, partner));
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.error("Lỗi khi gửi email: {}", e.getMessage(), e);
        }
    }

    public void sendCode(Email email, String verifyCode) {
        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email người nhận không được để trống");
            }

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(email, getVerifyCode(verifyCode));
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.error("Lỗi khi gửi email: {}", e.getMessage(), e);
        }
    }
    public void sendNewPassword(Email email,String newPassword){
        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email người nhận không được để trống");
            }

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(email, getNewPassword(newPassword));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Lỗi khi gửi email: {}", e.getMessage(), e);
        }
    }
    public void sendForgot(Email email,String verifyCode){
        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email người nhận không được để trống");
            }

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(email, getForgotPassword(verifyCode));
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.error("Lỗi khi gửi email: {}", e.getMessage(), e);
        }
    }

    public void sendAccount(Email email, String password){
        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email người nhận không được để trống");
            }
            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(email, getAccount(password));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Lỗi khi gửi email: {}", e.getMessage(), e);
        }
    }

    private MimeMessage createMimeMessage(Email email, String emailBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("quocta.gov@gmail.com");
        helper.setTo(email.getEmail().trim());
        helper.setSubject(email.getSubject());
        helper.setText(emailBody, true); // Cho phép nội dung HTML
        return mimeMessage;
    }

    private String getVerifyCode(String verificationCode) {
        if (verificationCode == null || verificationCode.isEmpty()) {
            throw new IllegalArgumentException("Verification code cannot be null or empty");
        }

        // Dùng phép nối chuỗi thay vì String.format
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>AutoCareerBridge - Mã Xác Nhận</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            background-color: #f4f4f4;" +
                "        }" +
                "        table {" +
                "            width: 100%;" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            background-color: #ffffff;" +
                "            border-radius: 8px;" +
                "            overflow: hidden;" +
                "        }" +
                "        .header {" +
                "            background-color: #0056b3;" +
                "            padding: 30px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .header h1 {" +
                "            font-family: 'Georgia', serif;" +
                "            font-size: 36px;" +
                "            font-style: italic;" +
                "            color: white;" +
                "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);" +
                "            font-weight: bold;" +
                "            letter-spacing: 2px;" +
                "        }" +
                "        .header h1:hover {" +
                "            transform: scale(1.1);" +
                "            color: #ffffff;" +
                "            text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.3);" +
                "        }" +
                "        .content {" +
                "            padding: 30px 20px;" +
                "        }" +
                "        .content h2 {" +
                "            color: #0056b3;" +
                "            font-size: 24px;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .code-box {" +
                "            background-color: #f0f8ff;" +
                "            border: 2px solid #0056b3;" +
                "            border-radius: 5px;" +
                "            padding: 15px;" +
                "            text-align: center;" +
                "            margin: 20px 0;" +
                "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        .code-box h3 {" +
                "            color: #0056b3;" +
                "            font-size: 28px;" +
                "            font-weight: bold;" +
                "            margin: 0;" +
                "        }" +
                "        .footer {" +
                "            background-color: #0056b3;" +
                "            color: #ffffff;" +
                "            padding: 15px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .footer p {" +
                "            margin: 0;" +
                "            font-size: 14px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <table>" +
                "        <tr>" +
                "            <td class=\"header\">" +
                "                <h1>AutoCareerBridge</h1>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"content\">" +
                "                <h2>Xin chào!</h2>" +
                "                <p>Cảm ơn bạn đã sử dụng AutoCareerBridge. Dưới đây là mã xác nhận của bạn:</p>" +
                "                <div class=\"code-box\">" +
                "                    <h3>" + verificationCode + "</h3>" +  // Nối chuỗi trực tiếp ở đây
                "                </div>" +
                "                <p>Vui lòng sử dụng mã này để hoàn tất quá trình xác thực tài khoản của bạn.</p>" +
                "                <p><strong>Chú ý:</strong> Mã này sẽ hết hạn sau 15 phút.</p>" +
                "                <p>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"footer\">" +
                "                <p>&copy; 2024 AutoCareerBridge. Tất cả các quyền được bảo lưu.</p>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";

        return htmlContent;
    }

    private String getForgotPassword(String verificationCode) {
        if (verificationCode == null || verificationCode.isEmpty()) {
            throw new IllegalArgumentException("Verification code cannot be null or empty");
        }

        // Dùng phép nối chuỗi thay vì String.format
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>AutoCareerBridge - Mã Xác Nhận</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            background-color: #f4f4f4;" +
                "        }" +
                "        table {" +
                "            width: 100%;" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            background-color: #ffffff;" +
                "            border-radius: 8px;" +
                "            overflow: hidden;" +
                "        }" +
                "        .header {" +
                "            background-color: #0056b3;" +
                "            padding: 30px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .header h1 {" +
                "            font-family: 'Georgia', serif;" +
                "            font-size: 36px;" +
                "            font-style: italic;" +
                "            color: white;" +
                "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);" +
                "            font-weight: bold;" +
                "            letter-spacing: 2px;" +
                "        }" +
                "        .header h1:hover {" +
                "            transform: scale(1.1);" +
                "            color: #ffffff;" +
                "            text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.3);" +
                "        }" +
                "        .content {" +
                "            padding: 30px 20px;" +
                "        }" +
                "        .content h2 {" +
                "            color: #0056b3;" +
                "            font-size: 24px;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .code-box {" +
                "            background-color: #f0f8ff;" +
                "            border: 2px solid #0056b3;" +
                "            border-radius: 5px;" +
                "            padding: 15px;" +
                "            text-align: center;" +
                "            margin: 20px 0;" +
                "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        .code-box h3 {" +
                "            color: #0056b3;" +
                "            font-size: 28px;" +
                "            font-weight: bold;" +
                "            margin: 0;" +
                "        }" +
                "        .footer {" +
                "            background-color: #0056b3;" +
                "            color: #ffffff;" +
                "            padding: 15px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .footer p {" +
                "            margin: 0;" +
                "            font-size: 14px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <table>" +
                "        <tr>" +
                "            <td class=\"header\">" +
                "                <h1>AutoCareerBridge</h1>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"content\">" +
                "                <h2>Xin chào!</h2>" +
                "                <p>Cảm ơn bạn đã sử dụng AutoCareerBridge. Dưới đây là mã xác nhận đổi mật khẩu của bạn:</p>" +
                "                <div class=\"code-box\">" +
                "                    <h3>" + verificationCode + "</h3>" +  // Nối chuỗi trực tiếp ở đây
                "                </div>" +
                "                <p>Vui lòng sử dụng mã này để hoàn tất quá trình đổi mật khẩu của bạn.</p>" +
                "                <p><strong>Chú ý:</strong> Mã này sẽ hết hạn sau 5 phút.</p>" +
                "                <p>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"footer\">" +
                "                <p>&copy; 2024 AutoCareerBridge. Tất cả các quyền được bảo lưu.</p>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";

        return htmlContent;
    }

    private String getAccount(String passwordAccount) {
        if (passwordAccount == null || passwordAccount.isEmpty()) {
            throw new IllegalArgumentException("Account cannot be null or empty");
        }

        // Dùng phép nối chuỗi thay vì String.format
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>AutoCareerBridge - Tài Khoản Của Bạn</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            background-color: #f4f4f4;" +
                "        }" +
                "        table {" +
                "            width: 100%;" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            background-color: #ffffff;" +
                "            border-radius: 8px;" +
                "            overflow: hidden;" +
                "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        .header {" +
                "            background-color: #0056b3;" +
                "            padding: 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .header h1 {" +
                "            color: white;" +
                "            font-size: 24px;" +
                "            margin: 0;" +
                "            text-transform: uppercase;" +
                "        }" +
                "        .content {" +
                "            padding: 20px;" +
                "            line-height: 1.6;" +
                "            color: #333333;" +
                "        }" +
                "        .content h2 {" +
                "            color: #0056b3;" +
                "            font-size: 20px;" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        .code-box {" +
                "            background-color: #f0f8ff;" +
                "            border: 1px solid #0056b3;" +
                "            padding: 15px;" +
                "            text-align: center;" +
                "            margin: 15px 0;" +
                "            font-size: 18px;" +
                "            font-weight: bold;" +
                "            color: #0056b3;" +
                "        }" +
                "        .footer {" +
                "            background-color: #0056b3;" +
                "            color: #ffffff;" +
                "            padding: 15px;" +
                "            text-align: center;" +
                "            font-size: 14px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <table>" +
                "        <tr>" +
                "            <td class=\"header\">" +
                "                <h1>Auto-Career Bridge</h1>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"content\">" +
                "                <h2>Chào bạn,</h2>" +
                "                <p>Chúng tôi rất vui thông báo rằng tài khoản của bạn trên hệ thống <strong>AutoCareerBridge</strong> đã được tạo thành công.</p>" +
                "                <p>Dưới đây là thông tin đăng nhập của bạn:</p>" +
                "                <p><strong>Tên đăng nhập:</strong> [Tài khoản email này của bạn]</p>" +  // Thay bằng thông tin thực
                "                <p><strong>Mật khẩu:</strong></p>" +
                "                <div class=\"code-box\">" +
                "                    " + passwordAccount +  // Mật khẩu
                "                </div>" +
                "                <p><strong>Chú ý:</strong> Hãy đăng nhập và thay đổi mật khẩu của bạn ngay để đảm bảo tính bảo mật.</p>" +
                "                <p>Nếu bạn không yêu cầu tài khoản này, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi ngay lập tức.</p>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"footer\">" +
                "                <p>&copy; 2024 AutoCareerBridge. Tất cả các quyền được bảo lưu.</p>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";

        return htmlContent;
    }

    private String getNewPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Verification code cannot be null or empty");
        }

        // Dùng phép nối chuỗi thay vì String.format
        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>AutoCareerBridge - Mã Xác Nhận</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: Arial, sans-serif;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            background-color: #f4f4f4;" +
                "        }" +
                "        table {" +
                "            width: 100%;" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            background-color: #ffffff;" +
                "            border-radius: 8px;" +
                "            overflow: hidden;" +
                "        }" +
                "        .header {" +
                "            background-color: #0056b3;" +
                "            padding: 30px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .header h1 {" +
                "            font-family: 'Georgia', serif;" +
                "            font-size: 36px;" +
                "            font-style: italic;" +
                "            color: white;" +
                "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);" +
                "            font-weight: bold;" +
                "            letter-spacing: 2px;" +
                "        }" +
                "        .header h1:hover {" +
                "            transform: scale(1.1);" +
                "            color: #ffffff;" +
                "            text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.3);" +
                "        }" +
                "        .content {" +
                "            padding: 30px 20px;" +
                "        }" +
                "        .content h2 {" +
                "            color: #0056b3;" +
                "            font-size: 24px;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .code-box {" +
                "            background-color: #f0f8ff;" +
                "            border: 2px solid #0056b3;" +
                "            border-radius: 5px;" +
                "            padding: 15px;" +
                "            text-align: center;" +
                "            margin: 20px 0;" +
                "            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);" +
                "        }" +
                "        .code-box h3 {" +
                "            color: #0056b3;" +
                "            font-size: 28px;" +
                "            font-weight: bold;" +
                "            margin: 0;" +
                "        }" +
                "        .footer {" +
                "            background-color: #0056b3;" +
                "            color: #ffffff;" +
                "            padding: 15px 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .footer p {" +
                "            margin: 0;" +
                "            font-size: 14px;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <table>" +
                "        <tr>" +
                "            <td class=\"header\">" +
                "                <h1>AutoCareerBridge</h1>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"content\">" +
                "                <h2>Xin chào!</h2>" +
                "                <p>Cảm ơn bạn đã sử dụng AutoCareerBridge. Dưới đây là mật khẩu mới của bạn:</p>" +
                "                <div class=\"code-box\">" +
                "                    <h3>" + newPassword + "</h3>" +  // Nối chuỗi trực tiếp ở đây
                "                </div>" +
                "                <p>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>" +
                "            </td>" +
                "        </tr>" +
                "        <tr>" +
                "            <td class=\"footer\">" +
                "                <p>&copy; 2024 AutoCareerBridge. Tất cả các quyền được bảo lưu.</p>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";

        return htmlContent;
    }


    private String getEmailBody(Email email, String namePartner) {
        String contactPerson = "AutoCareerBridge";
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = "Hà Nội, "+ localDateTime.format(formatter);
        return String.format(
                "<!DOCTYPE html>" +
                        "<html lang=\"vi\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>Chấp nhận hợp tác - AutoCareerBridge</title>" +
                        "    <style>" +
                        "        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');" +
                        "        .logo-text {" +
                        "            font-family: 'Georgia', serif;" +
                        "            font-size: 36px;" +
                        "            font-style: italic;" +
                        "            color: white;" +
                        "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);" +
                        "            font-weight: bold;" +
                        "            letter-spacing: 2px;" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body style=\"margin: 0; padding: 0; font-family: 'Roboto', Arial, sans-serif; background-color: #f7f7f7; color: #333333;\">" +
                        "    <table role=\"presentation\" style=\"width: 100%%; border-collapse: collapse;\">" +
                        "        <tr>" +
                        "            <td align=\"center\" style=\"padding: 0;\">" +
                        "                <table role=\"presentation\" style=\"width: 600px; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);\">" +
                        "                    <!-- Header -->" +
                        "                    <tr>" +
                        "                        <td align=\"center\" style=\"padding: 40px 0; background-color: #005f91;\">" +
                        "                            <span class=\"logo-text\">AutoCareerBridge</span>" +
                        "                        </td>" +
                        "                    </tr>" +
                        "                    <!-- Main Content -->" +
                        "                    <tr>" +
                        "                        <td style=\"padding: 40px 30px;\">" +
                        "                            <h1 style=\"font-size: 24px; margin: 0 0 20px 0; color: #005f91;\">Chấp nhận hợp tác</h1>" +
                        "                            <p style=\"margin: 0 0 15px 0; font-size: 16px; line-height: 1.5;\">Kính gửi %s,</p>" +
                        "                            <p style=\"margin: 0 0 15px 0; font-size: 16px; line-height: 1.5;\">Chúng tôi vui mừng thông báo rằng AutoCareerBridge đã chấp nhận đề xuất hợp tác của %s trong việc kết nối sinh viên tài năng với các cơ hội việc làm.</p>" +
                        "                            <p style=\"margin: 0 0 15px 0; font-size: 16px; line-height: 1.5;\">Thời gian gửi: %s</p>" +
                        "                            <p style=\"margin: 0 0 15px 0; font-size: 16px; line-height: 1.5;\">Chúng tôi rất mong được hợp tác để xây dựng cầu nối vững chắc giữa hai bên tiềm năng!</p>" +
                        "                            <p style=\"margin: 0; font-size: 16px; line-height: 1.5; font-style: italic; text-align: center; color: #005f91;\">\"Kết nối tài năng, xây dựng tương lai\"</p>" +
                        "                        </td>" +
                        "                    </tr>" +
                        "                    <!-- Footer -->" +
                        "                    <tr>" +
                        "                        <td style=\"padding: 30px; background-color: #005f91; color: #ffffff;\">" +
                        "                            <table role=\"presentation\" style=\"width: 100%%; border-collapse: collapse; font-size: 14px;\">" +
                        "                                <tr>" +
                        "                                    <td style=\"padding: 0; width: 50%%;\">" +
                        "                                        <p style=\"margin: 0; font-size: 14px;\">" +
                        "                                            &copy; AutoCareerBridge, 2024<br>" +
                        "                                            Email: contact@autocareerbridge.com<br>" +
                        "                                            Điện thoại: (84) 123-456-789" +
                        "                                        </p>" +
                        "                                    </td>" +
                        "                                    <td style=\"padding: 0; width: 50%%;\" align=\"right\">" +
                        "                                        <a href=\"https://www.linkedin.com\" style=\"color: #ffffff; text-decoration: none;\"><i class=\"fab fa-linkedin\" style=\"font-size: 20px;\"></i></a>" +
                        "                                        <a href=\"https://twitter.com\" style=\"color: #ffffff; text-decoration: none;\"><i class=\"fab fa-twitter\" style=\"font-size: 20px;\"></i></a>" +
                        "                                        <a href=\"https://facebook.com\" style=\"color: #ffffff; text-decoration: none;\"><i class=\"fab fa-facebook\" style=\"font-size: 20px;\"></i></a>" +
                        "                                    </td>" +
                        "                                </tr>" +
                        "                            </table>" +
                        "                        </td>" +
                        "                    </tr>" +
                        "                </table>" +
                        "            </td>" +
                        "        </tr>" +
                        "    </table>" +
                        "</body>" +
                        "</html>",
                contactPerson, namePartner, formattedDateTime
        );
    }


}
