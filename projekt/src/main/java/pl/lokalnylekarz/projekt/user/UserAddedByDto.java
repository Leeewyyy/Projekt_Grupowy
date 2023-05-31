package pl.lokalnylekarz.projekt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class UserAddedByDto {
    private Long id;
    private String fullName;
    private String email;
    private String imageUrl;
    private Timestamp registrationDate;
}
