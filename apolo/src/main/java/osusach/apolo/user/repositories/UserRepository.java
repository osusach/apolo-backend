package osusach.apolo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osusach.apolo.user.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    // Find a user by email
    Optional<UserEntity> findByEmail(String email);

}
