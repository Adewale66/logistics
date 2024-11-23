package logisticsapp.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import logisticsapp.dtos.Location;

public record Order(

        @NotBlank(message = "Customer name is mandatory")
        @JsonProperty("customer_name")
        String customerName,

        @NotNull(message = "Pickup location is mandatory")
        Location pickup,

        @NotNull(message = "Drop off location is mandatory")
        @JsonProperty("drop_off")
        Location dropOff
) {
}
