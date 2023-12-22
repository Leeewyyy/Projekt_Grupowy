package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MedicalFacilityForRegisterDto {
    private String name;
    private String type;
    private String address;
    private String phone;
    private String websiteUrl;
    private String description;
    private String nfzStatus;
    private LocalTime openFrom;
    private LocalTime openTo;
    private Double lat;
    private Double lon;
    private Long addedBy;
    private MultipartFile image;
    private List<MultipartFile> additionalImages;
}
