package ua.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.booking.entity.Booking;
import ua.booking.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByUser_Name(String name);

    @Query("select b from Booking b where ((:firstDate >= b.startDate and :firstDate < b.endDate) and b.room = :room) or " +
            "((:lastDate > b.startDate and :lastDate <= b.endDate) and b.room = :room) or " +
            "((b.startDate between :firstDate and :lastDate) and b.room = :room)")
    Booking getIsBusy(@Param("room") Room room, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);
}
