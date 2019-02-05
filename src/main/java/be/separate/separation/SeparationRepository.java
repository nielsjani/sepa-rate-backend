package be.separate.separation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeparationRepository extends JpaRepository<Separation, Integer> {

    List<Separation> findByCreatedUser(String userId);
    Optional<Separation> findByIdAndCreatedUser(String userId, String createdUser);
}
