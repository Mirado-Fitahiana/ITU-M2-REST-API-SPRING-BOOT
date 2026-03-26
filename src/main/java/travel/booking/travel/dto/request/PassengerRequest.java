package travel.booking.travel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PassengerRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String passportNumber;
}
