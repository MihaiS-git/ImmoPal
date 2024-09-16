package ubb.graduation24.immopal.appointment_service.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDetailsDto {

    private Long id;
    private String description;
    private Double carpetArea;
    private Double builtUpArea;
    private String comfortType;
    private String floor;
    private String structureType;
    private int yearOfConstruction;
    private int bathNo;
    private int kitchenNo;
    private int bedroomNo;
    private int parkingNo;
    @Setter
    private boolean balcony;
    @Setter
    private boolean terrace;
    @Setter
    private boolean swimmingPool;
    private String energeticCertificate;

}
