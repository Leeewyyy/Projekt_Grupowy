package pl.lokalnylekarz.projekt.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lokalnylekarz.projekt.dataTypes.Location;
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
import java.util.ArrayList;
import java.util.List;

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
            User user1 = User.builder()
                    .login("john1")
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch1@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user2 = User.builder()
                    .login("john2")
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch2@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user3 = User.builder()
                    .login("john3")
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch3@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user4 = User.builder()
                    .login("john4")
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch4@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

            Specialist specialist1 = Specialist.builder()
                    .firstname("Jane1")
                    .lastname("Doe")
                    .specialization(Specialization.ONE)
                    .build();

            Specialist specialist2 = Specialist.builder()
                    .firstname("Jane2")
                    .lastname("Doe")
                    .specialization(Specialization.TWO)
                    .build();

            Specialist specialist3 = Specialist.builder()
                    .firstname("Jane3")
                    .lastname("Doe")
                    .specialization(Specialization.THREE)
                    .build();

            Specialist specialist4 = Specialist.builder()
                    .firstname("Jane4")
                    .lastname("Doe")
                    .specialization(Specialization.FOUR)
                    .build();

            specialistRepository.save(specialist1);
            specialistRepository.save(specialist2);
            specialistRepository.save(specialist3);
            specialistRepository.save(specialist4);

            List<User> users = new ArrayList<>();
            users.add(user1);
            users.add(user3);

            MedicalFacility medicalFacility1 = MedicalFacility.builder()
                    .name("Politechnika Gdanska")
                    .type(MedicalFacilityTypes.ONE)
                    .address("ul. Politechniczna")
                    .imageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479")
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("Fajna placowka")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3739, 18.6214))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user1)
                    .favoriteFor(users)
                    .build();

            MedicalFacility medicalFacility2 = MedicalFacility.builder()
                    .name("Politechnika Gdanska")
                    .type(MedicalFacilityTypes.ONE)
                    .address("ul. Politechniczna")
                    .imageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479")
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("Fajna placowka")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3739, 18.6214))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user2)
                    .build();

            MedicalFacility medicalFacility3 = MedicalFacility.builder()
                    .name("Politechnika Gdanska")
                    .type(MedicalFacilityTypes.ONE)
                    .address("ul. Politechniczna")
                    .imageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479")
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("Fajna placowka")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3739, 18.6214))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user3)
                    .favoriteFor(users)
                    .build();

            MedicalFacility medicalFacility4 = MedicalFacility.builder()
                    .name("Politechnika Gdanska")
                    .type(MedicalFacilityTypes.ONE)
                    .address("ul. Politechniczna")
                    .imageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479")
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("Fajna placowka")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3739, 18.6214))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            medicalFacilityRepository.save(medicalFacility1);
            medicalFacilityRepository.save(medicalFacility2);
            medicalFacilityRepository.save(medicalFacility3);
            medicalFacilityRepository.save(medicalFacility4);

            Opinion opinion1 = Opinion.builder()
                    .addedBy(user1)
                    .rating(4)
                    .description("Lorem ipsum dolor sit amend")
                    .medicalFacility(medicalFacility1)
                    .build();

            Opinion opinion2 = Opinion.builder()
                    .addedBy(user2)
                    .rating(4)
                    .description("Lorem ipsum dolor sit amend")
                    .medicalFacility(medicalFacility1)
                    .build();

            Opinion opinion3 = Opinion.builder()
                    .addedBy(user3)
                    .rating(4)
                    .description("Lorem ipsum dolor sit amend")
                    .medicalFacility(medicalFacility2)
                    .build();

            Opinion opinion4 = Opinion.builder()
                    .addedBy(user4)
                    .rating(4)
                    .description("Lorem ipsum dolor sit amend")
                    .medicalFacility(medicalFacility2)
                    .build();

            opinionRepository.save(opinion1);
            opinionRepository.save(opinion2);
            opinionRepository.save(opinion3);
            opinionRepository.save(opinion4);
        }
    }
}
