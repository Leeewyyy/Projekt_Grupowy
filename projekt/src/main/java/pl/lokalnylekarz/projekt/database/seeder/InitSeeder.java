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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                    .fullName("John Smith")
                    .email("john.smitch1@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user2 = User.builder()
                    .fullName("John Smith")
                    .email("john.smitch2@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user3 = User.builder()
                    .fullName("John Smith")
                    .email("john.smitch3@gmail.com")
                    .password("cGFzc3dvcmQ=")
                    .build();

            User user4 = User.builder()
                    .fullName("John Smith")
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

            Specialist specialist5 = Specialist.builder()
                    .firstname("Jane5")
                    .lastname("Doe")
                    .specialization(Specialization.PSYCHOLOG)
                    .build();

            Specialist specialist6 = Specialist.builder()
                    .firstname("Jane6")
                    .lastname("Doe")
                    .specialization(Specialization.PEDIATRA)
                    .build();

            Specialist specialist7 = Specialist.builder()
                    .firstname("Jane7")
                    .lastname("Doe")
                    .specialization(Specialization.KARDIOLOG)
                    .build();

            Specialist specialist8 = Specialist.builder()
                    .firstname("Jane8")
                    .lastname("Doe")
                    .specialization(Specialization.STOMATOLOG)
                    .build();

            specialistRepository.save(specialist1);
            specialistRepository.save(specialist2);
            specialistRepository.save(specialist3);
            specialistRepository.save(specialist4);
            specialistRepository.save(specialist5);
            specialistRepository.save(specialist6);
            specialistRepository.save(specialist7);
            specialistRepository.save(specialist8);

            List<User> users = new ArrayList<>();
            users.add(user1);
            users.add(user3);

            List<Image> images = new ArrayList<>();

            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://www.luxmed.pl/assets/media/placowka-luxmed.jpg%3Ffm=webp&q=90&lossless=0"
            ));
            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://ocdn.eu/pulscms-transforms/1/9rFktkqTURBXy81ZGY0MWU4MDBkZjZjOTFjNDRkNWMyYTFkNGQxMDdiYi5qcGVnkpUDABHNAyDNAcKTBc0EsM0DWQ"
            ));
            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://www.kliniki.pl/photos/177/przychodnia-wielospecjalistyczna-lux-med-gliwice_176441_800x500fc.jpg"
            ));

            MedicalFacility medicalFacility1 = MedicalFacility.builder()
                    .name("CM LUX MED")
                    .type(MedicalFacilityTypes.SZPITAL)
                    .address("Aleja Zwycięstwa 49")
                    .imageUrl("https://renoma-wroclaw.pl/wp-content/uploads/2017/08/DSC3780.jpg")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("""
                                         ### Opis placówki
                            Grupa LUX MED jest liderem rynku prywatnych usług zdrowotnych w Polsce i częścią międzynarodowej grupy Bupa, która działa jako ubezpieczyciel i świadczeniodawca usług medycznych na całym świecie.
                                                                     
                            Zapewniamy pełną opiekę: ambulatoryjną, diagnostyczną, rehabilitacyjną, szpitalną i długoterminową dla ponad 2 500 000 Pacjentów.
                                         """)
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

            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://polmed.pl/wp-content/uploads/2021/11/Tczew_IMG_1630-1024x768.jpg"
            ));
            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://polmed.pl/wp-content/uploads/2021/11/Grzybowska_IMG_6307.jpg"
            ));
            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://polmed.pl/wp-content/uploads/2021/11/Wwa-Grzybowska-4_opt.jpg"
            ));
            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://polmed.pl/wp-content/uploads/2021/11/Wwa-Grzybowska-3_opt.jpg"
            ));

            MedicalFacility medicalFacility2 = MedicalFacility.builder()
                    .name("POLMED Gdańsk Grunwaldzka - Centrum Medyczne")
                    .type(MedicalFacilityTypes.PRZYCHODNIA)
                    .address("Aleja Grunwaldzka 82")
                    .imageUrl("https://polmed.pl/wp-content/uploads/2021/11/Poznan_20200219_095803-1024x768.jpg")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("""
                                         ### Opis placówki
                            Nasza oferta dla firm obejmuje pakiety medyczne zróżnicowane pod względem zakresu i ceny, kompleksowe zarządzanie medycyną pracy oraz szczepienia i akcje profilaktyczne (w tym testy, szczepienia i diagnostyka powikłań COVID-19).
                                                                     
                            Klientom indywidualnym oferujemy prywatne pakiety opieki medycznej, badania laboratoryjne i diagnostyczne oraz jednorazowe konsultacje lekarzy różnych specjalizacji.
                            """)
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.377320, 18.608100))
                    .specialist(List.of(specialistRepository.findById(2L).orElse(null)))
                    .addedBy(user2)
                    .build();

            images = new ArrayList<>();

            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://download.cloudgdansk.pl/gdansk-pl/t/202112182016.jpg"
            ));
            images.add(new Image(UUID.randomUUID().toString(), "https://uckwum.pl/wp-content/uploads/2020/06/12.jpg"));
            images.add(new Image(
                    UUID.randomUUID().toString(),
                    "https://pliki.rynekzdrowia.pl/i/15/76/96/157696_r2_940.jpg"
            ));
            images.add(new Image(UUID.randomUUID().toString(), "https://uck.pl/gallery/557/UCK_CMN_17.jpg"));

            MedicalFacility medicalFacility3 = MedicalFacility.builder()
                    .name("Uniwersyteckie Centrum Kliniczne")
                    .type(MedicalFacilityTypes.SZPITAL)
                    .address("Dębinki 7")
                    .imageUrl("https://www.onkonet.pl/images_news/cmn_nowa_czesc.jpg")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pg.edu.pl")
                    .description("""
                                         ### Opis placówki
                            Uniwersyteckie Centrum Kliniczne – jeden z największych szpitali w Polsce, funkcjonujący wcześniej pod nazwą Akademickie Centrum Kliniczne – zostało  utworzone przez Gdański Uniwersytet Medyczny w 1945 roku. Od tego czasu świadczymy usługi medyczne, oferując naszym pacjentom kompleksową diagnostykę i leczenie. Współpraca z uczelnią daje nam dostęp do najnowocześniejszych technologii, światowej wiedzy medycznej i badań klinicznych. Oddając się w ręce naszego zespołu, mogą Państwo czuć się bezpiecznie – w UCK pracują wybitni specjaliści, którzy do swojej dyspozycji mają nowoczesne zaplecze diagnostyczne i kliniczne.
                                         """)
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
                    .description("""
                            ### Opis placówki
                            Najlepsza uczelnia w Polsce, która oferuje wiele nowocześnie wyposażonych laboratoriów.""")
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3739, 18.6214))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            images = new ArrayList<>();

            images.add(new Image(UUID.randomUUID().toString(), "https://download.cloudgdansk.pl/gdansk-pl/t/202112182016.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://uckwum.pl/wp-content/uploads/2020/06/12.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://pliki.rynekzdrowia.pl/i/15/76/96/157696_r2_940.jpg"));
            images.add(new Image(UUID.randomUUID().toString(), "https://uck.pl/gallery/557/UCK_CMN_17.jpg"));

            MedicalFacility medicalFacility5 = MedicalFacility.builder()
                    .name("Szpital Specjalistyczny Św. Wojciecha")
                    .type(MedicalFacilityTypes.SZPITAL)
                    .address("Jana Pawła II 50")
                    .imageUrl("https://copernicus.gda.pl/gallery/652325290f7ff4e679d2853e77a0af78.1920.jpg;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://copernicus.gda.pl/")
                    .description("""
                            ### Opis placówki
                            Największy szpital w Gdańsku.
                            Oddziały:
                            Oddział Chorób Wewnętrznych\s
                            tel.: 58 768 44 92
                            Oddział Chirurgiczny Ogólny\s
                            tel.: 58 768 45 50
                            """)
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.4029503, 18.6126574))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            MedicalFacility medicalFacility6 = MedicalFacility.builder()
                    .name("Szpital im. M. Kopernika")
                    .type(MedicalFacilityTypes.SZPITAL)
                    .address("ul. Nowe Ogrody 1-6")
                    .imageUrl("https://copernicus.gda.pl/gallery/b47b3a1f1c8a81ad972fef12339c4da6.600.jpg;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://copernicus.gda.pl/koronawirus/szpital-im-m-kopernika")
                    .description("""
                            ### Opis placówki:
                            Szpital Wojewódzki w Gdańsku.
                            Oddziały:
                            Oddział Anestezjologii i Intensywnej Terapii Dzieci
                            tel.: 58 764 06 50
                                                        
                            Oddział Anestezjologii i Intensywnej Terapii Dorośli
                            tel.: 58 764 06 60
                                                        
                            Oddział chirurgii Ogólnej
                            tel.: 58 764 05 40
                                                        
                            Oddział Chirurgii Urazowo – Ortopedycznej
                            tel. do rejestracji: 58 772 39 50
                            zapisy na zabiegi planowe: 58 764 05 10
                                                        """)
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3512137, 18.6387727))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            MedicalFacility medicalFacility7 = MedicalFacility.builder()
                    .name("Przychodnia Lekarska Jasień")
                    .type(MedicalFacilityTypes.PRZYCHODNIA)
                    .address("ul. Kartuska 404")
                    .imageUrl("https://lh3.googleusercontent.com/p/AF1QipPv5qSiiTdP3wwsmQ_vKGRTvetLNdhwmW6anLb6=s680-w680-h510;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://pljasien.pl/")
                    .description(("""
                            ### Opis placówki:
                            Jesteśmy firmą, której głównym zadaniem jest opieka nad człowiekiem w jego najtrudniejszych chwilach życia, a mianowicie chorobie.
                            
                            Podstawowym zadaniem przychodni jest stworzenie przyjaznego środowiska w miejscu świadczenia
                            usług medycznych, od którego w znacznym stopniu zależy ostateczny efekt leczenia.
                            Naszym pacjentom oferujemy kompleksową obsługę, obejmującą lekarzy podstawowej opieki zdrowotnej,
                            lekarzy specjalistów oraz opiekę pielęgniarską. Cały personel jest gotów służyć pomocą naszym
                            pacjentom, wykorzystując swą wiedzę i doświadczenie.
                            Działania podejmowane przez P.L."Jasień" mają za zadanie budowanie marki naszej przychodni,
                            tak aby kojarzyła się z najwyższą jakością świadczonych usług medycznych oraz kulturą obsługi,
                            a także skuteczną i profesjonalną profilaktyką.
                            """))
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3421424, 18.5510178))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            MedicalFacility medicalFacility8 = MedicalFacility.builder()
                    .name("Centrum Zdrowia ETER-MED")
                    .type(MedicalFacilityTypes.PRZYCHODNIA)
                    .address("ul. Żabi Kruk 10")
                    .imageUrl("https://lh3.googleusercontent.com/p/AF1QipPLoWcd18EC6vRYUD9bJcrpSCMmGqEDhnsphtY7=s680-w680-h510;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://etermed.pl/")
                    .description("""
                                    ### Opis placówki:
                            Jesteśmy polską, innowacyjną, dynamicznie rozwijająca się firmą, która istnieje na rynku medycznym od 1999 roku.
                                                                
                            Świadczymy usługi zarówno z zakresu podstawowej, jak i specjalistycznej opieki zdrowotnej dla wszystkich osób ubezpieczonych w Narodowym Funduszu Zdrowia (NFZ), a także dla klientów prywatnych.
                                                                
                            Świadczymy również odpłatne specjalistyczne konsultacje medyczne, posiadamy ofertę skierowaną do klubów sportowych, ale też dla osób fizycznych i firm.
                                    """)
                    .isNFZ(true)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3739, 18.6214))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            MedicalFacility medicalFacility9 = MedicalFacility.builder()
                    .name("APTEKA POLSKA APTE.PL")
                    .type(MedicalFacilityTypes.APTEKA)
                    .address("ul. aleja Jana Pawła II 6D")
                    .imageUrl("https://lh3.googleusercontent.com/p/AF1QipNqYlnKsCKOgw2cYypCEM75sLBC7_VI2Phl9geG=s680-w680-h510;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://apte.pl")
                    .description("""
                                    ### Opis placówki
                            Nie jesteśmy firmą z kapitałem zagranicznym, ale rodzinną oraz rodzimą firmą starającą się, aby za jakiś czas hasło "polski przedsiębiorca" nie było tylko wspomnieniem. Możliwości finansowe mamy ograniczone, ale nadrabiamy pomysłowością i zapałem.
                                                       
                            Apteka internetowa www.apte.pl to apteka, w której kupisz leki i kosmetyki przez internet z dostawą do domu. U nas możesz zamówić niezbędne leki dostępne bez recepty, suplementy diety, kosmetyki i sprzęt medyczny. Apteka internetowa nie jest jedyną formą działalności naszej firmy. Oprócz tego serwisu nasza firma prowadzi również "tradycyjne" apteki. Informację o nich znajdziesz w dziale Nasze apteki. Dzięki ponad 20-letniemu doświadczeniu zdobytemu w trakcie ich prowadzenia, w ofercie internetowej umieściliśmy produkty, które sprawdziły się już w praktyce, posiadają najwyższą skuteczność w działaniu i cieszą się uznaniem naszych pacjentów.
                            """)
                    .isNFZ(false)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.391661, 18.5997536))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            MedicalFacility medicalFacility10 = MedicalFacility.builder()
                    .name("Dom Leków")
                    .type(MedicalFacilityTypes.APTEKA)
                    .address("ul. Władysława Cieszyńskiego 1A")
                    .imageUrl("https://lh3.googleusercontent.com/p/AF1QipNszC0Ujn6HkzozphJQKoA-k2bWDXClVWWy7Kn4=s680-w680-h510;")
                    .images(images)
                    .phone("+48123123123")
                    .websiteUrl("https://domlekow.pl/")
                    .description("""
                            ### Opis placówki
                            Apteki Dom Leków związane są z polskim rynkiem farmaceutycznym od 30 lat. Pierwsza Apteka pod szyldem "Dom Leków" została otwarta w Gdyni w 1990 roku i nadal nieprzerwanie prowadzi swoją działalność. Kolejne Apteki powstały w oparciu o tradycję i doświadczenie farmaceutyczne zdobyte na przestrzeni lat, aktualizowane o zmieniające się warunki podstawowej opieki farmaceutycznej. W chwili obecnej na terenie Polski jest otwartych 68 Aptek Dom Leków, w tym apteki całodobowe i otwarte przez 7 dni w tygodniu.
                            """)
                    .isNFZ(false)
                    .rating(3)
                    .openFrom(new Timestamp(12))
                    .openTo(new Timestamp(12345))
                    .location(new Location(54.3376318, 18.6125888))
                    .specialist((List<Specialist>) specialistRepository.findAll())
                    .addedBy(user4)
                    .build();

            medicalFacilityRepository.save(medicalFacility1);
            medicalFacilityRepository.save(medicalFacility2);
            medicalFacilityRepository.save(medicalFacility3);
            medicalFacilityRepository.save(medicalFacility4);
            medicalFacilityRepository.save(medicalFacility5);
            medicalFacilityRepository.save(medicalFacility6);
            medicalFacilityRepository.save(medicalFacility7);
            medicalFacilityRepository.save(medicalFacility8);
            medicalFacilityRepository.save(medicalFacility9);
            medicalFacilityRepository.save(medicalFacility10);

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
