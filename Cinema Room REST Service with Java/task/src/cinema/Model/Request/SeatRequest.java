package cinema.Model.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeatRequest {
    private int row;
    private int column;
}
