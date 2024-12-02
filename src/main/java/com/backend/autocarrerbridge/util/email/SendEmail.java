package com.backend.autocarrerbridge.util.email;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EMAIL_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EMAIL_REQUIRED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NO_CONTENT;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SendEmail {

    private final JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

    public void sendCode(EmailDTO emailDTO, String verifyCode) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(emailDTO, getVerifyCode(verifyCode));
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendNewPassword(EmailDTO emailDTO, String newPassword) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(emailDTO, getNewPassword(newPassword));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendForgot(EmailDTO emailDTO, String verifyCode) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(emailDTO, getForgotPassword(verifyCode));
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendAccount(EmailDTO emailDTO, String password) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);
            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(emailDTO, getAccount(password));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendAccountStatusNotification(EmailDTO emailDTO, State status) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            String emailContent = getAccountApprovalNotification(status);
            MimeMessage mimeMessage = createMimeMessage(emailDTO, emailContent);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendApprovedJobNotification(EmailDTO emailDTO, String titleJob) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            String emailContent = getJobApprovalNotification(titleJob);
            MimeMessage mimeMessage = createMimeMessage(emailDTO, emailContent);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendRRejectedJobNotification(EmailDTO emailDTO, String titleJob, String message) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            String emailContent = getJobRejectedNotification(titleJob, message);
            MimeMessage mimeMessage = createMimeMessage(emailDTO, emailContent);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendApprovedWorkshopNotification(EmailDTO emailDTO, String titleWorkshop) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            String emailContent = getWorkshopApprovalNotification(titleWorkshop);
            MimeMessage mimeMessage = createMimeMessage(emailDTO, emailContent);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    public void sendRRejectedWorkshopNotification(EmailDTO emailDTO, String titleWorkshop, String message) {
        try {
            // Kiểm tra email hợp lệ
            validatedEmail(emailDTO);

            // Tạo và gửi email
            String emailContent = getWorkshopRejectedNotification(titleWorkshop, message);
            MimeMessage mimeMessage = createMimeMessage(emailDTO, emailContent);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(ERROR_EMAIL_NOT_FOUND.getMessage(), e.getMessage(), e);
        }
    }

    private MimeMessage createMimeMessage(EmailDTO emailDTO, String emailBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("quocta.gov@gmail.com");
        helper.setTo(emailDTO.getEmail().trim());
        helper.setSubject(emailDTO.getSubject());
        helper.setText(emailBody, true); // Cho phép nội dung HTML
        return mimeMessage;
    }

    private String getVerifyCode(String verificationCode) {
        if (verificationCode == null || verificationCode.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }

        return buildHtmlTemplate(
                "Cảm ơn bạn đã sử dụng AutoCareerBridge. Dưới đây là mã xác nhận của bạn:",
                verificationCode,
                "Vui lòng sử dụng mã này để hoàn tất quá trình xác thực tài khoản của bạn.",
                "Mã này sẽ hết hạn sau 15 phút.");
    }

    private String getForgotPassword(String verificationCode) {
        if (verificationCode == null || verificationCode.isEmpty()) {
            throw new AppException(ERROR_NO_CONTENT);
        }

        return buildHtmlTemplate(
                "Cảm ơn bạn đã sử dụng AutoCareerBridge. Dưới đây là mã xác nhận đổi mật khẩu của bạn:",
                verificationCode,
                "Vui lòng sử dụng mã này để hoàn tất quá trình đổi mật khẩu của bạn.",
                "Mã này sẽ hết hạn sau 5 phút.");
    }

    private String buildHtmlTemplate(
            String message,
            String verificationCode,
            String footerMessage,
            String expiryMessage
    ) {
        String content =
                        "            <td class=\"content\">" +
                        "                <h2>" + "Xin chào!" + "</h2>" +
                        "                <p>" + message + "</p>" +
                        "                <div class=\"code-box\">" +
                        "                    <h3>" + verificationCode + "</h3>" +
                        "                </div>" +
                        "                <p>" + footerMessage + "</p>" +
                        "                <p><strong>Chú ý:</strong> " + expiryMessage + "</p>" +
                        "            </td>";
        return getEmailBody(content);
    }

    private String getAccount(String passwordAccount) {
        if (passwordAccount == null || passwordAccount.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_CONTENT.getMessage());
        }
        String content =
                        "            <td class=\"content\">" +
                        "                <h2>Chào bạn,</h2>" +
                        "                <p>Chúng tôi rất vui thông báo rằng tài khoản của bạn trên hệ thống <strong>AutoCareerBridge</strong> đã được tạo thành công.</p>" +
                        "                <p>Dưới đây là thông tin đăng nhập của bạn:</p>" +
                        "                <p><strong>Tên đăng nhập:</strong> [Tài khoản email này của bạn]</p>" +
                        "                <p><strong>Mật khẩu:</strong></p>" +
                        "                <div class=\"code-box\">" +
                        "                    " + passwordAccount +
                        "                </div>" +
                        "                <p><strong>Chú ý:</strong> Hãy đăng nhập và thay đổi mật khẩu của bạn ngay để đảm bảo tính bảo mật.</p>" +
                        "                <p>Nếu bạn không yêu cầu tài khoản này, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi ngay lập tức.</p>" +
                        "            </td>";

        return getEmailBody(content);
    }

    private String getNewPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_CONTENT.getMessage());
        }

        String content =
                "            <td class=\"content\">" +
                "                <h2>Xin chào!</h2>" +
                "                <p>Cảm ơn bạn đã sử dụng AutoCareerBridge. Dưới đây là mật khẩu mới của bạn:</p>" +
                "                <div class=\"code-box\">" +
                "                    <h3>" + newPassword + "</h3>" +
                "                </div>" +
                "                <p>Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.</p>" +
                "            </td>";
    return getEmailBody(content);
    }

    private String getAccountApprovalNotification(State state) {
        String title = "";
        String message = "";
        if (state == State.APPROVED) {
            title = "Tài khoản của bạn đã được chấp nhận"; // 'Chấp nhận' hoặc 'Từ chối'
            message = "Chúng tôi vui mừng thông báo tài khoản của bạn đã được chấp nhận. "
                    + "Bạn đã có thể đăng nhập vào hệ thống của chúng tôi.";
        }
        if (state == State.REJECTED) {
            title = "Tài khoản của bạn đã được bị từ chối"; // 'Chấp nhận' hoặc 'Từ chối'
            message = "Rất tiếc, tài khoản của bạn đã bị từ chối. " + "Chúng tôi nghi ngờ thông tin đăng ký của bạn. "
                    + "Nếu muốn tiếp tục hãy kiểm tra lại thông tin và đăng ký lại tài khoản khác. "
                    + "Hoặc liên hệ với chúng tôi để được hướng dẫn.";
        }
        String content =
                        "            <td class=\"content\">" +
                        "                <h2>" + title + "</h2>" +
                        "                <p>" + message + "</p>" +
                        "                <p>Chúng tôi sẽ liên lạc với bạn nếu có bất kỳ cập nhật nào tiếp theo.</p>" +
                        "                <p>Nếu bạn có thắc mắc, vui lòng liên hệ với chúng tôi.</p>" +
                        "            </td>";

        return getEmailBody(content);
    }

    private String getJobApprovalNotification(String titleJob) {
        String content =
                        "            <td class=\"content\">" +
                        "                <h2>Tin tuyển dụng " + titleJob + " của bạn đã được chấp nhận</h2>" +
                        "                <p>Chúng tôi vui mừng thông báo tin tuyển dụng của bạn đã được chấp nhận. " +
                        "                Bạn đã có thể theo dõi tuyển dụng của bạn trong hệ thống của chúng tôi.</p>" +
                        "                <p>Chúng tôi sẽ liên lạc với bạn nếu có bất kỳ cập nhật nào tiếp theo.</p>" +
                        "                <p>Nếu bạn có thắc mắc, vui lòng liên hệ với chúng tôi.</p>" +
                        "            </td>";

        return getEmailBody(content);
    }
    private String getJobRejectedNotification(String titleJob, String message) {
        String content =
                        "            <td class=\"content\">" +
                        "                <h2>Tin tuyển dụng " + titleJob + " của bạn đã được chấp nhận</h2>" +
                        "                <p>Rất tiếc, tài khoản của bạn đã bị từ chối. Chúng tôi nghi ngờ thông tin đăng ký của bạn." +
                        "                <p>Lý do từ chối của chúng tôi: "+message+"</p>" +
                        "                <p>Nếu muốn tiếp tục hãy kiểm tra lại thông tin và đăng ký lại tin tuyển dụng khác." +
                        "                Hoặc liên hệ với chúng tôi để được hướng dẫn.<p>" +
                        "                <p>Chúng tôi sẽ liên lạc với bạn nếu có bất kỳ cập nhật nào tiếp theo.</p>" +
                        "            </td>";

        return getEmailBody(content);
    }

    private String getWorkshopApprovalNotification(String titleWorkshop) {
        String content =
                "            <td class=\"content\">" +
                        "                <h2>Workshop " + titleWorkshop + " của bạn đã được chấp nhận</h2>" +
                        "                <p>Chúng tôi vui mừng thông báo workshop của bạn đã được chấp nhận. " +
                        "                Bạn đã có thể theo dõi workshop của bạn trong hệ thống của chúng tôi.</p>" +
                        "                <p>Chúng tôi sẽ liên lạc với bạn nếu có bất kỳ cập nhật nào tiếp theo.</p>" +
                        "                <p>Nếu bạn có thắc mắc, vui lòng liên hệ với chúng tôi.</p>" +
                        "            </td>";

        return getEmailBody(content);
    }
    private String getWorkshopRejectedNotification(String titleWorkshop, String message) {
        String content =
                "            <td class=\"content\">" +
                        "                <h2>Workshop " + titleWorkshop + " của bạn đã được chấp nhận</h2>" +
                        "                <p>Rất tiếc, tài khoản của bạn đã bị từ chối. Chúng tôi nghi ngờ thông tin đăng ký của bạn." +
                        "                <p>Lý do từ chối của chúng tôi: "+message+"</p>" +
                        "                <p>Nếu muốn tiếp tục hãy kiểm tra lại thông tin và đăng ký lại tin workshop khác." +
                        "                Hoặc liên hệ với chúng tôi để được hướng dẫn.<p>" +
                        "                <p>Chúng tôi sẽ liên lạc với bạn nếu có bất kỳ cập nhật nào tiếp theo.</p>" +
                        "            </td>";

        return getEmailBody(content);
    }


    private String getEmailBody(String content) {
        return "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title></title>" +
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
                "        .footer {" +
                "            background-color: #0056b3;" +
                "            color: #ffffff;" +
                "            padding: 15px;" +
                "            text-align: center;" +
                "            font-size: 14px;" +
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
                "                <h1>Thông báo từ Auto-Career Bridge</h1>" +
                "            </td>" +
                "        </tr>" +
                content +
                "        <tr>" +
                "            <td class=\"footer\">" +
                "                <p>&copy; 2024 AutoCareerBridge. Tất cả các quyền được bảo lưu.</p>" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</body>" +
                "</html>";
    }

    public void validatedEmail(EmailDTO emailDTO) {
        if (emailDTO == null
                || emailDTO.getEmail() == null
                || emailDTO.getEmail().trim().isEmpty()) {
            throw new AppException(ERROR_EMAIL_REQUIRED);
        }
    }
}
