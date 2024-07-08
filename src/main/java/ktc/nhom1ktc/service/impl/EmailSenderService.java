package ktc.nhom1ktc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendMail(String toMail, String subject, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ithuongngoai@gmail.com");
        mailMessage.setTo(toMail);
        mailMessage.setText(body);
        mailMessage.setSubject(subject);

        mailSender.send(mailMessage);
        System.out.println("Đã gửi email thành công đến " + toMail);
    }

    public void sendAccountCreationEmail(String toMail) {
        String subject = "Tài khoản của bạn đã được tạo thành công";
        String body = "Xin chào,\n\n"
                + "Cảm ơn bạn đã đăng ký tài khoản với chúng tôi.\n\n"
                + "Trân trọng,\n"
                + "Đội ngũ hỗ trợ khách hàng";

        sendMail(toMail, subject, body);
    }
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String text = "To reset your password, click the link below:\n"
                + "http://localhost:8080/api/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
