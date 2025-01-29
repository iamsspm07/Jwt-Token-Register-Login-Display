package hscare360.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hscare360.io.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findByEmail(String email);
    Optional<UserDetails> findByNumber(String number);
}
