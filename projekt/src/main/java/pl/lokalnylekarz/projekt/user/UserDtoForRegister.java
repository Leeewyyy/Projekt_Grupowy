package pl.lokalnylekarz.projekt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoForRegister {
    private String fullName;
    private String email;
    private String password;
    private String role;
}