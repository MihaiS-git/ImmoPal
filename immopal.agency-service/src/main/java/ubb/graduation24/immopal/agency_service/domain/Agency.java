package ubb.graduation24.immopal.agency_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="agencies")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    private String phone;
    private String email;
    private String description;

    @Column(name="logo_url")
    private String logoUrl;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private Set<AgencyAgent> agents;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
    private Set<AgencyProperty> properties;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return Objects.equals(id, agency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
