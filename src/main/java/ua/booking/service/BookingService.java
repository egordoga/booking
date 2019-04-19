package ua.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.booking.entity.Booking;
import ua.booking.repo.BookingRepository;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findBookingsByUserName(String name) {
        return bookingRepository.findAllByUser_Name(name);
    }

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public String doBooking(Booking booking) {
        Booking fromDB = bookingRepository.getIsBusy(booking.getRoom(), booking.getStartDate(), booking.getEndDate());
        if (fromDB != null) {
            return "Room is busy at this dates";
        }
        bookingRepository.save(booking);
        return "Booking is successful";
    }
}
