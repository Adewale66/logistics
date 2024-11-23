package logisticsapp.order;

import jakarta.validation.Valid;
import logisticsapp.dtos.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Response> orderRider(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(new Response("Rider ordered successfully", orderService.process(order), 200), HttpStatus.OK);
    }
}
