package pl.lokalnylekarz.projekt.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.services.ServerInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoint.RESOURCES)
public class UploadController {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/upload/images";

    @GetMapping("/{imageName}")
    public ResponseEntity<InputStreamResource> image(@PathVariable String imageName) throws IOException {
        FileInputStream in = new FileInputStream(UPLOAD_DIRECTORY + "/" + imageName);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(in));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = this.saveImage(file);
            if (filename.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(ServerInfo.getBaseUrl() + Endpoint.RESOURCES + "/" + filename, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected String saveImage(MultipartFile file) throws IOException {
        String filename = this.buildFilename(file);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, filename);

        Files.write(fileNameAndPath, file.getBytes());

        return filename;
    }

    protected String buildFilename(MultipartFile file) {
        return String.format("%s", RandomStringUtils.randomAlphanumeric(8)) +
                file.getOriginalFilename();
    }
}
