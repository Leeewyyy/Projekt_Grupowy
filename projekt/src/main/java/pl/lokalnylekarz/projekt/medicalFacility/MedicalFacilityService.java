package pl.lokalnylekarz.projekt.medicalFacility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalFacilityService {



    private final MedicalFacilityRepository medicalFacilityRepository;
    public List<MedicalFacility> getAll(){
        return (List<MedicalFacility>) medicalFacilityRepository.findAll();
    }
}
