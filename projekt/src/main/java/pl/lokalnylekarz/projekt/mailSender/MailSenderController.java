package pl.lokalnylekarz.projekt.mailSender;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lokalnylekarz.projekt.api.Endpoint;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.SEND_MESSAGE)
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @PostMapping()
    public void sendMail(MailDto mailDto) {
        mailSenderService.sendMailToUs(mailDto);
    }
}
