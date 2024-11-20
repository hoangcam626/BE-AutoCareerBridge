package com.backend.autocarrerbridge.emailconfig;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.UniversityService;
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

    public void sendEmail(Email email,String partner) {
        try {
            // Kiểm tra email hợp lệ
            if (email == null || email.getEmail() == null || email.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email người nhận không được để trống");
            }

            // Tạo và gửi email
            MimeMessage mimeMessage = createMimeMessage(email, getEmailBody(email,partner));
            mailSender.send(mimeMessage);
            logger.info("Email đã được gửi tới: {}", email.getEmail().trim());
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

    private String getEmailBody(Email email, String namePartner) {
        String contactPerson = "AutoCareerBridge";
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
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
                        "            color: red;" +
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
