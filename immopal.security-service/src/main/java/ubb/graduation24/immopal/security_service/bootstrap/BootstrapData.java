package ubb.graduation24.immopal.security_service.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.security_service.domain.Role;
import ubb.graduation24.immopal.security_service.model.RegisterRequest;
import ubb.graduation24.immopal.security_service.model.UserDto;
import ubb.graduation24.immopal.security_service.repository.UserRepository;
import ubb.graduation24.immopal.security_service.service.AuthenticationService;
import ubb.graduation24.immopal.security_service.service.IUserService;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final AuthenticationService authenticationService;
    private final IUserService userService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void run(final String... args) throws Exception {
        log.debug("Starting bootstrap data...");
        if (userRepository.count() == 0) {
            loadAgentsData();
            loadCustomersData();
            loadAdminData();
        }
    }

    private void loadAgentsData() {
        log.debug("Loading agents data...");
        RegisterRequest req1 = RegisterRequest.builder()
                .email("tammy@immopal.com")
                .password("ImmoP4ll")
                .firstName("Tammy")
                .lastName("Brighton")
                .phoneNumber("(221) 75 123 456")
                .dateOfBirth(LocalDate.of(1975, 8, 17))
                .address("NY, USA")
                .pictureUrl("/user/agent/immo_agen_0.jpeg")
                .build();
        authenticationService.register(req1);
        Long id1 = userService.getUserByEmail("tammy@immopal.com").getUserId();
        UserDto userDto1 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id1, userDto1);

        RegisterRequest req2 = RegisterRequest.builder()
                .email("angela@immopal.com")
                .password("ImmoP4ll")
                .firstName("Angela")
                .lastName("Trompster")
                .phoneNumber("(234) 12 498 374")
                .dateOfBirth(LocalDate.of(1990, 3, 21))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_1.jpeg")
                .build();
        authenticationService.register(req2);
        Long id2 = userService.getUserByEmail("angela@immopal.com").getUserId();
        UserDto userDto2 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id2, userDto2);

        RegisterRequest req3 = RegisterRequest.builder()
                .email("thomas@immopal.com")
                .password("ImmoP4ll")
                .firstName("Thomas")
                .lastName("Livington")
                .phoneNumber("(213) 23 896 285")
                .dateOfBirth(LocalDate.of(2000, 12, 11))
                .address("QC, Canada")
                .pictureUrl("/user/agent/immo_agen_4.jpeg")
                .build();
        authenticationService.register(req3);
        Long id3 = userService.getUserByEmail("thomas@immopal.com").getUserId();
        UserDto userDto3 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id3, userDto3);

        RegisterRequest req4 = RegisterRequest.builder()
                .email("kelly@immopal.com")
                .password("ImmoP4ll")
                .firstName("Kelly")
                .lastName("Nester")
                .phoneNumber("(234) 12 555 222")
                .dateOfBirth(LocalDate.of(2003, 1, 31))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_2.jpeg")
                .build();
        authenticationService.register(req4);
        Long id4 = userService.getUserByEmail("kelly@immopal.com").getUserId();
        UserDto userDto4 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id4, userDto4);

        RegisterRequest req5 = RegisterRequest.builder()
                .email("sofy@immopal.com")
                .password("ImmoP4ll")
                .firstName("Sofy")
                .lastName("Thomp")
                .phoneNumber("(112) 78 125 782")
                .dateOfBirth(LocalDate.of(2005, 5, 5))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_3.jpeg")
                .build();
        authenticationService.register(req5);
        Long id5 = userService.getUserByEmail("sofy@immopal.com").getUserId();
        UserDto userDto5 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id5, userDto5);

        RegisterRequest req6 = RegisterRequest.builder()
                .email("john@immopal.com")
                .password("ImmoP4ll")
                .firstName("John")
                .lastName("Doester")
                .phoneNumber("(231) 23 435 782")
                .dateOfBirth(LocalDate.of(1966, 5, 25))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_5.jpeg")
                .build();
        authenticationService.register(req6);
        Long id6 = userService.getUserByEmail("john@immopal.com").getUserId();
        UserDto userDto6 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id6, userDto6);

        RegisterRequest req7 = RegisterRequest.builder()
                .email("corry@immopal.com")
                .password("ImmoP4ll")
                .firstName("Corry")
                .lastName("Wolf")
                .phoneNumber("(678) 67 345 123")
                .dateOfBirth(LocalDate.of(2000, 11, 5))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_6.jpeg")
                .build();
        authenticationService.register(req7);
        Long id7 = userService.getUserByEmail("corry@immopal.com").getUserId();
        UserDto userDto7 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id7, userDto7);

        RegisterRequest req8 = RegisterRequest.builder()
                .email("helen@immopal.com")
                .password("ImmoP4ll")
                .firstName("Helen")
                .lastName("Dumper")
                .phoneNumber("(122) 22 521 666")
                .dateOfBirth(LocalDate.of(1982, 5, 25))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_7.jpeg")
                .build();
        authenticationService.register(req8);
        Long id8 = userService.getUserByEmail("helen@immopal.com").getUserId();
        UserDto userDto8 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id8, userDto8);

        RegisterRequest req9 = RegisterRequest.builder()
                .email("bud@immopal.com")
                .password("ImmoP4ll")
                .firstName("Bud")
                .lastName("Gun")
                .phoneNumber("(142) 76 174 784")
                .dateOfBirth(LocalDate.of(2000, 9, 7))
                .address("NY, USA")
                .pictureUrl("/user/agent/immo_agen_8.jpeg")
                .build();
        authenticationService.register(req9);
        Long id9 = userService.getUserByEmail("bud@immopal.com").getUserId();
        UserDto userDto9 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id9, userDto9);

        RegisterRequest req10 = RegisterRequest.builder()
                .email("steven@immopal.com")
                .password("ImmoP4ll")
                .firstName("Steven")
                .lastName("Avira")
                .phoneNumber("(731) 53 426 435")
                .dateOfBirth(LocalDate.of(1950, 2, 18))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_9.jpeg")
                .build();
        authenticationService.register(req10);
        Long id10 = userService.getUserByEmail("steven@immopal.com").getUserId();
        UserDto userDto10 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id10, userDto10);

        RegisterRequest req11 = RegisterRequest.builder()
                .email("phil@immopal.com")
                .password("ImmoP4ll")
                .firstName("Phil")
                .lastName("Coen")
                .phoneNumber("(637) 23 544 222")
                .dateOfBirth(LocalDate.of(1966, 10, 2))
                .address("QC, Canada")
                .pictureUrl("/user/agent/immo_agen_10.jpeg")
                .build();
        authenticationService.register(req11);
        Long id11 = userService.getUserByEmail("phil@immopal.com").getUserId();
        UserDto userDto11 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id11, userDto11);

        RegisterRequest req12 = RegisterRequest.builder()
                .email("ursula@immopal.com")
                .password("ImmoP4ll")
                .firstName("Ursula")
                .lastName("von Kritz")
                .phoneNumber("(234) 22 432 133")
                .dateOfBirth(LocalDate.of(1971, 4, 16))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_11.jpeg")
                .build();
        authenticationService.register(req12);
        Long id12 = userService.getUserByEmail("ursula@immopal.com").getUserId();
        UserDto userDto12 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id12, userDto12);

        RegisterRequest req13 = RegisterRequest.builder()
                .email("zlatan@immopal.com")
                .password("ImmoP4ll")
                .firstName("Zlatan")
                .lastName("Kretchinsky")
                .phoneNumber("(222) 34 232 987")
                .dateOfBirth(LocalDate.of(1969, 6, 5))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_12.jpeg")
                .build();
        authenticationService.register(req13);
        Long id13 = userService.getUserByEmail("zlatan@immopal.com").getUserId();
        UserDto userDto13 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id13, userDto13);

        RegisterRequest req14 = RegisterRequest.builder()
                .email("vanessa@immopal.com")
                .password("ImmoP4ll")
                .firstName("Vanessa")
                .lastName("Polsten")
                .phoneNumber("(111) 23 323 333")
                .dateOfBirth(LocalDate.of(1975, 7, 15))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_13.jpeg")
                .build();
        authenticationService.register(req14);
        Long id14 = userService.getUserByEmail("vanessa@immopal.com").getUserId();
        UserDto userDto14 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id14, userDto14);

        RegisterRequest req15 = RegisterRequest.builder()
                .email("simonne@immopal.com")
                .password("ImmoP4ll")
                .firstName("Simonne")
                .lastName("Tommiger")
                .phoneNumber("(123) 33 345 767")
                .dateOfBirth(LocalDate.of(2006, 8, 15))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_14.jpeg")
                .build();
        authenticationService.register(req15);
        Long id15 = userService.getUserByEmail("simonne@immopal.com").getUserId();
        UserDto userDto15 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id15, userDto15);

        RegisterRequest req16 = RegisterRequest.builder()
                .email("lilly@immopal.com")
                .password("ImmoP4ll")
                .firstName("Lilly")
                .lastName("Valley")
                .phoneNumber("(111) 77 221 667")
                .dateOfBirth(LocalDate.of(1988, 12, 25))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_15.jpeg")
                .build();
        authenticationService.register(req16);
        Long id16 = userService.getUserByEmail("lilly@immopal.com").getUserId();
        UserDto userDto16 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id16, userDto16);

        RegisterRequest req17 = RegisterRequest.builder()
                .email("smith@immopal.com")
                .password("ImmoP4ll")
                .firstName("Smith")
                .lastName("Johansson")
                .phoneNumber("(454) 54 125 121")
                .dateOfBirth(LocalDate.of(2004, 2, 14))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_16.jpeg")
                .build();
        authenticationService.register(req17);
        Long id17 = userService.getUserByEmail("smith@immopal.com").getUserId();
        UserDto userDto17 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id17, userDto17);

        RegisterRequest req18 = RegisterRequest.builder()
                .email("bob@immopal.com")
                .password("ImmoP4ll")
                .firstName("Bob")
                .lastName("Doncaster")
                .phoneNumber("(678) 21 654 445")
                .dateOfBirth(LocalDate.of(2000, 6, 25))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_17.jpeg")
                .build();
        authenticationService.register(req18);
        Long id18 = userService.getUserByEmail("john@immopal.com").getUserId();
        UserDto userDto18 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id18, userDto18);

        RegisterRequest req19 = RegisterRequest.builder()
                .email("dylan@immopal.com")
                .password("ImmoP4ll")
                .firstName("Dylan")
                .lastName("Chakal")
                .phoneNumber("(233) 65 566 123")
                .dateOfBirth(LocalDate.of(2002, 10, 17))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_18.jpeg")
                .build();
        authenticationService.register(req19);
        Long id19 = userService.getUserByEmail("dylan@immopal.com").getUserId();
        UserDto userDto19 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id19, userDto19);

        RegisterRequest req20 = RegisterRequest.builder()
                .email("stephan@immopal.com")
                .password("ImmoP4ll")
                .firstName("Stephan")
                .lastName("Sponge")
                .phoneNumber("(122) 22 521 666")
                .dateOfBirth(LocalDate.of(1982, 5, 25))
                .address("CA, USA")
                .pictureUrl("/user/agent/immo_agen_19.jpeg")
                .build();
        authenticationService.register(req20);
        Long id20 = userService.getUserByEmail("stephan@immopal.com").getUserId();
        UserDto userDto20 = UserDto.builder()
                .role(Role.AGENT)
                .build();
        userService.updateUser(id20, userDto20);
    }


    private void loadCustomersData() {
        RegisterRequest req1 = RegisterRequest.builder()
                .email("sophia@test.com")
                .password("ImmoP4ll")
                .firstName("Sophia")
                .lastName("Hawthorne")
                .phoneNumber("(222) 12 233 456")
                .dateOfBirth(LocalDate.of(1999, 8, 17))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_0.jpeg")
                .build();
        authenticationService.register(req1);

        RegisterRequest req2 = RegisterRequest.builder()
                .email("Ethan@test.com")
                .password("ImmoP4ll")
                .firstName("Ethan")
                .lastName("Blackwood")
                .phoneNumber("(213) 33 222 456")
                .dateOfBirth(LocalDate.of(1970, 8, 17))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_1.jpeg")
                .build();
        authenticationService.register(req2);

        RegisterRequest req3 = RegisterRequest.builder()
                .email("Lucas@test.com")
                .password("ImmoP4ll")
                .firstName("Lucas")
                .lastName("Drayton")
                .phoneNumber("(232) 76 878 234")
                .dateOfBirth(LocalDate.of(1971, 2, 7))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_2.jpeg")
                .build();
        authenticationService.register(req3);

        RegisterRequest req4 = RegisterRequest.builder()
                .email("Mason@test.com")
                .password("ImmoP4ll")
                .firstName("Mason")
                .lastName("Kingsley")
                .phoneNumber("(232) 43 762 213")
                .dateOfBirth(LocalDate.of(1981, 12, 1))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_3.jpeg")
                .build();
        authenticationService.register(req4);

        RegisterRequest req5 = RegisterRequest.builder()
                .email("Isabella@test.com")
                .password("ImmoP4ll")
                .firstName("Isabella")
                .lastName("Sterling")
                .phoneNumber("(231) 63 454 897")
                .dateOfBirth(LocalDate.of(2006, 2, 12))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_4.jpeg")
                .build();
        authenticationService.register(req5);

        RegisterRequest req6 = RegisterRequest.builder()
                .email("Ava@test.com")
                .password("ImmoP4ll")
                .firstName("Ava")
                .lastName("Montgomery")
                .phoneNumber("(333) 63 111 432")
                .dateOfBirth(LocalDate.of(2004, 12, 15))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_5.jpeg")
                .build();
        authenticationService.register(req6);

        RegisterRequest req7 = RegisterRequest.builder()
                .email("Liam@test.com")
                .password("ImmoP4ll")
                .firstName("Liam")
                .lastName("Winslow")
                .phoneNumber("(433) 43 222 342")
                .dateOfBirth(LocalDate.of(1968, 8, 30))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_6.jpeg")
                .build();
        authenticationService.register(req7);

        RegisterRequest req8 = RegisterRequest.builder()
                .email("Noah@test.com")
                .password("ImmoP4ll")
                .firstName("Noah")
                .lastName("Remington")
                .phoneNumber("(222) 87 324 342")
                .dateOfBirth(LocalDate.of(1979, 12, 22))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_7.jpeg")
                .build();
        authenticationService.register(req8);

        RegisterRequest req9 = RegisterRequest.builder()
                .email("Emma@test.com")
                .password("ImmoP4ll")
                .firstName("Emma")
                .lastName("Pendleton")
                .phoneNumber("(233) 34 765 745")
                .dateOfBirth(LocalDate.of(2000, 4, 1))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_8.jpeg")
                .build();
        authenticationService.register(req9);

        RegisterRequest req10 = RegisterRequest.builder()
                .email("Aiden@test.com")
                .password("ImmoP4ll")
                .firstName("Aiden")
                .lastName("Fairfax")
                .phoneNumber("(323) 12 444 745")
                .dateOfBirth(LocalDate.of(1966, 6, 12))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_9.jpeg")
                .build();
        authenticationService.register(req10);

        RegisterRequest req11 = RegisterRequest.builder()
                .email("Benjamin@test.com")
                .password("ImmoP4ll")
                .firstName("Benjamin")
                .lastName("Langley")
                .phoneNumber("(111) 23 233 545")
                .dateOfBirth(LocalDate.of(1980, 9, 22))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_10.jpeg")
                .build();
        authenticationService.register(req11);

        RegisterRequest req12 = RegisterRequest.builder()
                .email("Alexander@test.com")
                .password("ImmoP4ll")
                .firstName("Alexander")
                .lastName("Langley")
                .phoneNumber("(123) 23 434 444")
                .dateOfBirth(LocalDate.of(1982, 2, 16))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_11.jpeg")
                .build();
        authenticationService.register(req12);

        RegisterRequest req13 = RegisterRequest.builder()
                .email("Olivia@test.com")
                .password("ImmoP4ll")
                .firstName("Olivia")
                .lastName("Thorne")
                .phoneNumber("(324) 44 345 217")
                .dateOfBirth(LocalDate.of(1985, 6, 22))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_12.jpeg")
                .build();
        authenticationService.register(req13);

        RegisterRequest req14 = RegisterRequest.builder()
                .email("James@test.com")
                .password("ImmoP4ll")
                .firstName("James")
                .lastName("Kensington")
                .phoneNumber("(233) 76 127 642")
                .dateOfBirth(LocalDate.of(1975, 6, 2))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_13.jpeg")
                .build();
        authenticationService.register(req14);

        RegisterRequest req15 = RegisterRequest.builder()
                .email("Mia@test.com")
                .password("ImmoP4ll")
                .firstName("Mia")
                .lastName("Ellington")
                .phoneNumber("(444) 32 873 345")
                .dateOfBirth(LocalDate.of(1988, 9, 19))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_14.jpeg")
                .build();
        authenticationService.register(req15);

        RegisterRequest req16 = RegisterRequest.builder()
                .email("Harper@test.com")
                .password("ImmoP4ll")
                .firstName("Harper")
                .lastName("Lockwood")
                .phoneNumber("(123) 67 567 785")
                .dateOfBirth(LocalDate.of(1977, 11, 5))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_15.jpeg")
                .build();
        authenticationService.register(req16);

        RegisterRequest req17 = RegisterRequest.builder()
                .email("Jackson@test.com")
                .password("ImmoP4ll")
                .firstName("Jackson")
                .lastName("Marlowe")
                .phoneNumber("(123) 33 345 785")
                .dateOfBirth(LocalDate.of(1982, 1, 15))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_16.jpeg")
                .build();
        authenticationService.register(req17);

        RegisterRequest req18 = RegisterRequest.builder()
                .email("Charlotte@test.com")
                .password("ImmoP4ll")
                .firstName("Charlotte")
                .lastName("Sinclair")
                .phoneNumber("(213) 33 232 345")
                .dateOfBirth(LocalDate.of(2003, 8, 13))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_17.jpeg")
                .build();
        authenticationService.register(req18);

        RegisterRequest req19 = RegisterRequest.builder()
                .email("Amelia@test.com")
                .password("ImmoP4ll")
                .firstName("Amelia")
                .lastName("Waverly")
                .phoneNumber("(832) 54 457 765")
                .dateOfBirth(LocalDate.of(2002, 5, 23))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_18.jpeg")
                .build();
        authenticationService.register(req19);

        RegisterRequest req20 = RegisterRequest.builder()
                .email("Lily@test.com")
                .password("ImmoP4ll")
                .firstName("Lily")
                .lastName("Hawke")
                .phoneNumber("(234) 55 845 343")
                .dateOfBirth(LocalDate.of(2001, 12, 14))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_19.jpeg")
                .build();
        authenticationService.register(req20);

        RegisterRequest req21 = RegisterRequest.builder()
                .email("Gabriel@test.com")
                .password("ImmoP4ll")
                .firstName("Gabriel")
                .lastName("Winscott")
                .phoneNumber("(232) 32 744 322")
                .dateOfBirth(LocalDate.of(1978, 10, 2))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_20.jpeg")
                .build();
        authenticationService.register(req21);

        RegisterRequest req22 = RegisterRequest.builder()
                .email("Ella@test.com")
                .password("ImmoP4ll")
                .firstName("Ella")
                .lastName("Radcliffe")
                .phoneNumber("(127) 34 763 453")
                .dateOfBirth(LocalDate.of(1999, 9, 28))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_21.jpeg")
                .build();
        authenticationService.register(req22);

        RegisterRequest req23 = RegisterRequest.builder()
                .email("Samuel@test.com")
                .password("ImmoP4ll")
                .firstName("Samuel")
                .lastName("Ashford")
                .phoneNumber("(234) 43 723 582")
                .dateOfBirth(LocalDate.of(1986, 3, 12))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_22.jpeg")
                .build();
        authenticationService.register(req23);

        RegisterRequest req24 = RegisterRequest.builder()
                .email("Grace@test.com")
                .password("ImmoP4ll")
                .firstName("Grace")
                .lastName("Vanderlyn")
                .phoneNumber("(231) 87 524 555")
                .dateOfBirth(LocalDate.of(1983, 11, 30))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_23.jpeg")
                .build();
        authenticationService.register(req24);

        RegisterRequest req25 = RegisterRequest.builder()
                .email("Elijah@test.com")
                .password("ImmoP4ll")
                .firstName("Elijah")
                .lastName("Prescott")
                .phoneNumber("(211) 25 732 222")
                .dateOfBirth(LocalDate.of(1984, 10, 24))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_24.jpeg")
                .build();
        authenticationService.register(req25);

        RegisterRequest req26 = RegisterRequest.builder()
                .email("Scarlett@test.com")
                .password("ImmoP4ll")
                .firstName("Scarlett")
                .lastName("Winslow")
                .phoneNumber("(211) 25 333 735")
                .dateOfBirth(LocalDate.of(2004, 2, 28))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_25.jpeg")
                .build();
        authenticationService.register(req26);

        RegisterRequest req27 = RegisterRequest.builder()
                .email("Henry@test.com")
                .password("ImmoP4ll")
                .firstName("Henry")
                .lastName("Drummond")
                .phoneNumber("(116) 25 544 245")
                .dateOfBirth(LocalDate.of(2000, 12, 8))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_26.jpeg")
                .build();
        authenticationService.register(req27);

        RegisterRequest req28 = RegisterRequest.builder()
                .email("Aria@test.com")
                .password("ImmoP4ll")
                .firstName("Aria")
                .lastName("Langford")
                .phoneNumber("(229) 36 745 667")
                .dateOfBirth(LocalDate.of(2000, 2, 18))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_27.jpeg")
                .build();
        authenticationService.register(req28);

        RegisterRequest req29 = RegisterRequest.builder()
                .email("Chloe@test.com")
                .password("ImmoP4ll")
                .firstName("Chloe")
                .lastName("Fenwick")
                .phoneNumber("(232) 11 865 345")
                .dateOfBirth(LocalDate.of(2001, 12, 13))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_28.jpeg")
                .build();
        authenticationService.register(req29);

        RegisterRequest req30 = RegisterRequest.builder()
                .email("Oliver@test.com")
                .password("ImmoP4ll")
                .firstName("Oliver")
                .lastName("Thackeray")
                .phoneNumber("(122) 54 434 888")
                .dateOfBirth(LocalDate.of(1964, 3, 31))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_29.jpeg")
                .build();
        authenticationService.register(req30);

        RegisterRequest req31 = RegisterRequest.builder()
                .email("Violet@test.com")
                .password("ImmoP4ll")
                .firstName("Violet")
                .lastName("Merrick")
                .phoneNumber("(122) 54 754 222")
                .dateOfBirth(LocalDate.of(2003, 8, 27))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_30.jpeg")
                .build();
        authenticationService.register(req31);

        RegisterRequest req32 = RegisterRequest.builder()
                .email("Sebastian@test.com")
                .password("ImmoP4ll")
                .firstName("Sebastian")
                .lastName("Hawke")
                .phoneNumber("(345) 33 765 354")
                .dateOfBirth(LocalDate.of(1996, 7, 17))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_31.jpeg")
                .build();
        authenticationService.register(req32);

        RegisterRequest req33 = RegisterRequest.builder()
                .email("Hazel@test.com")
                .password("ImmoP4ll")
                .firstName("Hazel")
                .lastName("Lockhart")
                .phoneNumber("(435) 43 824 342")
                .dateOfBirth(LocalDate.of(1996, 7, 17))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_32.jpeg")
                .build();
        authenticationService.register(req33);

        RegisterRequest req34 = RegisterRequest.builder()
                .email("Zoe@test.com")
                .password("ImmoP4ll")
                .firstName("Zoe")
                .lastName("Harrington")
                .phoneNumber("(435) 43 824 342")
                .dateOfBirth(LocalDate.of(2002, 11, 3))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_33.jpeg")
                .build();
        authenticationService.register(req34);

        RegisterRequest req35 = RegisterRequest.builder()
                .email("Penelope@test.com")
                .password("ImmoP4ll")
                .firstName("Penelope")
                .lastName("Hawthorne")
                .phoneNumber("(122) 22 123 321")
                .dateOfBirth(LocalDate.of(2005, 1, 31))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_34.jpeg")
                .build();
        authenticationService.register(req35);

        RegisterRequest req36 = RegisterRequest.builder()
                .email("Evelyn@test.com")
                .password("ImmoP4ll")
                .firstName("Evelyn")
                .lastName("Norwood")
                .phoneNumber("(122) 22 123 321")
                .dateOfBirth(LocalDate.of(2004, 10, 26))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_35.jpeg")
                .build();
        authenticationService.register(req36);

        RegisterRequest req37 = RegisterRequest.builder()
                .email("William@test.com")
                .password("ImmoP4ll")
                .firstName("William")
                .lastName("Sterling")
                .phoneNumber("(117) 23 454 123")
                .dateOfBirth(LocalDate.of(1974, 5, 16))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_36.jpeg")
                .build();
        authenticationService.register(req37);

        RegisterRequest req38 = RegisterRequest.builder()
                .email("Theodore@test.com")
                .password("ImmoP4ll")
                .firstName("Theodore")
                .lastName("Calvert")
                .phoneNumber("(114) 23 853 555")
                .dateOfBirth(LocalDate.of(1990, 9, 21))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_37.jpeg")
                .build();
        authenticationService.register(req38);

        RegisterRequest req39 = RegisterRequest.builder()
                .email("Abigail@test.com")
                .password("ImmoP4ll")
                .firstName("Abigail")
                .lastName("Prescott")
                .phoneNumber("(123) 23 345 823")
                .dateOfBirth(LocalDate.of(2002, 6, 22))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_38.jpeg")
                .build();
        authenticationService.register(req39);

        RegisterRequest req40 = RegisterRequest.builder()
                .email("Tristan@test.com")
                .password("ImmoP4ll")
                .firstName("Tristan")
                .lastName("Bexley")
                .phoneNumber("(333) 23 546 674")
                .dateOfBirth(LocalDate.of(1980, 4, 19))
                .address("NY, USA")
                .pictureUrl("/user/customer/immo_cust_39.jpeg")
                .build();
        authenticationService.register(req40);
    }

    private void loadAdminData() {
        RegisterRequest req0 = RegisterRequest.builder()
                .email("mihai@immopal.com")
                .password("ImmoP4ll")
                .firstName("Mihai")
                .lastName("Suciu")
                .phoneNumber("(122) 22 521 666")
                .dateOfBirth(LocalDate.of(1982, 5, 25))
                .address("CA, USA")
                .pictureUrl("/user/user_icon.png")
                .build();
        authenticationService.register(req0);
        Long id0 = userService.getUserByEmail("mihai@immopal.com").getUserId();
        UserDto userDto0 = UserDto.builder()
                .role(Role.ADMIN)
                .build();
        userService.updateUser(id0, userDto0);
    }


}
