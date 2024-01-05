package pl.lokalnylekarz.projekt.dataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersStatistics {
    private int registeredUsers;
    private int verifiedUsers;
}
