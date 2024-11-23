package logisticsapp.unit_tests;

import logisticsapp.dtos.Location;
import logisticsapp.exceptions.RiderNotFoundException;
import logisticsapp.order.Order;
import logisticsapp.order.OrderService;
import logisticsapp.rider.Rider;
import logisticsapp.rider.RiderDto;
import logisticsapp.rider.RiderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private RiderService riderService;

    @Mock
    private ModelMapper modelMapper;

    private Location pickupLocation;
    private Location dropoffLocation;
    private Rider rider1;
    private Rider rider2;
    private RiderDto riderDto;

    @BeforeEach
    void setUp() {

        pickupLocation = new Location(40.712776, -74.005974);
        dropoffLocation = new Location(34.052235, -118.243683);


        rider1 = Rider.builder()
                .id("rider1")
                .firstName("John")
                .lastName("Doe")
                .latitude(BigDecimal.valueOf(40.730610))
                .longitude(BigDecimal.valueOf(-73.935242))
                .available(true)
                .build();

        rider2 = Rider.builder()
                .id("rider2")
                .firstName("Jane")
                .lastName("Smith")
                .latitude(BigDecimal.valueOf(40.712776))
                .longitude(BigDecimal.valueOf(-74.005974))
                .available(true)
                .build();

        riderDto = new RiderDto();
        riderDto.setId("rider2");
        riderDto.setFirstName("Jane");
        riderDto.setLastName("Smith");
        riderDto.setLocation(pickupLocation);
    }

    @Test
    void process_shouldReturnNearestRider_whenRidersAreAvailable() {
        when(riderService.getAllAvailableRiders()).thenReturn(Arrays.asList(rider1, rider2));
        when(modelMapper.map(rider2, RiderDto.class)).thenReturn(riderDto);

        RiderDto result = orderService.process(new Order("james", pickupLocation, dropoffLocation));

        assertNotNull(result);
        assertEquals("rider2", result.getId());
        verify(riderService, times(1)).getAllAvailableRiders();
        verify(modelMapper, times(1)).map(rider2, RiderDto.class);
    }

    @Test
    void process_shouldThrowException_whenNoRidersAvailable() {
        when(riderService.getAllAvailableRiders()).thenReturn(Collections.emptyList());

        RiderNotFoundException exception = assertThrows(RiderNotFoundException.class,
                () -> orderService.process(new Order("james", pickupLocation, dropoffLocation)));
        assertEquals("No riders available", exception.getMessage());
        verify(riderService, times(1)).getAllAvailableRiders();
        verifyNoInteractions(modelMapper);
    }

    @Test
    void getNearestRider_shouldReturnClosestRider() {
        List<Rider> riders = Arrays.asList(rider1, rider2);

        Rider result = orderService.getNearestRider(riders, pickupLocation);

        assertNotNull(result);
        assertEquals("rider2", result.getId());
    }

    @Test
    void calculateHaversineDistance_shouldReturnCorrectDistance() {
        double distance = orderService.calculateHaversineDistance(
                40.730610, -73.935242, pickupLocation);

        assertTrue(distance > 0);
        assertEquals(6282.021382425868, distance, 10);
    }

}
