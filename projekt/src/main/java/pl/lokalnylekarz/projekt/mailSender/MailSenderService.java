package pl.lokalnylekarz.projekt.mailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMailToUs(MailDto mailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lokalnylekarz@gmail.com");
        message.setTo("lokalnylekarz@gmail.com");
        message.setSubject(mailDto.getSubject());
        message.setText("Email from: " + mailDto.getFromEmail() + "\n\n" + mailDto.getText());

        mailSender.send(message);
    }
}
