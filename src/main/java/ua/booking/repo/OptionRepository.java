package ua.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.booking.entity.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    Option findByName(String optionName);
}
