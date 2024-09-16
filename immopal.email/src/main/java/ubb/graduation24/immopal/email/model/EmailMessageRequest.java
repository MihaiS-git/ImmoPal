package ubb.graduation24.immopal.email.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessageRequest {

    private String subject;
    private String to;
    private String body;
    private boolean isHtml;

}
