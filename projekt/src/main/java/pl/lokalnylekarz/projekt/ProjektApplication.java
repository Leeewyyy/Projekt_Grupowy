package pl.lokalnylekarz.projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.lokalnylekarz.projekt.user.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class ProjektApplication {


    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get(UserService.UPLOAD_DIRECTORY));

        SpringApplication.run(ProjektApplication.class, args);
    }

}
