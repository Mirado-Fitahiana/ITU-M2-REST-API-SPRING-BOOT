package travel.booking.travel.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ReviewRequest {

    @Min(1)
    @Max(5)
    private int rating;

    private String comment;

    private Long userId;

    private Long tripId;
}
