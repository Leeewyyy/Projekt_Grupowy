package pl.lokalnylekarz.projekt.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lokalnylekarz.projekt.dataTypes.Image;
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
import java.util.*;

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
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch1@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user2 = User.builder()
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch2@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user3 = User.builder()
                    .firstname("John")
                    .lastname("Smith")
                    .email("john.smitch3@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user4 = User.builder()
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
                    .specialization(Specialization.CHIRURG)
                    .build();

            Specialist specialist2 = Specialist.builder()
                    .firstname("Jane2")
                    .lastname("Doe")
                    .specialization(Specialization.DERMATOLOG)
                    .build();

            Specialist specialist3 = Specialist.builder()
                    .firstname("Jane3")
                    .lastname("Doe")
                    .specialization(Specialization.KARDIOLOG)
                    .build();

            Specialist specialist4 = Specialist.builder()
                    .firstname("Jane4")
                    .lastname("Doe")
                    .specialization(Specialization.STOMATOLOG)
                    .build();

            specialistRepository.save(specialist1);
            specialistRepository.save(specialist2);
            specialistRepository.save(specialist3);
            specialistRepository.save(specialist4);

            List<User> users = new ArrayList<>();
            users.add(user1);
            users.add(user3);

            List<Image> images = new ArrayList<>();

            images.add(new Image(UUID.randomUUID().toString(), "https://www.luxmed.pl/assets/media/placowka-luxmed.jpg%3Ffm=webp&q=90&lossless=0"));
            images.add(new Image(UUID.randomUUID().toString(), "https://ocdn.eu/pulscms-transforms/1/9rFktkqTURBXy81ZGY0MWU4MDBkZjZjOTFjNDRkNWMyYTFkNGQxMDdiYi5qcGVnkpUDABHNAyDNAcKTBc0EsM0DWQ"));
            images.add(new Image(UUID.randomUUID().toString(), "https://www.kliniki.pl/photos/177/przychodnia-wielospecjalistyczna-lux-med-gliwice_176441_800x500fc.jpg"));
//            images.add(new Image(UUID.randomUUID().toString(), "https://www.luxmed.pl/assets/media/f8b48e5b2fad9ec4ecea51f910acf5a2_800_600_u_aaf09fc5.jpeg%3Ffm=webp&q=90&lossless=0"));

            MedicalFacility medicalFacility1 = MedicalFacility.builder()
                    .name("CM LUX MED")
                    .type(MedicalFacilityTypes.SZPITAL)
                    .address("Aleja Zwycięstwa 49")
                    .imageUrl("https://renoma-wroclaw.pl/wp-content/uploads/2017/08/DSC3780.jpg")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("### Opis placówki\n" +
                                         "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                                         "Commodo nulla facilisi nullam vehicula ipsum. Sit amet nisl suscipit adipiscing bibendum est.\n" +
                                         "Sit amet purus gravida quis blandit turpis cursus in. Sed elementum tempus egestas sed sed risus pretium quam.\n" +
                                         "Arcu felis bibendum ut tristique et egestas quis ipsum. Sem fringilla ut morbi tincidunt augue interdum velit.\n" +
                                         "\n" +
                                         "### Dostępne usługi\n" +
                                         "- Usługa 1\n" +
                                         "- Usługa 2\n" +
                                         "- Usługa 3\n" +
                                         "\n" +
                                         "### Godziny otwarcia\n" +
                                         "- Poniedziałek - Piątek 08:00 - 20:00\n" +
                                         "- Sobota - Niedziela 08:00 - 14:00")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.365000, 18.635410))
                    .specialist(List.of(specialistRepository.findById(1L).orElse(null)))
                    .addedBy(user1)
                    .favoriteFor(users)
                    .build();

            images = new ArrayList<>();

            images.add(new Image(UUID.randomUUID().toString(), "https://polmed.pl/wp-content/uploads/2021/11/Tczew_IMG_1630-1024x768.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://polmed.pl/wp-content/uploads/2021/11/Grzybowska_IMG_6307.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://polmed.pl/wp-content/uploads/2021/11/Wwa-Grzybowska-4_opt.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://polmed.pl/wp-content/uploads/2021/11/Wwa-Grzybowska-3_opt.jpg"));

            MedicalFacility medicalFacility2 = MedicalFacility.builder()
                    .name("POLMED Gdańsk Grunwaldzka - Centrum Medyczne")
                    .type(MedicalFacilityTypes.PRZYCHODNIA)
                    .address("Aleja Grunwaldzka 82")
                    .imageUrl("https://polmed.pl/wp-content/uploads/2021/11/Poznan_20200219_095803-1024x768.jpg")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("### Opis placówki\n" +
                                         "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                                         "Commodo nulla facilisi nullam vehicula ipsum. Sit amet nisl suscipit adipiscing bibendum est.\n" +
                                         "Sit amet purus gravida quis blandit turpis cursus in. Sed elementum tempus egestas sed sed risus pretium quam.\n" +
                                         "Arcu felis bibendum ut tristique et egestas quis ipsum. Sem fringilla ut morbi tincidunt augue interdum velit.\n" +
                                         "\n" +
                                         "### Dostępne usługi\n" +
                                         "- Usługa 1\n" +
                                         "- Usługa 2\n" +
                                         "- Usługa 3\n" +
                                         "\n" +
                                         "### Godziny otwarcia\n" +
                                         "- Poniedziałek - Piątek 08:00 - 20:00\n" +
                                         "- Sobota - Niedziela 08:00 - 14:00")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.377320, 18.608100))
                    .specialist(List.of(specialistRepository.findById(2L).orElse(null)))
                    .addedBy(user2)
                    .build();

            images = new ArrayList<>();

            images.add(new Image(UUID.randomUUID().toString(), "https://download.cloudgdansk.pl/gdansk-pl/t/202112182016.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://uckwum.pl/wp-content/uploads/2020/06/12.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://pliki.rynekzdrowia.pl/i/15/76/96/157696_r2_940.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://uck.pl/gallery/557/UCK_CMN_17.jpg"));

            MedicalFacility medicalFacility3 = MedicalFacility.builder()
                    .name("Uniwersyteckie Centrum Kliniczne")
                    .type(MedicalFacilityTypes.SZPITAL)
                    .address("Dębinki 7")
                    .imageUrl("https://www.onkonet.pl/images_news/cmn_nowa_czesc.jpg")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("### Opis placówki\n" +
                                         "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                                         "Commodo nulla facilisi nullam vehicula ipsum. Sit amet nisl suscipit adipiscing bibendum est.\n" +
                                         "Sit amet purus gravida quis blandit turpis cursus in. Sed elementum tempus egestas sed sed risus pretium quam.\n" +
                                         "Arcu felis bibendum ut tristique et egestas quis ipsum. Sem fringilla ut morbi tincidunt augue interdum velit.\n" +
                                         "\n" +
                                         "### Dostępne usługi\n" +
                                         "- Usługa 1\n" +
                                         "- Usługa 2\n" +
                                         "- Usługa 3\n" +
                                         "\n" +
                                         "### Godziny otwarcia\n" +
                                         "- Poniedziałek - Piątek 08:00 - 20:00\n" +
                                         "- Sobota - Niedziela 08:00 - 14:00")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.366310, 18.625540))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user3)
                    .favoriteFor(users)
                    .build();

            MedicalFacility medicalFacility4 = MedicalFacility.builder()
                    .name("Politechnika Gdanska")
                    .type(MedicalFacilityTypes.APTEKA)
                    .address("ul. Politechniczna")
                    .imageUrl("https://pg.edu.pl/image/journal/article?img_id=66815909&t=1515678457479;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("### Opis placówki\n" +
                                         "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                                         "Commodo nulla facilisi nullam vehicula ipsum. Sit amet nisl suscipit adipiscing bibendum est.\n" +
                                         "Sit amet purus gravida quis blandit turpis cursus in. Sed elementum tempus egestas sed sed risus pretium quam.\n" +
                                         "Arcu felis bibendum ut tristique et egestas quis ipsum. Sem fringilla ut morbi tincidunt augue interdum velit.\n" +
                                         "\n" +
                                         "### Dostępne usługi\n" +
                                         "- Usługa 1\n" +
                                         "- Usługa 2\n" +
                                         "- Usługa 3\n" +
                                         "\n" +
                                         "### Godziny otwarcia\n" +
                                         "- Poniedziałek - Piątek 08:00 - 20:00\n" +
                                         "- Sobota - Niedziela 08:00 - 14:00")
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
                    .addedBy(user4)
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
