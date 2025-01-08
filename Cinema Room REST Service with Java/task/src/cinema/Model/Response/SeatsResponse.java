package cinema.Model.Response;

import cinema.Model.DTO.SeatDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class SeatsResponse {
    private int rows;
    private int columns;
    private List<SeatDTO> seats;

}
