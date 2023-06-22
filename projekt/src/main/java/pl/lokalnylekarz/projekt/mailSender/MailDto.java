package pl.lokalnylekarz.projekt.mailSender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String fromEmail;
    private String subject;
    private String text;
}
