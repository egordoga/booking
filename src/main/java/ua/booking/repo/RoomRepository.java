package ua.booking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.booking.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByCategory_Name(String categoryName);

    Room findByNumber(Integer number);

    @Query(value = "select r.* from room r where r.id not in (select b.room_id from booking b where" +
            " (:firstDate >= b.start_date and :firstDate < b.end_date) or" +
            " (:lastDate > b.start_date and :lastDate <= b.end_date) or" +
            " (b.start_date between :firstDate and :lastDate))", nativeQuery = true)
    List<Room> findAllNoReserved(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);
}
