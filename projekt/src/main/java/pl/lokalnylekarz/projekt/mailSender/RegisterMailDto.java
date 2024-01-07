package pl.lokalnylekarz.projekt.mailSender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.lokalnylekarz.projekt.model.User;

@Setter
@Getter
@AllArgsConstructor
public class RegisterMailDto {
    private User user;
    private String userVerificationLink;
}
