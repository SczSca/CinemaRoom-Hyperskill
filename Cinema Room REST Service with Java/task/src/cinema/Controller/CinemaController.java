package cinema.Controller;

import cinema.Model.DTO.SeatDTO;
import cinema.Model.Request.ReturnRequest;
import cinema.Model.Request.SeatRequest;
import cinema.Model.Request.StatRequest;
import cinema.Model.Response.PurchaseResponse;
import cinema.Model.Response.ReturnResponse;
import cinema.Model.Response.SeatsResponse;
import cinema.Model.Response.StatsResponse;
import cinema.Service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/seats")
    public ResponseEntity<SeatsResponse> getSeats() {

        return cinemaService.getSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchaseTicket(@RequestBody SeatRequest purchaseRequest) {
        return cinemaService.purchaseTicket(purchaseRequest);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnResponse> returnTicket(@RequestBody ReturnRequest returnRequest) {
        return cinemaService.returnTicket(returnRequest);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(@RequestParam(required = false)String password) {
        return cinemaService.getStats(password);
    }
}
