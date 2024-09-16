package ubb.graduation24.immopal.person_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @Column(name="picture_url")
    private String pictureUrl;

    @Column(name="user_id")
    private Long userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_appointments", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "appointment_id")
    @Builder.Default
    private List<Long> appointmentIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_property", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "property_id")
    @Builder.Default
    private List<Long> propertyIds = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "person_bid", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "bid_id")
    @Builder.Default
    private List<String> bidIds = new ArrayList<>();

    public Person(String firstName, String lastName, String phoneNumber,
                  String dateOfBirth, String address, String pictureUrl, Long userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
        this.address = address;
        this.pictureUrl = pictureUrl;
        this.userId = userId;
    }
}
