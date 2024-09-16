package ubb.graduation24.immopal.agency_service.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.agency_service.domain.Address;
import ubb.graduation24.immopal.agency_service.domain.Agency;
import ubb.graduation24.immopal.agency_service.domain.AgencyAgent;
import ubb.graduation24.immopal.agency_service.domain.AgencyProperty;
import ubb.graduation24.immopal.agency_service.repository.AddressRepository;
import ubb.graduation24.immopal.agency_service.repository.AgencyAgentRepository;
import ubb.graduation24.immopal.agency_service.repository.AgencyPropertyRepository;
import ubb.graduation24.immopal.agency_service.repository.AgencyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final AgencyRepository agencyRepository;
    private final AgencyAgentRepository agencyAgentRepository;
    private final AgencyPropertyRepository agencyPropertyRepository;
    private final AddressRepository addressRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadAddressData();
        loadAgencyData();
        loadAgencyAgentData();
        loadAgencyPropertyData();
    }

    private void loadAddressData() {
        if (addressRepository.count() == 0) {
            Address address1 = Address.builder()
                    .country("United States")
                    .state("New York")
                    .city("New York")
                    .neighborhood("Manhattan")
                    .street("5th Ave")
                    .streetNo(8L)
                    .latitude(46.77315)
                    .longitude(-73.60031)
                    .build();

            Address address2 = Address.builder()
                    .country("United States")
                    .state("Los Angeles")
                    .city("California")
                    .neighborhood("Hollywood")
                    .street("6th Ave")
                    .streetNo(28L)
                    .latitude(46.77315)
                    .longitude(-73.60031)
                    .build();

            Address address3 = Address.builder()
                    .country("United States")
                    .state("Los Angeles")
                    .city("California")
                    .neighborhood("Downtown")
                    .street("12th Ave")
                    .streetNo(15L)
                    .latitude(46.77315)
                    .longitude(-73.60031)
                    .build();

            Address address4 = Address.builder()
                    .country("United States")
                    .state("New York")
                    .city("New York")
                    .neighborhood("Manhattan")
                    .street("15th Ave")
                    .streetNo(18L)
                    .latitude(46.77315)
                    .longitude(-73.60031)
                    .build();

            addressRepository.saveAll(Arrays.asList(address1, address2, address3, address4));
        }
    }

    private void loadAgencyData() {
        if (agencyRepository.count() == 0) {
            Agency agency1 = Agency.builder()
                    .name("Dreamscape Realty")
                    .address(addressRepository.getReferenceById(1L))
                    .phone("(0264) 123-4567")
                    .email("info@dreamscaperealty.com")
                    .description("We turn your property dreams into reality. " +
                            "Our team of passionate agents specializes in finding " +
                            "hidden gems, from cozy cottages to luxurious penthouses. " +
                            "Whether you’re buying or selling, let us guide you through " +
                            "the world of real estate.")
                    .logoUrl("/agencies/logo/logo_0.jpeg")
                    .build();

            Agency agency2 = Agency.builder()
                    .name("Urban Haven Properties")
                    .address(addressRepository.getReferenceById(2L))
                    .phone("(0264) 987-6543")
                    .email("hello@urbanhavenproperties.com")
                    .description("Welcome to Urban Haven Properties, where city living meets tranquility. " +
                            "Our boutique agency focuses on urban retreats, chic lofts, and stylish condos. " +
                            "We believe that home is not just a place, it’s an experience. " +
                            "Let us help you find your sanctuary.")
                    .logoUrl("/agencies/logo/logo_1.jpeg")
                    .build();

            Agency agency3 = Agency.builder()
                    .name("Hillview Estates")
                    .address(addressRepository.getReferenceById(3L))
                    .phone("(0264) 789-0123")
                    .email("explore@harborviewestates.com")
                    .description("Hillview Estates, the name says it all. With a portfolio of" +
                            " scenic views, and mountain charm, we redefine hill living. " +
                            "Our agents are view and sunset enthusiasts.")
                    .logoUrl("/agencies/logo/logo_2.jpeg")
                    .build();

            Agency agency4 = Agency.builder()
                    .name("Skyline Realty Group")
                    .address(addressRepository.getReferenceById(4L))
                    .phone("(0264) 234-5678")
                    .email("info@skylinerealtygroup.com")
                    .description("Elevate your real estate journey with Skyline Realty Group. Our mission? " +
                            "To reach new heights. From high-rise condos to penthouses with panoramic views.")
                    .logoUrl("/agencies/logo/logo_3.jpeg")
                    .build();

            agencyRepository.saveAll(Arrays.asList(agency1, agency2, agency3, agency4));
        }
    }

    private void loadAgencyAgentData() {
        if (agencyAgentRepository.count() == 0) {
            List<AgencyAgent> agencyAgents = new ArrayList<>();
            for (long i = 1; i <= 4; i++) {
                switch ((int) i) {
                    case 1:
                        for (long j = 1; j <= 5; j++) {
                            AgencyAgent agencyAgent = AgencyAgent.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .agentId(j)
                                    .build();
                            agencyAgents.add(agencyAgent);
                        }
                        break;
                    case 2:
                        for (long j = 6; j <= 10; j++) {
                            AgencyAgent agencyAgent = AgencyAgent.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .agentId(j)
                                    .build();
                            agencyAgents.add(agencyAgent);
                        }
                        break;
                    case 3:
                        for (long j = 11; j <= 15; j++) {
                            AgencyAgent agencyAgent = AgencyAgent.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .agentId(j)
                                    .build();
                            agencyAgents.add(agencyAgent);
                        }
                        break;
                    case 4:
                        for (long j = 16; j <= 20; j++) {
                            AgencyAgent agencyAgent = AgencyAgent.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .agentId(j)
                                    .build();
                            agencyAgents.add(agencyAgent);
                        }
                        break;
                }
            }
            agencyAgentRepository.saveAll(agencyAgents);

        }
    }

    private void loadAgencyPropertyData() {
        if (agencyPropertyRepository.count() == 0) {
            List<AgencyProperty> agencyProperties = new ArrayList<>();

            for (long i = 1; i <= 4; i++) {
                switch ((int) i) {
                    case 1:
                        for (long j = 1; j <= 67; j = j + 4) {
                            AgencyProperty agencyProperty = AgencyProperty.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .propertyId(j)
                                    .build();
                            agencyProperties.add(agencyProperty);

                        }
                        break;
                    case 2:
                        for (long j = 2; j <= 67; j = j + 4) {
                            AgencyProperty agencyProperty = AgencyProperty.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .propertyId(j)
                                    .build();
                            agencyProperties.add(agencyProperty);
                        }
                        break;
                    case 3:
                        for (long j = 3; j <= 67; j = j + 4) {
                            AgencyProperty agencyProperty = AgencyProperty.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .propertyId(j)
                                    .build();
                            agencyProperties.add(agencyProperty);
                        }
                        break;
                    case 4:
                        for (long j = 4; j <= 67; j = j + 4) {
                            AgencyProperty agencyProperty = AgencyProperty.builder()
                                    .agency(agencyRepository.getReferenceById(i))
                                    .propertyId(j)
                                    .build();
                            agencyProperties.add(agencyProperty);
                        }
                        break;
                }
            }
            agencyPropertyRepository.saveAll(agencyProperties);
        }
    }
}
