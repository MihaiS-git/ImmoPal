package ubb.graduation24.immopal.property_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name="property_category")
    private PropertyCategory propertyCategory;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="details_id", referencedColumnName = "id")
    private PropertyDetails propertyDetails;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address address;

    private Double price;

    @Column(name="agent_id")
    private Long agentId;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PropertyImages> propertyImages = new ArrayList<>();

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", propertyCategory=" + propertyCategory +
                ", price=" + price +
                ", address=" + (address != null ? address.getId() : "null") +
                ", propertyDetails=" + (propertyDetails != null ? propertyDetails.getId() : "null") +
                ", propertyImages=" + propertyImages.stream().map(PropertyImages::getImageUrl).toList() +
                '}';
    }
}
