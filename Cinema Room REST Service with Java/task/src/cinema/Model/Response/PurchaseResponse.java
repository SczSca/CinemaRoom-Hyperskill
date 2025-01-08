package cinema.Model.Response;

import cinema.Model.DTO.SeatDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PurchaseResponse {
    private String token;
    private SeatDTO ticket;
}
