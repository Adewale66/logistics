package logisticsapp.rider;

import jakarta.validation.Valid;
import logisticsapp.dtos.CreateRiderDto;
import logisticsapp.dtos.Location;
import logisticsapp.dtos.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("riders")
class RiderController {

    private final RiderService riderService;

    @PostMapping
    public ResponseEntity<Response> createRider(@Valid @RequestBody CreateRiderDto rider) {
        riderService.createRider(rider);
        return new ResponseEntity<>(new Response("Rider created successfully", null, 201), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getRiders() {
        List<RiderDto> riders = riderService.getAllAvailableRiders().stream().map((element) -> {
            RiderDto riderDto = new RiderDto();
            riderDto.setId(element.getId());
            riderDto.setFirstName(element.getFirstName());
            riderDto.setLastName(element.getLastName());
            riderDto.setLocation(new Location(element.getLatitude().doubleValue(), element.getLongitude().doubleValue()));
            return riderDto;
        }).toList();
        return new ResponseEntity<>(new Response("Riders retrieved successfully", riders, 200), HttpStatus.OK);
    }


}
