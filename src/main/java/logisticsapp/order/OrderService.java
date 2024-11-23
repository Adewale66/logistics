package logisticsapp.order;

import jakarta.validation.Valid;
import logisticsapp.dtos.Location;
import logisticsapp.exceptions.RiderNotFoundException;
import logisticsapp.rider.Rider;
import logisticsapp.rider.RiderDto;
import logisticsapp.rider.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RiderService riderService;
    private final ModelMapper modelMapper;

    public RiderDto process(Order order) {
        List<Rider> riders = riderService.getAllAvailableRiders();
        if (riders.isEmpty()) {
            throw new RiderNotFoundException("No riders available", 404);
        }
        Rider nearestRider = getNearestRider(riders, order.pickup());
        return modelMapper.map(nearestRider, RiderDto.class);

    }

    public Rider getNearestRider(List<Rider> riders, Location location) {
        riders.sort((a, b) -> {
            double distanceA = calculateHaversineDistance(a.getLatitude().doubleValue(),
                    a.getLongitude().doubleValue(), location);
            double distanceB = calculateHaversineDistance(b.getLatitude().doubleValue(),
                    b.getLongitude().doubleValue(), location);
            return Double.compare(distanceA, distanceB);
        });
        return riders.getFirst();
    }

    public double calculateHaversineDistance(double lat1, double lon1, Location b) {
        // Earth's radius in meters
        final double EARTH_RADIUS = 6371e3;

        // Latitude and longitude of the second point
        double lat2 = b.getLatitude();
        double lon2 = b.getLongitude();

        // Convert latitude and longitude differences to radians
        double latitude1InRadians = Math.toRadians(lat1);
        double latitude2InRadians = Math.toRadians(lat2);
        double deltaLatitude = Math.toRadians(lat2 - lat1);
        double deltaLongitude = Math.toRadians(lon2 - lon1);

        // Haversine formula
        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
                + Math.cos(latitude1InRadians) * Math.cos(latitude2InRadians)
                * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate and return the distance
        return EARTH_RADIUS * c;
    }


}
