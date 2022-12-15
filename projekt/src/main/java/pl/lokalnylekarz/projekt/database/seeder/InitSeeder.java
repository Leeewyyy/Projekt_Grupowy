package pl.lokalnylekarz.projekt.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lokalnylekarz.projekt.enumeration.FacilityRatings;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.NfzStatuses;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.model.Specialist;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.OpinionsRepository;
import pl.lokalnylekarz.projekt.repository.SpecialistRepository;
import pl.lokalnylekarz.projekt.repository.UserRepository;


import java.sql.Timestamp;

@Component
public class InitSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalFacilityRepository medicalFacilityRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private OpinionsRepository opinionsRepository;

    @Override
    public void run(String... args) throws Exception {
        this.loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = new User("john1", "John1", "Smith", "john.smitch@gmail.com", "cGFzc3dvcmQ=");
            User user2 = new User("john2", "John2", "Smith2", "john2.smitch@gmail.com", "cGFzc3dvcmQ=");
            User user3 = new User("john3", "John3", "Smith3", "john3.smitch@gmail.com", "cGFzc3dvcmQ=");
            User user4 = new User("john4", "John4", "Smith4", "john4.smitch@gmail.com", "cGFzc3dvcmQ=");

            MedicalFacility medicalFacility1 = new MedicalFacility("LuxMed1", MedicalFacilityTypes.ONE, "123123123", "med.pl", "Lorem ipsum dolor sit amed", NfzStatuses.TRUE, new Timestamp(12), new Timestamp(12345), user1);
            MedicalFacility medicalFacility2 = new MedicalFacility("LuxMed2", MedicalFacilityTypes.TWO, "123123123", "med.pl", "Lorem ipsum dolor sit amed", NfzStatuses.TRUE, new Timestamp(12), new Timestamp(12345), user2);
            MedicalFacility medicalFacility3 = new MedicalFacility("LuxMed3", MedicalFacilityTypes.THREE, "123123123", "med.pl", "Lorem ipsum dolor sit amed", NfzStatuses.TRUE, new Timestamp(12), new Timestamp(12345), user3);
            MedicalFacility medicalFacility4 = new MedicalFacility("LuxMed4", MedicalFacilityTypes.FOUR, "123123123", "med.pl", "Lorem ipsum dolor sit amed", NfzStatuses.TRUE, new Timestamp(12), new Timestamp(12345), user4);

            Specialist specialist1 = new Specialist("Jane", "Doe", Specialization.ONE, medicalFacility1);
            Specialist specialist2 = new Specialist("Jane2", "Doe", Specialization.TWO, medicalFacility2);
            Specialist specialist3 = new Specialist("Jane3", "Doe", Specialization.THREE, medicalFacility3);
            Specialist specialist4 = new Specialist("Jane4", "Doe", Specialization.FOUR, medicalFacility4);

            Opinion opinion1 = new Opinion(user1, FacilityRatings.ONE, "Lorem ipsume dolor sit amend", medicalFacility1);
            Opinion opinion2 = new Opinion(user2, FacilityRatings.TWO, "Lorem ipsume dolor sit amend", medicalFacility2);
            Opinion opinion3 = new Opinion(user3, FacilityRatings.THREE, "Lorem ipsume dolor sit amend", medicalFacility3);
            Opinion opinion4 = new Opinion(user4, FacilityRatings.FOUR, "Lorem ipsume dolor sit amend", medicalFacility4);

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            medicalFacilityRepository.save(medicalFacility1);
            medicalFacilityRepository.save(medicalFacility2);
            medicalFacilityRepository.save(medicalFacility3);
            medicalFacilityRepository.save(medicalFacility4);

            specialistRepository.save(specialist1);
            specialistRepository.save(specialist2);
            specialistRepository.save(specialist3);
            specialistRepository.save(specialist4);

            opinionsRepository.save(opinion1);
            opinionsRepository.save(opinion2);
            opinionsRepository.save(opinion3);
            opinionsRepository.save(opinion4);
        }
    }
}
