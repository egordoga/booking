package ua.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.booking.entity.Option;
import ua.booking.entity.Room;
import ua.booking.repo.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> findRoomsByCategoryName(String categoryName) {
        return roomRepository.findAllByCategory_Name(categoryName);
    }

    @Override
    public Room findRoomByNumber(Integer number) {
        return roomRepository.findByNumber(number);
    }

    @Override
    public String getCost(Room room, LocalDate startDate, LocalDate endDate) {
        BigDecimal costOption = room.getOptions().stream().map(Option::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        int daysQuantity = Period.between(startDate, endDate).getDays();
        BigDecimal costOneDay = costOption.add(room.getPrice());
        BigDecimal cost = costOneDay.multiply(new BigDecimal(String.valueOf(daysQuantity)));
        return "Booking cost is  " + cost.toString();
    }

    @Override
    public List<Room> findAllRoomsNoReserved(LocalDate firstDate, LocalDate lastDate) {
        return roomRepository.findAllNoReserved(firstDate, lastDate);
    }
}
