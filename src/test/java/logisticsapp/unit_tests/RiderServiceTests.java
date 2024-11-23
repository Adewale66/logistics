package logisticsapp.unit_tests;

import logisticsapp.dtos.CreateRiderDto;
import logisticsapp.exceptions.RiderExistsException;
import logisticsapp.rider.RiderRepository;
import logisticsapp.rider.RiderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RiderServiceTests {

    @InjectMocks
    private RiderService riderService;

    @Mock
    private RiderRepository repository;

    private CreateRiderDto rider;

    @BeforeEach
    public void setUp() {
        rider = CreateRiderDto.builder()
                .firstName("John")
                .lastName("Doe")
                .location(
                        logisticsapp.dtos.Location.builder()
                                .latitude(1.0)
                                .longitude(1.0)
                                .build()
                ).email("email@email.com").build();

    }

    @Test
    public void testCreateRider() {
        when(repository.existsByEmail(rider.getEmail())).thenReturn(false);
        this.riderService.createRider(rider);
    }

    @Test
    public void testCreateRiderExists() {
        when(repository.existsByEmail(rider.getEmail())).thenReturn(true);
        assertThrows(RiderExistsException.class, () -> {
            this.riderService.createRider(rider);
        });
    }

}
