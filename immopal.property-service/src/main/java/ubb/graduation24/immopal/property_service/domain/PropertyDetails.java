package ubb.graduation24.immopal.property_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name="carpet_area")
    private Double carpetArea;

    @Column(name="built_up_area")
    private Double builtUpArea;

    @Enumerated(EnumType.STRING)
    @Column(name="comfort_type")
    private ComfortType comfortType;

    private String floor;

    @Enumerated(EnumType.STRING)
    @Column(name="structure_type")
    private StructureType structureType;

    @Column(name="year_of_construction")
    private int yearOfConstruction;

    @Column(name="bath_no")
    private int bathNo;

    @Column(name="kitchen_no")
    private int kitchenNo;

    @Column(name="bedroom_no")
    private int bedroomNo;

    @Column(name="parking_no")
    private int parkingNo;

    private boolean balcony;
    private boolean terrace;

    @Column(name="swimming_pool")
    private boolean swimmingPool;

    @Enumerated(EnumType.STRING)
    @Column(name="energetic_certificate")
    private EnergeticCertificate energeticCertificate;

}
