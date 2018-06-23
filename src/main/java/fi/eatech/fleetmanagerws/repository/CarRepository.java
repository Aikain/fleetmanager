package fi.eatech.fleetmanagerws.repository;

import fi.eatech.fleetmanagerws.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Car repository
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@Repository
public interface CarRepository extends JpaRepository<Car, String> {
}
