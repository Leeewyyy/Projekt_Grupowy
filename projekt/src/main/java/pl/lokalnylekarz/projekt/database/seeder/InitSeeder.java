package pl.lokalnylekarz.projekt.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lokalnylekarz.projekt.enumeration.FacilityRatings;
import pl.lokalnylekarz.projekt.enumeration.MedicalFacilityTypes;
import pl.lokalnylekarz.projekt.enumeration.Specialization;
import pl.lokalnylekarz.projekt.model.MedicalFacility;
import pl.lokalnylekarz.projekt.model.Opinion;
import pl.lokalnylekarz.projekt.model.Specialist;
import pl.lokalnylekarz.projekt.model.User;
import pl.lokalnylekarz.projekt.repository.MedicalFacilityRepository;
import pl.lokalnylekarz.projekt.repository.OpinionRepository;
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
    private OpinionRepository opinionRepository;

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

            MedicalFacility medicalFacility1 = new MedicalFacility();
            medicalFacility1.setName("Politechnika Gdanska");
            medicalFacility1.setType(MedicalFacilityTypes.ONE);
            medicalFacility1.setAddress("ul. Politechniczna");
            medicalFacility1.setImageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479");
            medicalFacility1.setPhone("+48123123123");
            medicalFacility1.setWebsiteUrl("https://pg.edu.pl");
            medicalFacility1.setDescription("Fajna placowka");
            medicalFacility1.setIsNFZ(true);
            medicalFacility1.setRating(3);
            medicalFacility1.setOpenFrom(new Timestamp(12));
            medicalFacility1.setOpenTo(new Timestamp(12345));
            medicalFacility1.setLat(54.3739);
            medicalFacility1.setLng(18.6214);

            MedicalFacility medicalFacility2 = new MedicalFacility();
            medicalFacility2.setName("Politechnika Gdanska");
            medicalFacility2.setType(MedicalFacilityTypes.ONE);
            medicalFacility2.setAddress("ul. Politechniczna");
            medicalFacility2.setImageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479");
            medicalFacility2.setPhone("+48123123123");
            medicalFacility2.setWebsiteUrl("https://pg.edu.pl");
            medicalFacility2.setDescription("Fajna placowka");
            medicalFacility2.setIsNFZ(true);
            medicalFacility2.setRating(3);
            medicalFacility2.setOpenFrom(new Timestamp(12));
            medicalFacility2.setOpenTo(new Timestamp(12345));
            medicalFacility2.setLat(54.3739);
            medicalFacility2.setLng(18.6214);

            MedicalFacility medicalFacility3 = new MedicalFacility();
            medicalFacility3.setName("Politechnika Gdanska");
            medicalFacility3.setType(MedicalFacilityTypes.ONE);
            medicalFacility3.setAddress("ul. Politechniczna");
            medicalFacility3.setImageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479");
            medicalFacility3.setPhone("+48123123123");
            medicalFacility3.setWebsiteUrl("https://pg.edu.pl");
            medicalFacility3.setDescription("Fajna placowka");
            medicalFacility3.setIsNFZ(true);
            medicalFacility3.setRating(3);
            medicalFacility3.setOpenFrom(new Timestamp(12));
            medicalFacility3.setOpenTo(new Timestamp(12345));
            medicalFacility3.setLat(54.3739);
            medicalFacility3.setLng(18.6214);

            MedicalFacility medicalFacility4 = new MedicalFacility();
            medicalFacility4.setName("Politechnika Gdanska");
            medicalFacility4.setType(MedicalFacilityTypes.ONE);
            medicalFacility4.setAddress("ul. Politechniczna");
            medicalFacility4.setImageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479");
            medicalFacility4.setPhone("+48123123123");
            medicalFacility4.setWebsiteUrl("https://pg.edu.pl");
            medicalFacility4.setDescription("Fajna placowka");
            medicalFacility4.setIsNFZ(true);
            medicalFacility4.setRating(3);
            medicalFacility4.setOpenFrom(new Timestamp(12));
            medicalFacility4.setOpenTo(new Timestamp(12345));
            medicalFacility4.setLat(54.3739);
            medicalFacility4.setLng(18.6214);

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

            opinionRepository.save(opinion1);
            opinionRepository.save(opinion2);
            opinionRepository.save(opinion3);
            opinionRepository.save(opinion4);
        }
    }
}
