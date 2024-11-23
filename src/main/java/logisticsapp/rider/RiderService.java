package logisticsapp.rider;

import logisticsapp.dtos.CreateRiderDto;
import logisticsapp.exceptions.RiderExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository repository;

    @Transactional
    public void createRider(CreateRiderDto rider) {

        if (repository.existsByEmail(rider.getEmail())) {
            throw new RiderExistsException("Rider with email " + rider.getEmail() + " already exists", 409);
        }

        Rider newRider = Rider.builder()
                .firstName(rider.getFirstName())
                .lastName(rider.getLastName())
                .email(rider.getEmail())
                .latitude(BigDecimal.valueOf(rider.getLocation().getLatitude()))
                .longitude(BigDecimal.valueOf(rider.getLocation().getLongitude()))
                .available(true)
                .build();
        repository.save(newRider);
    }

    public List<Rider> getAllAvailableRiders() {
        return repository.findAllByAvailable(true);
    }

}
