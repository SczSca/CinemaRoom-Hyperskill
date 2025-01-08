package cinema.Model.DTO;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ReturnSeatDTO {
    private Instant expirationToken;
    private SeatDTO seat;
}
