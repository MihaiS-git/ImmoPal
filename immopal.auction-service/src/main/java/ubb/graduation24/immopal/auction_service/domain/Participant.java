package ubb.graduation24.immopal.auction_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "participants")
public class Participant {

    @Id
    private String id;

    private Long personId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String pictureUrl;
    private Long userId;
    private String email;

    @Builder.Default
    private List<String> auctions = new ArrayList<>();

}
