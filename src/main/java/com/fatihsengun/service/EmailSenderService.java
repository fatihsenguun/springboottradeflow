package com.fatihsengun.service;

import com.fatihsengun.entity.User;
import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import com.fatihsengun.kafka.OrderEventModel;
import com.fatihsengun.repository.AuthRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }


    public void sendOrderCreateMail(OrderEventModel event) {
        User user = authRepository.findById(event.getUserId()).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "userId: " + event.getUserId())));

        String subject = "Your Order Has Been Received! \uD83D\uDCE6 ";
        String body = String.format(
                "Dear %s %s,\n\nYour order with a total amount of %.2f TL has been successfully received.\nOrder Number: %s\n\nThank you for choosing us!",
                user.getFirstName(),
                user.getLastName(),
                event.getTotalAmount(),
                event.getOrderId()
        );
        sendEmail(user.getEmail(), subject, body);
        log.info("✅ Order confirmation email sent: {}");

    }

}
