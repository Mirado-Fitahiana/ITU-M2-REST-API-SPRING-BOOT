package travel.booking.travel.dto.request;

import lombok.Data;
import travel.booking.travel.entity.PaymentMethod;

@Data
public class PaymentRequest {

    private Double amount;

    private PaymentMethod method;

    private Long bookingId;
}
