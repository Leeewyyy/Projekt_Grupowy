package pl.lokalnylekarz.projekt.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lokalnylekarz.projekt.api.Endpoint;
import pl.lokalnylekarz.projekt.dataTransferObjects.UsersStatistics;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityService;
import pl.lokalnylekarz.projekt.medicalFacility.MedicalFacilityStatisticsDTO;
import pl.lokalnylekarz.projekt.model.SearchDetails;
import pl.lokalnylekarz.projekt.services.SearchStatisticsService;
import pl.lokalnylekarz.projekt.user.UserService;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STATISTICS)
public class StatisticsController {
    private final SearchStatisticsService searchStatisticsService;
    private final UserService userService;
    private final MedicalFacilityService medicalFacilityService;

    @Autowired
    public StatisticsController(
            SearchStatisticsService searchStatisticsService,
            UserService userService,
            MedicalFacilityService medicalFacilityService
    ) {
        this.searchStatisticsService = searchStatisticsService;
        this.userService = userService;
        this.medicalFacilityService = medicalFacilityService;
    }

    @GetMapping("/search/details")
    public List<SearchDetails> getAllSearchDetails() {
        return searchStatisticsService.getAllSearchDetails();
    }

    @GetMapping("/search")
    public ResponseEntity<SearchDetails> getGlobalSearch() {
        return new ResponseEntity<>(searchStatisticsService.getGlobalSearch(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<UsersStatistics> getUsersAmount() {
        return new ResponseEntity<>(new UsersStatistics(
                userService.getAll().size(),
                userService.getAllVerified().size()
        ), HttpStatus.OK);
    }

    @GetMapping("/medical-facilities")
    public ResponseEntity<List<MedicalFacilityStatisticsDTO>> getMedicalFacilitiesStatistics() {
        return new ResponseEntity<>(medicalFacilityService.getAllForStatistics(), HttpStatus.OK);
    }
}
