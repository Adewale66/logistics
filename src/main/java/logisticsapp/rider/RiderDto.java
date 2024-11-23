package logisticsapp.rider;

import logisticsapp.dtos.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RiderDto {
    String id;
    String firstName;
    String  lastName;
    Location location;
}
