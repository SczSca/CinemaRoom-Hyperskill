package cinema.Model.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatsResponse {
    private int income;
    private int available;
    private int purchased;
}
