package logisticsapp.rider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiderRepository extends JpaRepository<Rider, String> {

    List<Rider> findAllByAvailable(boolean available);
    boolean existsByEmail(String email);
}
