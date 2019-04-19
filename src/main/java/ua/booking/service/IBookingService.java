package ua.booking.service;

import ua.booking.entity.Booking;

import java.util.List;

public interface IBookingService {
    List<Booking> findBookingsByUserName(String name);
    List<Booking> findAllBookings();

    String doBooking(Booking booking);
}
