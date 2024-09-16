package ubb.graduation24.immopal.property_service.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ubb.graduation24.immopal.property_service.domain.*;
import ubb.graduation24.immopal.property_service.repository.PropertyRepository;
import ubb.graduation24.immopal.property_service.service.IPropertyService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final PropertyRepository propertyRepository;
    private final IPropertyService propertyService;

    @Transactional
    @Override
    public void run(final String... args) throws Exception {
        if (propertyRepository.count() == 0) {
            log.debug("Starting Bootstrap Data...");
            loadAppartmentsData();
            loadVillasData();
            loadCommercialsData();
            loadIndustrialsData();
            loadOfficesData();
            loadCottageData();
        }
    }

    private void loadCottageData() {
        log.debug("Loading Cottage Data...");
        PropertyDetails ctpd1 = PropertyDetails.builder()
                .description("Old Wood cottage in the mountains")
                .carpetArea(640.00)
                .builtUpArea((double) Math.round(640.00 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("3")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1980)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(9)
                .parkingNo(10)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address cta1 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct1 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd1)
                .address(cta1)
                .price(800.00)
                .agentId(1L)
                .build();
        Property saved_ct1 = propertyService.saveProperty(ct1);
        List<PropertyImages> ctpropertyImages1 = new ArrayList<>();
        ctpropertyImages1.add(new PropertyImages(saved_ct1, "/real_estate/cottage/exterior/immo_cotext_0.jpeg"));
        ctpropertyImages1.add(new PropertyImages(saved_ct1, "/real_estate/cottage/bedroom/immo_cotbe_0.jpeg"));
        ctpropertyImages1.add(new PropertyImages(saved_ct1, "/real_estate/cottage/kitchen/immo_cotk_0.jpeg"));
        ctpropertyImages1.add(new PropertyImages(saved_ct1, "/real_estate/cottage/bathroom/immo_cotb_0.jpeg"));
        saved_ct1.setPropertyImages(ctpropertyImages1);
        propertyRepository.save(saved_ct1);

        PropertyDetails ctpd2 = PropertyDetails.builder()
                .description("New Wood cottage in the mountains")
                .carpetArea(840.00)
                .builtUpArea((double) Math.round(840.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("4")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(2023)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(20)
                .parkingNo(20)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address cta2 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct2 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd2)
                .address(cta2)
                .price(1200.00)
                .agentId(6L)
                .build();
        Property saved_ct2 = propertyService.saveProperty(ct2);
        List<PropertyImages> ctpropertyImages2 = new ArrayList<>();
        ctpropertyImages2.add(new PropertyImages(saved_ct2, "/real_estate/cottage/exterior/immo_cotext_1.jpeg"));
        ctpropertyImages2.add(new PropertyImages(saved_ct2, "/real_estate/cottage/bedroom/immo_cotbe_1.jpeg"));
        ctpropertyImages2.add(new PropertyImages(saved_ct2, "/real_estate/cottage/kitchen/immo_cotk_1.jpeg"));
        ctpropertyImages2.add(new PropertyImages(saved_ct2, "/real_estate/cottage/bathroom/immo_cotb_1.jpeg"));
        saved_ct2.setPropertyImages(ctpropertyImages2);
        propertyRepository.save(saved_ct2);

        PropertyDetails ctpd3 = PropertyDetails.builder()
                .description("Wood cottage in the mountains")
                .carpetArea(200.00)
                .builtUpArea((double) Math.round(200.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(1997)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(3)
                .balcony(true)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address cta3 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct3 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd3)
                .address(cta3)
                .price(400.00)
                .agentId(11L)
                .build();
        Property saved_ct3 = propertyService.saveProperty(ct3);
        List<PropertyImages> ctpropertyImages3 = new ArrayList<>();
        ctpropertyImages3.add(new PropertyImages(saved_ct3, "/real_estate/cottage/exterior/immo_cotext_2.jpeg"));
        ctpropertyImages3.add(new PropertyImages(saved_ct3, "/real_estate/cottage/bedroom/immo_cotbe_2.jpeg"));
        ctpropertyImages3.add(new PropertyImages(saved_ct3, "/real_estate/cottage/kitchen/immo_cotk_2.jpeg"));
        ctpropertyImages3.add(new PropertyImages(saved_ct3, "/real_estate/cottage/bathroom/immo_cotb_2.jpeg"));
        saved_ct3.setPropertyImages(ctpropertyImages3);
        propertyRepository.save(saved_ct3);

        PropertyDetails ctpd4 = PropertyDetails.builder()
                .description("Wood cottage in the mountains by the lake")
                .carpetArea(90.00)
                .builtUpArea((double) Math.round(90.00 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("2")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1991)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address cta4 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct4 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd4)
                .address(cta4)
                .price(250.00)
                .agentId(16L)
                .build();
        Property saved_ct4 = propertyService.saveProperty(ct4);
        List<PropertyImages> ctpropertyImages4 = new ArrayList<>();
        ctpropertyImages4.add(new PropertyImages(saved_ct4, "/real_estate/cottage/exterior/immo_cotext_3.jpeg"));
        ctpropertyImages4.add(new PropertyImages(saved_ct4, "/real_estate/cottage/bedroom/immo_cotbe_3.jpeg"));
        ctpropertyImages4.add(new PropertyImages(saved_ct4, "/real_estate/cottage/kitchen/immo_cotk_3.jpeg"));
        ctpropertyImages4.add(new PropertyImages(saved_ct4, "/real_estate/cottage/bathroom/immo_cotb_3.jpeg"));
        saved_ct4.setPropertyImages(ctpropertyImages4);
        propertyRepository.save(saved_ct4);

        PropertyDetails ctpd5 = PropertyDetails.builder()
                .description("Small wood fishing cottage in the mountains by the lake")
                .carpetArea(80.00)
                .builtUpArea((double) Math.round(80.00 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("1")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1970)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address cta5 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct5 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd5)
                .address(cta5)
                .price(100000.00)
                .agentId(1L)
                .build();
        Property saved_ct5 = propertyService.saveProperty(ct5);
        List<PropertyImages> ctpropertyImages5 = new ArrayList<>();
        ctpropertyImages5.add(new PropertyImages(saved_ct5, "/real_estate/cottage/exterior/immo_cotext_4.jpeg"));
        ctpropertyImages5.add(new PropertyImages(saved_ct5, "/real_estate/cottage/bedroom/immo_cotbe_4.jpeg"));
        ctpropertyImages5.add(new PropertyImages(saved_ct5, "/real_estate/cottage/bathroom/immo_cotb_4.jpeg"));
        saved_ct5.setPropertyImages(ctpropertyImages5);
        propertyRepository.save(saved_ct5);

        PropertyDetails ctpd6 = PropertyDetails.builder()
                .description("Small wood cottage in the mountains.")
                .carpetArea(60.00)
                .builtUpArea((double) Math.round(60.00 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("1")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1967)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(1)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address cta6 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct6 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd6)
                .address(cta6)
                .price(76000.00)
                .agentId(6L)
                .build();
        Property saved_ct6 = propertyService.saveProperty(ct6);
        List<PropertyImages> ctpropertyImages6 = new ArrayList<>();
        ctpropertyImages6.add(new PropertyImages(saved_ct6, "/real_estate/cottage/exterior/immo_cotext_5.jpeg"));
        ctpropertyImages6.add(new PropertyImages(saved_ct6, "/real_estate/cottage/bedroom/immo_cotbe_5.jpeg"));
        saved_ct6.setPropertyImages(ctpropertyImages6);
        propertyRepository.save(saved_ct6);

        PropertyDetails ctpd7 = PropertyDetails.builder()
                .description("Small wood cottage in the mountains.")
                .carpetArea(87.00)
                .builtUpArea((double) Math.round(87.00 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("1")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1993)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address cta7 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct7 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd7)
                .address(cta7)
                .price(76000.00)
                .agentId(11L)
                .build();
        Property saved_ct7 = propertyService.saveProperty(ct7);
        List<PropertyImages> ctpropertyImages7 = new ArrayList<>();
        ctpropertyImages7.add(new PropertyImages(saved_ct7, "/real_estate/cottage/exterior/immo_cotext_6.jpeg"));
        ctpropertyImages7.add(new PropertyImages(saved_ct7, "/real_estate/cottage/bedroom/immo_cotbe_6.jpeg"));
        saved_ct7.setPropertyImages(ctpropertyImages7);
        propertyRepository.save(saved_ct7);

        PropertyDetails ctpd8 = PropertyDetails.builder()
                .description("Small wood cottage in the mountains.")
                .carpetArea(87.00)
                .builtUpArea((double) Math.round(87.00 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("1")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1999)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address cta8 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct8 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd8)
                .address(cta8)
                .price(57000.00)
                .agentId(16L)
                .build();
        Property saved_ct8 = propertyService.saveProperty(ct8);
        List<PropertyImages> ctpropertyImages8 = new ArrayList<>();
        ctpropertyImages8.add(new PropertyImages(saved_ct8, "/real_estate/cottage/exterior/immo_cotext_7.jpeg"));
        saved_ct8.setPropertyImages(ctpropertyImages8);
        propertyRepository.save(saved_ct8);

        PropertyDetails ctpd9 = PropertyDetails.builder()
                .description("Small wood cottage in the mountains.")
                .carpetArea(50.00)
                .builtUpArea((double) Math.round(50.00 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("1")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(2007)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address cta9 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ct9 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.CHALET)
                .propertyDetails(ctpd9)
                .address(cta9)
                .price(35000.00)
                .agentId(1L)
                .build();
        Property saved_ct9 = propertyService.saveProperty(ct9);
        List<PropertyImages> ctpropertyImages9 = new ArrayList<>();
        ctpropertyImages9.add(new PropertyImages(saved_ct9, "/real_estate/cottage/exterior/immo_cotext_8.jpeg"));
        saved_ct9.setPropertyImages(ctpropertyImages9);
        propertyRepository.save(saved_ct9);
    }

    private void loadVillasData() {
        log.debug("Loading Villas Data...");
        PropertyDetails vpd1 = PropertyDetails.builder()
                .description("Old Victorian duplex")
                .carpetArea(640.00)
                .builtUpArea((double) Math.round(640.00 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("4")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(1930)
                .bathNo(4)
                .kitchenNo(1)
                .bedroomNo(5)
                .parkingNo(2)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.E)
                .build();
        Address va1 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v1 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd1)
                .address(va1)
                .price(750000.00)
                .agentId(6L)
                .build();
        Property saved_v1 = propertyService.saveProperty(v1);
        List<PropertyImages> vpropertyImages1 = new ArrayList<>();
        vpropertyImages1.add(new PropertyImages(saved_v1, "/real_estate/villa/immo_vil_1.jpeg"));
        vpropertyImages1.add(new PropertyImages(saved_v1, "/real_estate/villa/immo_vil_0.jpeg"));
        saved_v1.setPropertyImages(vpropertyImages1);
        propertyRepository.save(saved_v1);

        PropertyDetails vpd2 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces and swimming pool")
                .carpetArea(2640.00)
                .builtUpArea((double) Math.round(2640.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2020)
                .bathNo(4)
                .kitchenNo(1)
                .bedroomNo(7)
                .parkingNo(2)
                .balcony(false)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.A)
                .build();
        Address va2 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v2 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd2)
                .address(va2)
                .price(1750000.00)
                .agentId(11L)
                .build();
        Property saved_v2 = propertyService.saveProperty(v2);
        List<PropertyImages> vpropertyImages2 = new ArrayList<>();
        vpropertyImages2.add(new PropertyImages(saved_v2, "/real_estate/villa/immo_vil_2.jpeg"));
        saved_v2.setPropertyImages(vpropertyImages2);
        propertyRepository.save(saved_v2);

        PropertyDetails vpd3 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside")
                .carpetArea(1500.00)
                .builtUpArea((double) Math.round(1500.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2021)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(4)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.A)
                .build();
        Address va3 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v3 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd3)
                .address(va3)
                .price(2350000.00)
                .agentId(16L)
                .build();
        Property saved_v3 = propertyService.saveProperty(v3);
        List<PropertyImages> vpropertyImages3 = new ArrayList<>();
        vpropertyImages3.add(new PropertyImages(saved_v3, "/real_estate/villa/immo_vil_3.jpeg"));
        saved_v3.setPropertyImages(vpropertyImages3);
        propertyRepository.save(saved_v3);

        PropertyDetails vpd4 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside. Bonus swimming pool and dock.")
                .carpetArea(2750.00)
                .builtUpArea((double) Math.round(2750.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("4")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2022)
                .bathNo(4)
                .kitchenNo(1)
                .bedroomNo(4)
                .parkingNo(4)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address va4 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v4 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd4)
                .address(va4)
                .price(5670000.00)
                .agentId(1L)
                .build();
        Property saved_v4 = propertyService.saveProperty(v4);
        List<PropertyImages> vpropertyImages4 = new ArrayList<>();
        vpropertyImages4.add(new PropertyImages(saved_v4, "/real_estate/villa/immo_vil_4.jpeg"));
        saved_v4.setPropertyImages(vpropertyImages4);
        propertyRepository.save(saved_v4);

        PropertyDetails vpd5 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside. Bonus swimming pool.")
                .carpetArea(700.00)
                .builtUpArea((double) Math.round(700.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2012)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(4)
                .parkingNo(2)
                .balcony(false)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address va5 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v5 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd5)
                .address(va5)
                .price(2270000.00)
                .agentId(6L)
                .build();
        Property saved_v5 = propertyService.saveProperty(v5);
        List<PropertyImages> vpropertyImages5 = new ArrayList<>();
        vpropertyImages5.add(new PropertyImages(saved_v5, "/real_estate/villa/immo_vil_5.jpeg"));
        saved_v5.setPropertyImages(vpropertyImages5);
        propertyRepository.save(saved_v5);

        PropertyDetails vpd6 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces. Bonus swimming pool.")
                .carpetArea(1200.00)
                .builtUpArea((double) Math.round(1200.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2013)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(4)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va6 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v6 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd6)
                .address(va6)
                .price(1270000.00)
                .agentId(11L)
                .build();
        Property saved_v6 = propertyService.saveProperty(v6);
        List<PropertyImages> vpropertyImages6 = new ArrayList<>();
        vpropertyImages6.add(new PropertyImages(saved_v6, "/real_estate/villa/immo_vil_6.jpeg"));
        saved_v6.setPropertyImages(vpropertyImages6);
        propertyRepository.save(saved_v6);

        PropertyDetails vpd7 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces. Bonus swimming pool.")
                .carpetArea(2300.00)
                .builtUpArea((double) Math.round(2300.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2010)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(6)
                .parkingNo(4)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address va7 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v7 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd7)
                .address(va7)
                .price(278000.00)
                .agentId(16L)
                .build();
        Property saved_v7 = propertyService.saveProperty(v7);
        List<PropertyImages> vpropertyImages7 = new ArrayList<>();
        vpropertyImages7.add(new PropertyImages(saved_v7, "/real_estate/villa/immo_vil_7.jpeg"));
        saved_v7.setPropertyImages(vpropertyImages7);
        propertyRepository.save(saved_v7);

        PropertyDetails vpd8 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside. Bonus swimming pool and own dock.")
                .carpetArea(1850.00)
                .builtUpArea((double) Math.round(1850.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2022)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(5)
                .parkingNo(2)
                .balcony(false)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va8 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v8 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd8)
                .address(va8)
                .price(3474000.00)
                .agentId(2L)
                .build();
        Property saved_v8 = propertyService.saveProperty(v8);
        List<PropertyImages> vpropertyImages8 = new ArrayList<>();
        vpropertyImages8.add(new PropertyImages(saved_v8, "/real_estate/villa/immo_vil_8.jpeg"));
        saved_v8.setPropertyImages(vpropertyImages8);
        propertyRepository.save(saved_v8);

        PropertyDetails vpd9 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside. Bonus infinity pool.")
                .carpetArea(700.00)
                .builtUpArea((double) Math.round(700.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2009)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address va9 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v9 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd9)
                .address(va9)
                .price(670000.00)
                .agentId(7L)
                .build();
        Property saved_v9 = propertyService.saveProperty(v9);
        List<PropertyImages> vpropertyImages9 = new ArrayList<>();
        vpropertyImages9.add(new PropertyImages(saved_v9, "/real_estate/villa/immo_vil_9.jpeg"));
        saved_v9.setPropertyImages(vpropertyImages9);
        propertyRepository.save(saved_v9);

        PropertyDetails vpd10 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside. Bonus swimming pool.")
                .carpetArea(1250.00)
                .builtUpArea((double) Math.round(1250.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2011)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(4)
                .balcony(false)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address va10 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v10 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd10)
                .address(va10)
                .price(740000.00)
                .agentId(12L)
                .build();
        Property saved_v10 = propertyService.saveProperty(v10);
        List<PropertyImages> vpropertyImages10 = new ArrayList<>();
        vpropertyImages10.add(new PropertyImages(saved_v10, "/real_estate/villa/immo_vil_10.jpeg"));
        saved_v10.setPropertyImages(vpropertyImages10);
        propertyRepository.save(saved_v10);

        PropertyDetails vpd11 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the lakeside.")
                .carpetArea(650.00)
                .builtUpArea((double) Math.round(650.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2010)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va11 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v11 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd11)
                .address(va11)
                .price(740000.00)
                .agentId(17L)
                .build();
        Property saved_v11 = propertyService.saveProperty(v11);
        List<PropertyImages> vpropertyImages11 = new ArrayList<>();
        vpropertyImages11.add(new PropertyImages(saved_v11, "/real_estate/villa/immo_vil_11.jpeg"));
        saved_v11.setPropertyImages(vpropertyImages11);
        propertyRepository.save(saved_v11);


        PropertyDetails vpd12 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the seaside.")
                .carpetArea(1375.00)
                .builtUpArea((double) Math.round(1375.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2019)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va12 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v12 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd12)
                .address(va12)
                .price(1340000.00)
                .agentId(2L)
                .build();
        Property saved_v12 = propertyService.saveProperty(v12);
        List<PropertyImages> vpropertyImages12 = new ArrayList<>();
        vpropertyImages12.add(new PropertyImages(saved_v12, "/real_estate/villa/immo_vil_12.jpeg"));
        saved_v12.setPropertyImages(vpropertyImages12);
        propertyRepository.save(saved_v12);

        PropertyDetails vpd13 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the lakeside.")
                .carpetArea(450.00)
                .builtUpArea((double) Math.round(450.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2020)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va13 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v13 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd13)
                .address(va13)
                .price(740000.00)
                .agentId(7L)
                .build();
        Property saved_v13 = propertyService.saveProperty(v13);
        List<PropertyImages> vpropertyImages13 = new ArrayList<>();
        vpropertyImages13.add(new PropertyImages(saved_v13, "/real_estate/villa/immo_vil_13.jpeg"));
        saved_v13.setPropertyImages(vpropertyImages13);
        propertyRepository.save(saved_v13);

        PropertyDetails vpd14 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces in a residential neighborhood.")
                .carpetArea(1250.00)
                .builtUpArea((double) Math.round(1250.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("4")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2014)
                .bathNo(4)
                .kitchenNo(1)
                .bedroomNo(6)
                .parkingNo(4)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va14 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v14 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd14)
                .address(va14)
                .price(740000.00)
                .agentId(12L)
                .build();
        Property saved_v14 = propertyService.saveProperty(v14);
        List<PropertyImages> vpropertyImages14 = new ArrayList<>();
        vpropertyImages14.add(new PropertyImages(saved_v14, "/real_estate/villa/immo_vil_14.jpeg"));
        saved_v14.setPropertyImages(vpropertyImages14);
        propertyRepository.save(saved_v14);

        PropertyDetails vpd15 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the lakeside with the own dock")
                .carpetArea(1650.00)
                .builtUpArea((double) Math.round(1650.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("4")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2020)
                .bathNo(4)
                .kitchenNo(1)
                .bedroomNo(8)
                .parkingNo(4)
                .balcony(true)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address va15 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v15 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd15)
                .address(va15)
                .price(740000.00)
                .agentId(17L)
                .build();
        Property saved_v15 = propertyService.saveProperty(v15);
        List<PropertyImages> vpropertyImages15 = new ArrayList<>();
        vpropertyImages15.add(new PropertyImages(saved_v15, "/real_estate/villa/immo_vil_15.jpeg"));
        saved_v15.setPropertyImages(vpropertyImages15);
        propertyRepository.save(saved_v15);


        PropertyDetails vpd16 = PropertyDetails.builder()
                .description("Modern architecture villa with large spaces on the lakeside.")
                .carpetArea(375.00)
                .builtUpArea((double) Math.round(375.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("2")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2016)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(1)
                .balcony(true)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address va16 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property v16 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.VILLA)
                .propertyDetails(vpd16)
                .address(va16)
                .price(340000.00)
                .agentId(2L)
                .build();
        Property saved_v16 = propertyService.saveProperty(v16);
        List<PropertyImages> vpropertyImages16 = new ArrayList<>();
        vpropertyImages16.add(new PropertyImages(saved_v16, "/real_estate/villa/immo_vil_16.jpeg"));
        saved_v16.setPropertyImages(vpropertyImages16);
        propertyRepository.save(saved_v16);
    }

    private void loadOfficesData() {
        log.debug("Loading Offices Data...");
        PropertyDetails opd1 = PropertyDetails.builder()
                .description("Modern office for modern people")
                .carpetArea(650.00)
                .builtUpArea((double) Math.round(650.00 * 1.25))
                .comfortType(ComfortType.BASIC)
                .floor("7th")
                .structureType(StructureType.STEEL)
                .yearOfConstruction(2005)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(5)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address oa1 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property o1 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.OFFICE)
                .propertyDetails(opd1)
                .address(oa1)
                .price(7500.00)
                .agentId(7L)
                .build();
        Property saved_o1 = propertyService.saveProperty(o1);
        List<PropertyImages> opropertyImages1 = new ArrayList<>();
        opropertyImages1.add(new PropertyImages(saved_o1, "/real_estate/office/immo_off_0.jpeg"));
        opropertyImages1.add(new PropertyImages(saved_o1, "/real_estate/office/immo_off_1.jpeg"));
        opropertyImages1.add(new PropertyImages(saved_o1, "/real_estate/office/immo_off_2.jpeg"));
        opropertyImages1.add(new PropertyImages(saved_o1, "/real_estate/office/immo_off_3.jpeg"));
        saved_o1.setPropertyImages(opropertyImages1);
        propertyRepository.save(saved_o1);

        PropertyDetails opd2 = PropertyDetails.builder()
                .description("Modern office for modern people")
                .carpetArea(650.00)
                .builtUpArea((double) Math.round(650.00 * 1.25))
                .comfortType(ComfortType.BASIC)
                .floor("7th")
                .structureType(StructureType.STEEL)
                .yearOfConstruction(2005)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(5)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address oa2 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property o2 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.OFFICE)
                .propertyDetails(opd2)
                .address(oa2)
                .price(7500.00)
                .agentId(12L)
                .build();
        Property saved_o2 = propertyService.saveProperty(o2);
        List<PropertyImages> opropertyImages2 = new ArrayList<>();
        opropertyImages2.add(new PropertyImages(saved_o2, "/real_estate/office/immo_off_4.jpeg"));
        opropertyImages2.add(new PropertyImages(saved_o2, "/real_estate/office/immo_off_5.jpeg"));
        opropertyImages2.add(new PropertyImages(saved_o2, "/real_estate/office/immo_off_6.jpeg"));
        opropertyImages2.add(new PropertyImages(saved_o2, "/real_estate/office/immo_off_7.jpeg"));
        saved_o2.setPropertyImages(opropertyImages2);
        propertyRepository.save(saved_o2);

        PropertyDetails opd3 = PropertyDetails.builder()
                .description("Modern office for modern people")
                .carpetArea(650.00)
                .builtUpArea((double) Math.round(650.00 * 1.25))
                .comfortType(ComfortType.BASIC)
                .floor("7th")
                .structureType(StructureType.STEEL)
                .yearOfConstruction(2005)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(5)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address oa3 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property o3 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.OFFICE)
                .propertyDetails(opd3)
                .address(oa3)
                .price(7500.00)
                .agentId(17L)
                .build();
        Property saved_o3 = propertyService.saveProperty(o3);
        List<PropertyImages> opropertyImages3 = new ArrayList<>();
        opropertyImages3.add(new PropertyImages(saved_o3, "/real_estate/office/immo_off_8.jpeg"));
        opropertyImages3.add(new PropertyImages(saved_o3, "/real_estate/office/immo_off_9.jpeg"));
        opropertyImages3.add(new PropertyImages(saved_o3, "/real_estate/office/immo_off_10.jpeg"));
        opropertyImages3.add(new PropertyImages(saved_o3, "/real_estate/office/immo_off_11.jpeg"));
        saved_o3.setPropertyImages(opropertyImages3);
        propertyRepository.save(saved_o3);

        PropertyDetails opd4 = PropertyDetails.builder()
                .description("Modern office for modern people")
                .carpetArea(650.00)
                .builtUpArea((double) Math.round(650.00 * 1.25))
                .comfortType(ComfortType.BASIC)
                .floor("7th")
                .structureType(StructureType.STEEL)
                .yearOfConstruction(2005)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(5)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address oa4 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property o4 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.OFFICE)
                .propertyDetails(opd4)
                .address(oa4)
                .price(7500.00)
                .agentId(2L)
                .build();
        Property saved_o4 = propertyService.saveProperty(o4);
        List<PropertyImages> opropertyImages4 = new ArrayList<>();
        opropertyImages4.add(new PropertyImages(saved_o4, "/real_estate/office/immo_off_12.jpeg"));
        opropertyImages4.add(new PropertyImages(saved_o4, "/real_estate/office/immo_off_13.jpeg"));
        opropertyImages4.add(new PropertyImages(saved_o4, "/real_estate/office/immo_off_14.jpeg"));
        opropertyImages4.add(new PropertyImages(saved_o4, "/real_estate/office/immo_off_15.jpeg"));
        opropertyImages4.add(new PropertyImages(saved_o4, "/real_estate/office/immo_off_16.jpeg"));
        saved_o4.setPropertyImages(opropertyImages4);
        propertyRepository.save(saved_o4);
    }

    private void loadIndustrialsData() {
        log.debug("Loading Industrials Data...");
        PropertyDetails ipd1 = PropertyDetails.builder()
                .description("Old factory for modern days")
                .carpetArea(20000.00)
                .builtUpArea((double) Math.round(20000.00 * 1.15))
                .comfortType(ComfortType.BASIC)
                .floor("Ground Floor")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(1974)
                .bathNo(10)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(500)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ia1 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property i1 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.INDUSTRIAL)
                .propertyDetails(ipd1)
                .address(ia1)
                .price(2000000.00)
                .agentId(7L)
                .build();
        Property saved_i1 = propertyService.saveProperty(i1);
        List<PropertyImages> ipropertyImages1 = new ArrayList<>();
        ipropertyImages1.add(new PropertyImages(saved_i1, "/real_estate/industrial/immo_ind_0.jpeg"));
        ipropertyImages1.add(new PropertyImages(saved_i1, "/real_estate/industrial/immo_ind_1.jpeg"));
        ipropertyImages1.add(new PropertyImages(saved_i1, "/real_estate/industrial/immo_ind_2.jpeg"));
        ipropertyImages1.add(new PropertyImages(saved_i1, "/real_estate/industrial/immo_ind_3.jpeg"));
        saved_i1.setPropertyImages(ipropertyImages1);
        propertyRepository.save(saved_i1);

        PropertyDetails ipd2 = PropertyDetails.builder()
                .description("Old factory for sale")
                .carpetArea(60000.00)
                .builtUpArea((double) Math.round(60000.00 * 1.15))
                .comfortType(ComfortType.BASIC)
                .floor("Ground Floor")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(1977)
                .bathNo(10)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(250)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ia2 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property i2 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.INDUSTRIAL)
                .propertyDetails(ipd2)
                .address(ia2)
                .price(5500000.00)
                .agentId(12L)
                .build();
        Property saved_i2 = propertyService.saveProperty(i2);
        List<PropertyImages> ipropertyImages2 = new ArrayList<>();
        ipropertyImages2.add(new PropertyImages(saved_i2, "/real_estate/industrial/immo_ind_4.jpeg"));
        ipropertyImages2.add(new PropertyImages(saved_i2, "/real_estate/industrial/immo_ind_5.jpeg"));
        ipropertyImages2.add(new PropertyImages(saved_i2, "/real_estate/industrial/immo_ind_6.jpeg"));
        ipropertyImages2.add(new PropertyImages(saved_i2, "/real_estate/industrial/immo_ind_7.jpeg"));
        saved_i2.setPropertyImages(ipropertyImages2);
        propertyRepository.save(saved_i2);

        PropertyDetails ipd3 = PropertyDetails.builder()
                .description("Old factory for sale")
                .carpetArea(47000.00)
                .builtUpArea((double) Math.round(47000.00 * 1.15))
                .comfortType(ComfortType.BASIC)
                .floor("Ground Floor")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(1988)
                .bathNo(4)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(50)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ia3 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property i3 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.INDUSTRIAL)
                .propertyDetails(ipd3)
                .address(ia3)
                .price(2500000.00)
                .agentId(17L)
                .build();
        Property saved_i3 = propertyService.saveProperty(i3);
        List<PropertyImages> ipropertyImages3 = new ArrayList<>();
        ipropertyImages3.add(new PropertyImages(saved_i3, "/real_estate/industrial/immo_ind_8.jpeg"));
        ipropertyImages3.add(new PropertyImages(saved_i3, "/real_estate/industrial/immo_ind_9.jpeg"));
        ipropertyImages3.add(new PropertyImages(saved_i3, "/real_estate/industrial/immo_ind_10.jpeg"));
        ipropertyImages3.add(new PropertyImages(saved_i3, "/real_estate/industrial/immo_ind_11.jpeg"));
        saved_i3.setPropertyImages(ipropertyImages3);
        propertyRepository.save(saved_i3);

        PropertyDetails ipd4 = PropertyDetails.builder()
                .description("Old factory for sale")
                .carpetArea(27000.00)
                .builtUpArea((double) Math.round(27000.00 * 1.15))
                .comfortType(ComfortType.BASIC)
                .floor("Ground Floor")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(1995)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(20)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ia4 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property i4 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.INDUSTRIAL)
                .propertyDetails(ipd4)
                .address(ia4)
                .price(2500000.00)
                .agentId(3L)
                .build();
        Property saved_i4 = propertyService.saveProperty(i4);
        List<PropertyImages> ipropertyImages4 = new ArrayList<>();
        ipropertyImages4.add(new PropertyImages(saved_i4, "/real_estate/industrial/immo_ind_12.jpeg"));
        ipropertyImages4.add(new PropertyImages(saved_i4, "/real_estate/industrial/immo_ind_13.jpeg"));
        ipropertyImages4.add(new PropertyImages(saved_i4, "/real_estate/industrial/immo_ind_14.jpeg"));
        ipropertyImages4.add(new PropertyImages(saved_i4, "/real_estate/industrial/immo_ind_15.jpeg"));
        saved_i4.setPropertyImages(ipropertyImages4);
        propertyRepository.save(saved_i4);

        PropertyDetails ipd5 = PropertyDetails.builder()
                .description("Old factory for sale")
                .carpetArea(67000.00)
                .builtUpArea((double) Math.round(67000.00 * 1.15))
                .comfortType(ComfortType.BASIC)
                .floor("Ground Floor")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(1967)
                .bathNo(3)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(100)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ia5 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property i5 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.INDUSTRIAL)
                .propertyDetails(ipd5)
                .address(ia5)
                .price(3750000.00)
                .agentId(8L)
                .build();
        Property saved_i5 = propertyService.saveProperty(i5);
        List<PropertyImages> ipropertyImages5 = new ArrayList<>();
        ipropertyImages5.add(new PropertyImages(saved_i5, "/real_estate/industrial/immo_ind_16.jpeg"));
        ipropertyImages5.add(new PropertyImages(saved_i5, "/real_estate/industrial/immo_ind_17.jpeg"));
        ipropertyImages5.add(new PropertyImages(saved_i5, "/real_estate/industrial/immo_ind_18.jpeg"));
        ipropertyImages5.add(new PropertyImages(saved_i5, "/real_estate/industrial/immo_ind_19.jpeg"));
        saved_i5.setPropertyImages(ipropertyImages5);
        propertyRepository.save(saved_i5);
    }

    private void loadCommercialsData() {
        log.debug("Loading commercials data...");
        PropertyDetails cpd1 = PropertyDetails.builder()
                .description("Showroom for your art")
                .carpetArea(200.00)
                .builtUpArea((double) Math.round(200.00 * 1.50))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("GF")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(2000)
                .bathNo(1)
                .kitchenNo(0)
                .bedroomNo(2)
                .parkingNo(5)
                .balcony(false)
                .terrace(true)
                .swimmingPool(true)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ca1 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property c1 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd1)
                .address(ca1)
                .price(10000.00)
                .agentId(13L)
                .build();
        Property saved_c1 = propertyService.saveProperty(c1);
        List<PropertyImages> cpropertyImages1 = new ArrayList<>();
        cpropertyImages1.add(new PropertyImages(saved_c1, "/real_estate/commercial/immo_co_0.jpeg"));
        cpropertyImages1.add(new PropertyImages(saved_c1, "/real_estate/commercial/immo_co_3.jpeg"));
        saved_c1.setPropertyImages(cpropertyImages1);
        propertyRepository.save(saved_c1);

        PropertyDetails cpd2 = PropertyDetails.builder()
                .description("Open space party or exhibition place in the middle of nature")
                .carpetArea(5000.00)
                .builtUpArea((double) Math.round(400.00 * 1.20))
                .comfortType(ComfortType.BASIC)
                .floor("GF")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(2022)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(50)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.F)
                .build();
        Address ca2 = Address.builder()
                .country("United States")
                .state("Montana")
                .city("")
                .neighborhood("")
                .street("")
                .streetNo("")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property c2 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd2)
                .address(ca2)
                .price(1000.00)
                .agentId(18L)
                .build();
        Property saved_c2 = propertyService.saveProperty(c2);
        List<PropertyImages> cpropertyImages2 = new ArrayList<>();
        cpropertyImages2.add(new PropertyImages(saved_c2, "/real_estate/commercial/immo_co_1.jpeg"));
        saved_c2.setPropertyImages(cpropertyImages2);
        propertyRepository.save(saved_c2);

        PropertyDetails cpd3 = PropertyDetails.builder()
                .description("Hot cozy coffee place in the heart of the town")
                .carpetArea(250.00)
                .builtUpArea((double) Math.round(250.00 * 1.20))
                .comfortType(ComfortType.STANDARD)
                .floor("GF")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(0)
                .balcony(false)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address ca3 = Address.builder()
                .country("United States")
                .state("Texas")
                .city("Dallas")
                .neighborhood("Downtown")
                .street("Commerce St")
                .streetNo("20")
                .latitude(32.780681)
                .longitude(-96.796761)
                .build();
        Property c3 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd3)
                .address(ca3)
                .price(7500.00)
                .agentId(3L)
                .build();
        Property saved_c3 = propertyService.saveProperty(c3);
        List<PropertyImages> cpropertyImages3 = new ArrayList<>();
        cpropertyImages3.add(new PropertyImages(saved_c3, "/real_estate/commercial/immo_co_2.jpeg"));
        cpropertyImages3.add(new PropertyImages(saved_c3, "/real_estate/commercial/immo_co_28.jpeg"));
        saved_c3.setPropertyImages(cpropertyImages3);
        propertyRepository.save(saved_c3);

        PropertyDetails cpd4 = PropertyDetails.builder()
                .description("Magic coffee place")
                .carpetArea(450.00)
                .builtUpArea((double) Math.round(450.00 * 1.20))
                .comfortType(ComfortType.STANDARD)
                .floor("GF")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(2003)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(3)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address ca4 = Address.builder()
                .country("United States")
                .state("California")
                .city("Los Angeles")
                .neighborhood("Downtown")
                .street("S Olive St")
                .streetNo("17")
                .latitude(34.053489)
                .longitude(-118.248783)
                .build();
        Property c4 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd4)
                .address(ca4)
                .price(5500.00)
                .agentId(8L)
                .build();
        Property saved_c4 = propertyService.saveProperty(c4);
        List<PropertyImages> cpropertyImages4 = new ArrayList<>();
        cpropertyImages4.add(new PropertyImages(saved_c4, "/real_estate/commercial/immo_co_9.jpeg"));
        cpropertyImages4.add(new PropertyImages(saved_c4, "/real_estate/commercial/immo_co_23.jpeg"));
        saved_c4.setPropertyImages(cpropertyImages4);
        propertyRepository.save(saved_c4);

        PropertyDetails cpd5 = PropertyDetails.builder()
                .description("Coffee place for the offices in the building")
                .carpetArea(625.00)
                .builtUpArea((double) Math.round(625.00 * 1.20))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("20th")
                .structureType(StructureType.COMPOSITE)
                .yearOfConstruction(2020)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(3)
                .parkingNo(0)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.A)
                .build();
        Address ca5 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 56th St")
                .streetNo("6")
                .latitude(40.763922)
                .longitude(-73.978525)
                .build();
        Property c5 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd5)
                .address(ca5)
                .price(15000.00)
                .agentId(13L)
                .build();
        Property saved_c5 = propertyService.saveProperty(c5);
        List<PropertyImages> cpropertyImages5 = new ArrayList<>();
        cpropertyImages5.add(new PropertyImages(saved_c5, "/real_estate/commercial/immo_co_4.jpeg"));
        cpropertyImages5.add(new PropertyImages(saved_c5, "/real_estate/commercial/immo_co_10.jpeg"));
        cpropertyImages5.add(new PropertyImages(saved_c5, "/real_estate/commercial/immo_co_27.jpeg"));
        cpropertyImages5.add(new PropertyImages(saved_c5, "/real_estate/commercial/immo_co_29.jpeg"));
        saved_c5.setPropertyImages(cpropertyImages5);
        propertyRepository.save(saved_c5);

        PropertyDetails cpd6 = PropertyDetails.builder()
                .description("Modern dancing studio or fashion studio")
                .carpetArea(375.00)
                .builtUpArea((double) Math.round(375.00 * 1.20))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("Ground Floor")
                .structureType(StructureType.STEEL)
                .yearOfConstruction(2021)
                .bathNo(1)
                .kitchenNo(0)
                .bedroomNo(2)
                .parkingNo(4)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.B)
                .build();
        Address ca6 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Ridgewood")
                .street("Madison St")
                .streetNo("10")
                .latitude(40.702861)
                .longitude(-73.905106)
                .build();
        Property c6 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd6)
                .address(ca6)
                .price(2500.00)
                .agentId(18L)
                .build();
        Property saved_c6 = propertyService.saveProperty(c6);
        List<PropertyImages> cpropertyImages6 = new ArrayList<>();
        cpropertyImages6.add(new PropertyImages(saved_c6, "/real_estate/commercial/immo_co_5.jpeg"));
        cpropertyImages6.add(new PropertyImages(saved_c6, "/real_estate/commercial/immo_co_12.jpeg"));
        cpropertyImages6.add(new PropertyImages(saved_c6, "/real_estate/commercial/immo_co_17.jpeg"));
        saved_c6.setPropertyImages(cpropertyImages6);
        propertyRepository.save(saved_c6);

        PropertyDetails cpd7 = PropertyDetails.builder()
                .description("Beloved neighborhood bakery nestled in the heart of a vibrant community")
                .carpetArea(150.00)
                .builtUpArea((double) Math.round(150.00 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("Ground Floor")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(1975)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address ca7 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Queens Village")
                .street("102nd Ave")
                .streetNo("102")
                .latitude(40.712975)
                .longitude(-73.746386)
                .build();
        Property c7 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd7)
                .address(ca7)
                .price(185000.00)
                .agentId(3L)
                .build();
        Property saved_c7 = propertyService.saveProperty(c7);
        List<PropertyImages> cpropertyImages7 = new ArrayList<>();
        cpropertyImages7.add(new PropertyImages(saved_c7, "/real_estate/commercial/immo_co_6.jpeg"));
        cpropertyImages7.add(new PropertyImages(saved_c7, "/real_estate/commercial/immo_co_7.jpeg"));
        cpropertyImages7.add(new PropertyImages(saved_c7, "/real_estate/commercial/immo_co_15.jpeg"));
        saved_c7.setPropertyImages(cpropertyImages7);
        propertyRepository.save(saved_c7);

        PropertyDetails cpd8 = PropertyDetails.builder()
                .description("Beloved neighborhood bakery nestled in the heart of a vibrant community")
                .carpetArea(220.00)
                .builtUpArea((double) Math.round(220.00 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("Ground Floor")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(1967)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address ca8 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Queens Village")
                .street("107nd Ave")
                .streetNo("102")
                .latitude(40.712975)
                .longitude(-73.746386)
                .build();
        Property c8 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd8)
                .address(ca8)
                .price(192000.00)
                .agentId(8L)
                .build();
        Property saved_c8 = propertyService.saveProperty(c8);
        List<PropertyImages> cpropertyImages8 = new ArrayList<>();
        cpropertyImages8.add(new PropertyImages(saved_c8, "/real_estate/commercial/immo_co_13.jpeg"));
        cpropertyImages8.add(new PropertyImages(saved_c8, "/real_estate/commercial/immo_co_21.jpeg"));
        cpropertyImages8.add(new PropertyImages(saved_c8, "/real_estate/commercial/immo_co_18.jpeg"));
        saved_c8.setPropertyImages(cpropertyImages8);
        propertyRepository.save(saved_c8);

        PropertyDetails cpd9 = PropertyDetails.builder()
                .description("Modern open space studio")
                .carpetArea(340.00)
                .builtUpArea((double) Math.round(340.00 * 1.15))
                .comfortType(ComfortType.BASIC)
                .floor("5th")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(1998)
                .bathNo(1)
                .kitchenNo(0)
                .bedroomNo(1)
                .parkingNo(0)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address ca9 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("56nd Ave")
                .streetNo("47")
                .latitude(40.712975)
                .longitude(-73.746386)
                .build();
        Property c9 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd9)
                .address(ca9)
                .price(575000.00)
                .agentId(13L)
                .build();
        Property saved_c9 = propertyService.saveProperty(c9);
        List<PropertyImages> cpropertyImages9 = new ArrayList<>();
        cpropertyImages9.add(new PropertyImages(saved_c9, "/real_estate/commercial/immo_co_8.jpeg"));
        cpropertyImages9.add(new PropertyImages(saved_c9, "/real_estate/commercial/immo_co_11.jpeg"));
        cpropertyImages9.add(new PropertyImages(saved_c9, "/real_estate/commercial/immo_co_22.jpeg"));
        saved_c9.setPropertyImages(cpropertyImages9);
        propertyRepository.save(saved_c9);

        PropertyDetails cpd10 = PropertyDetails.builder()
                .description("Showroom at ground floor")
                .carpetArea(450.00)
                .builtUpArea((double) Math.round(450.00 * 1.15))
                .comfortType(ComfortType.STANDARD)
                .floor("Ground floor")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2017)
                .bathNo(1)
                .kitchenNo(0)
                .bedroomNo(1)
                .parkingNo(5)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.C)
                .build();
        Address ca10 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("25nd Ave")
                .streetNo("4")
                .latitude(40.712975)
                .longitude(-73.746386)
                .build();
        Property c10 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.COMMERCIAL)
                .propertyDetails(cpd10)
                .address(ca10)
                .price(758000.00)
                .agentId(18L)
                .build();
        Property saved_c10 = propertyService.saveProperty(c10);
        List<PropertyImages> cpropertyImages10 = new ArrayList<>();
        cpropertyImages10.add(new PropertyImages(saved_c10, "/real_estate/commercial/immo_co_16.jpeg"));
        cpropertyImages10.add(new PropertyImages(saved_c10, "/real_estate/commercial/immo_co_20.jpeg"));
        cpropertyImages10.add(new PropertyImages(saved_c10, "/real_estate/commercial/immo_co_25.jpeg"));
        cpropertyImages10.add(new PropertyImages(saved_c10, "/real_estate/commercial/immo_co_26.jpeg"));
        saved_c10.setPropertyImages(cpropertyImages10);
        propertyRepository.save(saved_c10);

    }

    private void loadAppartmentsData() {
        log.debug("Loading appartments data...");
        PropertyDetails pd1 = PropertyDetails.builder()
                .description("Basic 1-bedroom apartment in Bronx")
                .carpetArea(48.50)
                .builtUpArea((double) Math.round(48.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("2nd")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(2000)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.E)
                .build();
        Address a1 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap1 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd1)
                .address(a1)
                .price(120000.00)
                .agentId(3L)
                .build();
        Property saved_ap1 = propertyService.saveProperty(ap1);
        List<PropertyImages> propertyImages1 = new ArrayList<>();
        propertyImages1.add(new PropertyImages(saved_ap1, "/real_estate/apartment/bedroom/immo_abed_0.jpeg"));
        propertyImages1.add(new PropertyImages(saved_ap1, "/real_estate/apartment/bathroom/immo_ab_0.jpeg"));
        propertyImages1.add(new PropertyImages(saved_ap1, "/real_estate/apartment/kitchen/immo_ak_0.jpeg"));
        saved_ap1.setPropertyImages(propertyImages1);
        propertyRepository.save(saved_ap1);

        PropertyDetails pd2 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(60.75)
                .builtUpArea((double) Math.round(60.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a2 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap2 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd2)
                .address(a2)
                .price(134000.00)
                .agentId(8L)
                .build();
        Property saved_ap2 = propertyService.saveProperty(ap2);
        List<PropertyImages> propertyImages2 = new ArrayList<>();
        propertyImages2.add(new PropertyImages(saved_ap2, "/real_estate/apartment/bedroom/immo_abed_1.jpeg"));
        propertyImages2.add(new PropertyImages(saved_ap2, "/real_estate/apartment/bathroom/immo_ab_1.jpeg"));
        propertyImages2.add(new PropertyImages(saved_ap2, "/real_estate/apartment/kitchen/immo_ak_1.jpeg"));
        saved_ap2.setPropertyImages(propertyImages2);
        propertyRepository.save(saved_ap2);

        PropertyDetails pd3 = PropertyDetails.builder()
                .description("Basic 2-bedroom apartment in Bronx")
                .carpetArea(45.50)
                .builtUpArea((double) Math.round(45.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("3rd")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a3 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap3 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd3)
                .address(a3)
                .price(245000.00)
                .agentId(13L)
                .build();
        Property saved_ap3 = propertyService.saveProperty(ap3);
        List<PropertyImages> propertyImages3 = new ArrayList<>();
        propertyImages3.add(new PropertyImages(saved_ap3, "/real_estate/apartment/bedroom/immo_abed_2.jpeg"));
        propertyImages3.add(new PropertyImages(saved_ap3, "/real_estate/apartment/bathroom/immo_ab_2.jpeg"));
        propertyImages3.add(new PropertyImages(saved_ap3, "/real_estate/apartment/kitchen/immo_ak_2.jpeg"));
        saved_ap3.setPropertyImages(propertyImages3);
        propertyRepository.save(saved_ap3);

        PropertyDetails pd4 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(50.75)
                .builtUpArea((double) Math.round(50.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a4 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap4 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd4)
                .address(a4)
                .price(136000.00)
                .agentId(19L)
                .build();
        Property saved_ap4 = propertyService.saveProperty(ap4);
        List<PropertyImages> propertyImages4 = new ArrayList<>();
        propertyImages4.add(new PropertyImages(saved_ap4, "/real_estate/apartment/bedroom/immo_abed_3.jpeg"));
        propertyImages4.add(new PropertyImages(saved_ap4, "/real_estate/apartment/bathroom/immo_ab_3.jpeg"));
        propertyImages4.add(new PropertyImages(saved_ap4, "/real_estate/apartment/kitchen/immo_ak_3.jpeg"));
        saved_ap4.setPropertyImages(propertyImages4);
        propertyRepository.save(saved_ap4);

        PropertyDetails pd5 = PropertyDetails.builder()
                .description("Basic 1-bedroom apartment in Bronx")
                .carpetArea(38.50)
                .builtUpArea((double) Math.round(38.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("2nd")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(2000)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.E)
                .build();
        Address a5 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap5 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd5)
                .address(a5)
                .price(120000.00)
                .agentId(4L)
                .build();
        Property saved_ap5 = propertyService.saveProperty(ap5);
        List<PropertyImages> propertyImages5 = new ArrayList<>();
        propertyImages5.add(new PropertyImages(saved_ap5, "/real_estate/apartment/bedroom/immo_abed_4.jpeg"));
        propertyImages5.add(new PropertyImages(saved_ap5, "/real_estate/apartment/bathroom/immo_ab_4.jpeg"));
        propertyImages5.add(new PropertyImages(saved_ap5, "/real_estate/apartment/kitchen/immo_ak_4.jpeg"));
        saved_ap5.setPropertyImages(propertyImages5);
        propertyRepository.save(saved_ap5);

        PropertyDetails pd6 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(40.75)
                .builtUpArea((double) Math.round(40.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a6 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap6 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd6)
                .address(a6)
                .price(134000.00)
                .agentId(9L)
                .build();
        Property saved_ap6 = propertyService.saveProperty(ap6);
        List<PropertyImages> propertyImages6 = new ArrayList<>();
        propertyImages6.add(new PropertyImages(saved_ap6, "/real_estate/apartment/bedroom/immo_abed_5.jpeg"));
        propertyImages6.add(new PropertyImages(saved_ap6, "/real_estate/apartment/bathroom/immo_ab_5.jpeg"));
        propertyImages6.add(new PropertyImages(saved_ap6, "/real_estate/apartment/kitchen/immo_ak_5.jpeg"));
        saved_ap6.setPropertyImages(propertyImages6);
        propertyRepository.save(saved_ap6);

        PropertyDetails pd7 = PropertyDetails.builder()
                .description("Basic 2-bedroom apartment in Bronx")
                .carpetArea(38.50)
                .builtUpArea((double) Math.round(38.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("3rd")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a7 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap7 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd7)
                .address(a7)
                .price(245000.00)
                .agentId(14L)
                .build();
        Property saved_ap7 = propertyService.saveProperty(ap7);
        List<PropertyImages> propertyImages7 = new ArrayList<>();
        propertyImages7.add(new PropertyImages(saved_ap7, "/real_estate/apartment/bedroom/immo_abed_6.jpeg"));
        propertyImages7.add(new PropertyImages(saved_ap7, "/real_estate/apartment/bathroom/immo_ab_6.jpeg"));
        propertyImages7.add(new PropertyImages(saved_ap7, "/real_estate/apartment/kitchen/immo_ak_6.jpeg"));
        saved_ap7.setPropertyImages(propertyImages7);
        propertyRepository.save(saved_ap7);

        PropertyDetails pd8 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(50.75)
                .builtUpArea((double) Math.round(50.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a8 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap8 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd8)
                .address(a8)
                .price(136000.00)
                .agentId(19L)
                .build();
        Property saved_ap8 = propertyService.saveProperty(ap8);
        List<PropertyImages> propertyImages8 = new ArrayList<>();
        propertyImages8.add(new PropertyImages(saved_ap8, "/real_estate/apartment/bedroom/immo_abed_7.jpeg"));
        propertyImages8.add(new PropertyImages(saved_ap8, "/real_estate/apartment/bathroom/immo_ab_7.jpeg"));
        propertyImages8.add(new PropertyImages(saved_ap8, "/real_estate/apartment/kitchen/immo_ak_7.jpeg"));
        saved_ap8.setPropertyImages(propertyImages8);
        propertyRepository.save(saved_ap8);

        PropertyDetails pd9 = PropertyDetails.builder()
                .description("Basic 1-bedroom apartment in Bronx")
                .carpetArea(48.50)
                .builtUpArea((double) Math.round(48.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("2nd")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(2000)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.E)
                .build();
        Address a9 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap9 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd9)
                .address(a9)
                .price(120000.00)
                .agentId(4L)
                .build();
        Property saved_ap9 = propertyService.saveProperty(ap9);
        List<PropertyImages> propertyImages9 = new ArrayList<>();
        propertyImages9.add(new PropertyImages(saved_ap9, "/real_estate/apartment/bedroom/immo_abed_8.jpeg"));
        propertyImages9.add(new PropertyImages(saved_ap9, "/real_estate/apartment/bathroom/immo_ab_8.jpeg"));
        propertyImages9.add(new PropertyImages(saved_ap9, "/real_estate/apartment/kitchen/immo_ak_8.jpeg"));
        saved_ap9.setPropertyImages(propertyImages9);
        propertyRepository.save(saved_ap9);

        PropertyDetails pd10 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(60.75)
                .builtUpArea((double) Math.round(60.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a10 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap10 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd10)
                .address(a10)
                .price(134000.00)
                .agentId(9L)
                .build();
        Property saved_ap10 = propertyService.saveProperty(ap10);
        List<PropertyImages> propertyImages10 = new ArrayList<>();
        propertyImages10.add(new PropertyImages(saved_ap10, "/real_estate/apartment/bedroom/immo_abed_9.jpeg"));
        propertyImages10.add(new PropertyImages(saved_ap10, "/real_estate/apartment/bathroom/immo_ab_9.jpeg"));
        propertyImages10.add(new PropertyImages(saved_ap10, "/real_estate/apartment/kitchen/immo_ak_9.jpeg"));
        saved_ap10.setPropertyImages(propertyImages10);
        propertyRepository.save(saved_ap10);

        PropertyDetails pd11 = PropertyDetails.builder()
                .description("Basic 2-bedroom apartment in Bronx")
                .carpetArea(38.50)
                .builtUpArea((double) Math.round(38.50 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("3rd")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a11 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap11 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd11)
                .address(a11)
                .price(245000.00)
                .agentId(14L)
                .build();
        Property saved_ap11 = propertyService.saveProperty(ap11);
        List<PropertyImages> propertyImages11 = new ArrayList<>();
        propertyImages11.add(new PropertyImages(saved_ap11, "/real_estate/apartment/bedroom/immo_abed_10.jpeg"));
        propertyImages11.add(new PropertyImages(saved_ap11, "/real_estate/apartment/bathroom/immo_ab_10.jpeg"));
        propertyImages11.add(new PropertyImages(saved_ap11, "/real_estate/apartment/kitchen/immo_ak_10.jpeg"));
        saved_ap11.setPropertyImages(propertyImages11);
        propertyRepository.save(saved_ap11);

        PropertyDetails pd12 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(50.75)
                .builtUpArea((double) Math.round(50.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a12 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap12 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd12)
                .address(a12)
                .price(700.00)
                .agentId(19L)
                .build();
        Property saved_ap12 = propertyService.saveProperty(ap12);
        List<PropertyImages> propertyImages12 = new ArrayList<>();
        propertyImages12.add(new PropertyImages(saved_ap12, "/real_estate/apartment/bedroom/immo_abed_11.jpeg"));
        propertyImages12.add(new PropertyImages(saved_ap12, "/real_estate/apartment/bathroom/immo_ab_11.jpeg"));
        propertyImages12.add(new PropertyImages(saved_ap12, "/real_estate/apartment/kitchen/immo_ak_11.jpeg"));
        saved_ap12.setPropertyImages(propertyImages12);
        propertyRepository.save(saved_ap12);

        PropertyDetails pd13 = PropertyDetails.builder()
                .description("Basic 1-bedroom apartment in Bronx")
                .carpetArea(48.50)
                .builtUpArea((double) Math.round(48.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("2nd")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(2000)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.E)
                .build();
        Address a13 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap13 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd13)
                .address(a13)
                .price(650.00)
                .agentId(4L)
                .build();
        Property saved_ap13 = propertyService.saveProperty(ap13);
        List<PropertyImages> propertyImages13 = new ArrayList<>();
        propertyImages13.add(new PropertyImages(saved_ap13, "/real_estate/apartment/bedroom/immo_abed_12.jpeg"));
        propertyImages13.add(new PropertyImages(saved_ap13, "/real_estate/apartment/bathroom/immo_ab_12.jpeg"));
        propertyImages13.add(new PropertyImages(saved_ap13, "/real_estate/apartment/kitchen/immo_ak_12.jpeg"));
        saved_ap13.setPropertyImages(propertyImages13);
        propertyRepository.save(saved_ap13);

        PropertyDetails pd14 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(50.75)
                .builtUpArea((double) Math.round(50.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a14 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap14 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd14)
                .address(a14)
                .price(725.00)
                .agentId(9L)
                .build();
        Property saved_ap14 = propertyService.saveProperty(ap14);
        List<PropertyImages> propertyImages14 = new ArrayList<>();
        propertyImages14.add(new PropertyImages(saved_ap14, "/real_estate/apartment/bedroom/immo_abed_13.jpeg"));
        propertyImages14.add(new PropertyImages(saved_ap14, "/real_estate/apartment/bathroom/immo_ab_13.jpeg"));
        propertyImages14.add(new PropertyImages(saved_ap14, "/real_estate/apartment/kitchen/immo_ak_13.jpeg"));
        saved_ap14.setPropertyImages(propertyImages14);
        propertyRepository.save(saved_ap14);

        PropertyDetails pd15 = PropertyDetails.builder()
                .description("Basic 2-bedroom apartment in Bronx")
                .carpetArea(38.50)
                .builtUpArea((double) Math.round(38.50 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("3rd")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a15 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap15 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd15)
                .address(a15)
                .price(1550.00)
                .agentId(14L)
                .build();
        Property saved_ap15 = propertyService.saveProperty(ap15);
        List<PropertyImages> propertyImages15 = new ArrayList<>();
        propertyImages15.add(new PropertyImages(saved_ap15, "/real_estate/apartment/bedroom/immo_abed_14.jpeg"));
        propertyImages15.add(new PropertyImages(saved_ap15, "/real_estate/apartment/bathroom/immo_ab_14.jpeg"));
        propertyImages15.add(new PropertyImages(saved_ap15, "/real_estate/apartment/kitchen/immo_ak_14.jpeg"));
        saved_ap15.setPropertyImages(propertyImages15);
        propertyRepository.save(saved_ap15);

        PropertyDetails pd16 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(40.75)
                .builtUpArea((double) Math.round(40.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a16 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap16 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd16)
                .address(a16)
                .price(650.00)
                .agentId(20L)
                .build();
        Property saved_ap16 = propertyService.saveProperty(ap16);
        List<PropertyImages> propertyImages16 = new ArrayList<>();
        propertyImages16.add(new PropertyImages(saved_ap16, "/real_estate/apartment/bedroom/immo_abed_15.jpeg"));
        propertyImages16.add(new PropertyImages(saved_ap16, "/real_estate/apartment/bathroom/immo_ab_15.jpeg"));
        propertyImages16.add(new PropertyImages(saved_ap16, "/real_estate/apartment/kitchen/immo_ak_15.jpeg"));
        saved_ap16.setPropertyImages(propertyImages16);
        propertyRepository.save(saved_ap16);

        PropertyDetails pd17 = PropertyDetails.builder()
                .description("Basic 2-bedroom apartment in Bronx")
                .carpetArea(38.50)
                .builtUpArea((double) Math.round(38.50 * 1.35))
                .comfortType(ComfortType.BASIC)
                .floor("3rd")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a17 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap17 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd17)
                .address(a17)
                .price(550.00)
                .agentId(5L)
                .build();
        Property saved_ap17 = propertyService.saveProperty(ap17);
        List<PropertyImages> propertyImages17 = new ArrayList<>();
        propertyImages17.add(new PropertyImages(saved_ap17, "/real_estate/apartment/bedroom/immo_abed_16.jpeg"));
        propertyImages17.add(new PropertyImages(saved_ap17, "/real_estate/apartment/bathroom/immo_ab_16.jpeg"));
        propertyImages17.add(new PropertyImages(saved_ap17, "/real_estate/apartment/kitchen/immo_ak_16.jpeg"));
        saved_ap17.setPropertyImages(propertyImages17);
        propertyRepository.save(saved_ap17);

        PropertyDetails pd18 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(40.75)
                .builtUpArea((double) Math.round(40.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a18 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap18 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd18)
                .address(a18)
                .price(725.00)
                .agentId(10L)
                .build();
        Property saved_ap18 = propertyService.saveProperty(ap18);
        List<PropertyImages> propertyImages18 = new ArrayList<>();
        propertyImages18.add(new PropertyImages(saved_ap18, "/real_estate/apartment/bedroom/immo_abed_17.jpeg"));
        propertyImages18.add(new PropertyImages(saved_ap18, "/real_estate/apartment/bathroom/immo_ab_17.jpeg"));
        propertyImages18.add(new PropertyImages(saved_ap18, "/real_estate/apartment/kitchen/immo_ak_17.jpeg"));
        saved_ap18.setPropertyImages(propertyImages18);
        propertyRepository.save(saved_ap18);

        PropertyDetails pd19 = PropertyDetails.builder()
                .description("Basic 1-bedroom apartment in Bronx")
                .carpetArea(48.50)
                .builtUpArea((double) Math.round(48.50 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("2nd")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(2000)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.E)
                .build();
        Address a19 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap19 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd19)
                .address(a19)
                .price(675.00)
                .agentId(15L)
                .build();
        Property saved_ap19 = propertyService.saveProperty(ap19);
        List<PropertyImages> propertyImages19 = new ArrayList<>();
        propertyImages19.add(new PropertyImages(saved_ap19, "/real_estate/apartment/bedroom/immo_abed_18.jpeg"));
        propertyImages19.add(new PropertyImages(saved_ap19, "/real_estate/apartment/bathroom/immo_ab_18.jpeg"));
        propertyImages19.add(new PropertyImages(saved_ap19, "/real_estate/apartment/kitchen/immo_ak_18.jpeg"));
        saved_ap19.setPropertyImages(propertyImages19);
        propertyRepository.save(saved_ap19);

        PropertyDetails pd20 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(40.75)
                .builtUpArea((double) Math.round(40.75 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("1st")
                .structureType(StructureType.MASONRY)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a20 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap20 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd20)
                .address(a20)
                .price(699.00)
                .agentId(20L)
                .build();
        Property saved_ap20 = propertyService.saveProperty(ap20);
        List<PropertyImages> propertyImages20 = new ArrayList<>();
        propertyImages20.add(new PropertyImages(saved_ap20, "/real_estate/apartment/bedroom/immo_abed_19.jpeg"));
        propertyImages20.add(new PropertyImages(saved_ap20, "/real_estate/apartment/bathroom/immo_ab_19.jpeg"));
        propertyImages20.add(new PropertyImages(saved_ap20, "/real_estate/apartment/kitchen/immo_ak_19.jpeg"));
        saved_ap20.setPropertyImages(propertyImages20);
        propertyRepository.save(saved_ap20);

        PropertyDetails pd21 = PropertyDetails.builder()
                .description("Basic 2-bedroom apartment in Bronx")
                .carpetArea(38.50)
                .builtUpArea((double) Math.round(38.50 * 1.35))
                .comfortType(ComfortType.STANDARD)
                .floor("3rd")
                .structureType(StructureType.WOOD)
                .yearOfConstruction(1996)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(1)
                .parkingNo(1)
                .balcony(false)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a21 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Bronx")
                .street("E Tremont Ave")
                .streetNo("2")
                .latitude(40.841261)
                .longitude(-73.862219)
                .build();
        Property ap21 = Property.builder()
                .transactionType(TransactionType.RENT)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd21)
                .address(a21)
                .price(700.00)
                .agentId(5L)
                .build();
        Property saved_ap21 = propertyService.saveProperty(ap21);
        List<PropertyImages> propertyImages21 = new ArrayList<>();
        propertyImages21.add(new PropertyImages(saved_ap21, "/real_estate/apartment/bedroom/immo_abed_20.jpeg"));
        propertyImages21.add(new PropertyImages(saved_ap21, "/real_estate/apartment/bathroom/immo_ab_20.jpeg"));
        propertyImages21.add(new PropertyImages(saved_ap21, "/real_estate/apartment/kitchen/immo_ak_20.jpeg"));
        saved_ap21.setPropertyImages(propertyImages21);
        propertyRepository.save(saved_ap21);

        PropertyDetails pd22 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(135.00)
                .builtUpArea((double) Math.round(135.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(1)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(2)
                .balcony(true)
                .terrace(false)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a22 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap22 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd22)
                .address(a22)
                .price(245000.00)
                .agentId(10L)
                .build();
        Property saved_ap22 = propertyService.saveProperty(ap22);
        List<PropertyImages> propertyImages22 = new ArrayList<>();
        propertyImages22.add(new PropertyImages(saved_ap22, "/real_estate/apartment/bedroom/immo_abed_21.jpeg"));
        propertyImages22.add(new PropertyImages(saved_ap22, "/real_estate/apartment/bedroom/immo_abed_22.jpeg"));
        propertyImages22.add(new PropertyImages(saved_ap22, "/real_estate/apartment/bathroom/immo_ab_21.jpeg"));
        propertyImages22.add(new PropertyImages(saved_ap22, "/real_estate/apartment/kitchen/immo_ak_21.jpeg"));
        saved_ap22.setPropertyImages(propertyImages22);
        propertyRepository.save(saved_ap22);

        PropertyDetails pd23 = PropertyDetails.builder()
                .description("Standard 1-bedroom apartment in Bronx")
                .carpetArea(120.00)
                .builtUpArea((double) Math.round(120.00 * 1.35))
                .comfortType(ComfortType.LUXURIOUS)
                .floor("1st")
                .structureType(StructureType.REINFORCED_CONCRETE)
                .yearOfConstruction(2002)
                .bathNo(2)
                .kitchenNo(1)
                .bedroomNo(2)
                .parkingNo(2)
                .balcony(true)
                .terrace(true)
                .swimmingPool(false)
                .energeticCertificate(EnergeticCertificate.D)
                .build();
        Address a23 = Address.builder()
                .country("United States")
                .state("New York")
                .city("New York")
                .neighborhood("Manhattan")
                .street("W 113th St")
                .streetNo("1")
                .latitude(40.805856)
                .longitude(-73.965125)
                .build();
        Property ap23 = Property.builder()
                .transactionType(TransactionType.SALE)
                .propertyCategory(PropertyCategory.APARTMENT)
                .propertyDetails(pd23)
                .address(a23)
                .price(325000.00)
                .agentId(15L)
                .build();
        Property saved_ap23 = propertyService.saveProperty(ap23);
        List<PropertyImages> propertyImages23 = new ArrayList<>();
        propertyImages23.add(new PropertyImages(saved_ap23, "/real_estate/apartment/bedroom/immo_abed_23.jpeg"));
        propertyImages23.add(new PropertyImages(saved_ap23, "/real_estate/apartment/bedroom/immo_abed_24.jpeg"));
        propertyImages23.add(new PropertyImages(saved_ap23, "/real_estate/apartment/bathroom/immo_ab_22.jpeg"));
        propertyImages23.add(new PropertyImages(saved_ap23, "/real_estate/apartment/bathroom/immo_ab_23.jpeg"));
        propertyImages23.add(new PropertyImages(saved_ap23, "/real_estate/apartment/kitchen/immo_ak_22.jpeg"));
        saved_ap23.setPropertyImages(propertyImages23);
        propertyRepository.save(saved_ap23);
    }
}
