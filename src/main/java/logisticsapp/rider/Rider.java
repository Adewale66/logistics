package logisticsapp.rider;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "riders")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, precision = 8, scale = 6)
    private BigDecimal  latitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal  longitude;

    @Column(nullable = false)
    private boolean available;
}
