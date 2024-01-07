package pl.lokalnylekarz.projekt.mailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendMailToUs(MailDto mailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lokalnylekarz@gmail.com");
        message.setTo("lokalnylekarz@gmail.com");
        message.setSubject(mailDto.getSubject());
        message.setText("Email from: " + mailDto.getFromEmail() + "\n\n" + mailDto.getText());

        mailSender.send(message);
    }

    public void sendToUser(RegisterMailDto mail) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("user", mail.getUser());
        thymeleafContext.setVariable("verificationLink", mail.getUserVerificationLink());

        String emailContent = templateEngine.process("userRegisterTemplate", thymeleafContext);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("lokalnylekarz@gmail.com");
            helper.setTo(mail.getUser().getEmail());
            helper.setSubject("Rejestracja w serwisie LokalnyLekarz");
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
