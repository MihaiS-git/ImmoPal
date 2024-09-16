package ubb.graduation24.immopal.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${notification.host_name}")
    private String hostName;

    @Value("${notification.host_port}")
    private String hostPort;

    public void sendEmail(String subject, String to,  String body, boolean isHtml) throws MessagingException {
        log.info("EmailService sending email to " + hostName + ":" + hostPort + ":" + subject);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostName);
        mailSender.setPort(Integer.parseInt(hostPort));
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setFrom("office@immopal.com");
        helper.setTo(to);
        helper.setText(body, isHtml);

        mailSender.send(message);
        log.info("EmailService email sent to " + hostName + ":" + hostPort + ":" + subject);
    }
}
