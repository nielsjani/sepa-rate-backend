package be.separate.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsernameAndPassword(String username, String password);
    List<User> findByCountry(String country);
    User findByUsername(String username);
}
