package ua.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.booking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
