package pl.lokalnylekarz.projekt.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.dataTypes.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageSave {
    public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/upload/images";

    public String saveImage(MultipartFile file) throws IOException {
        String filename = this.buildFilename(file);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, filename);

        Files.write(fileNameAndPath, file.getBytes());

        return ServerInfo.getBaseUrl() + Endpoint.RESOURCES + "/" + filename;
    }

    public List<Image> saveImages(List<MultipartFile> imagesToSave) {
        return imagesToSave.stream()
                .map(image -> {
                    try {
                        String url = this.saveImage(image);

                        return new Image(
                                UUID.randomUUID().toString(),
                                url
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    protected String buildFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        String newFilename = originalFilename.replace("." + fileExtension, "--" + System.currentTimeMillis() + "." + fileExtension);

        return newFilename;
    }
}
