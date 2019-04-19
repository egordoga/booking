package ua.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.booking.assembler.BookingResourceAssembler;
import ua.booking.dto.BookingDto;
import ua.booking.entity.Booking;
import ua.booking.entity.Option;
import ua.booking.entity.Room;
import ua.booking.entity.User;
import ua.booking.exeption.ArgsWrongException;
import ua.booking.service.IBookingService;
import ua.booking.service.IOptionService;
import ua.booking.service.IRoomService;
import ua.booking.service.IUserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/bookings", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class BookingController {

    private final IBookingService bookingService;
    private final IUserService userService;
    private final IRoomService roomService;
    private final IOptionService optionService;
    private final BookingResourceAssembler assembler;

    @Autowired
    public BookingController(IBookingService bookingService, IUserService userService, IRoomService roomService,
                             IOptionService optionService, BookingResourceAssembler assembler) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.roomService = roomService;
        this.optionService = optionService;
        this.assembler = assembler;
    }

    @GetMapping("/username/{name}")
    public ResponseEntity<Resources<Resource<Booking>>> getBookingsForUser(@PathVariable String name) /*throws EntityNotFoundException*/ {
        List<Booking> listBooking = bookingService.findBookingsByUserName(name);
        List<Resource<Booking>> bookings = listBooking.stream()
                .map(assembler::toResource).collect(Collectors.toList());
        Resources<Resource<Booking>> resBookings = new Resources<>(bookings, linkTo(methodOn(BookingController.class)
                .getBookingsForUser(name)).withSelfRel());
        return new ResponseEntity<>(resBookings, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Resources<Resource<Booking>>> getAllBookings() {
        List<Resource<Booking>> bookings = bookingService.findAllBookings().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new Resources<>(bookings, linkTo(methodOn(BookingController.class)
                .getAllBookings()).withSelfRel()), HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<String> doBooking(@RequestBody BookingDto bookingDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yy");
        LocalDate startDate = LocalDate.parse(bookingDto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(bookingDto.getEndDate(), formatter);

        if (startDate.isBefore(LocalDate.now()) || startDate.compareTo(endDate) > 0) {
            throw new ArgsWrongException("Date wrong");
        }
        User userFromDb = userService.findUserByName(bookingDto.getUsername());
        if (userFromDb == null) {
            throw new ArgsWrongException("User not found");
        }
        Room roomFromDB = roomService.findRoomByNumber(bookingDto.getRoomNumber());
        if (roomFromDB == null) {
            throw new ArgsWrongException("There is no room with this number");
        }

        List<String> options = bookingDto.getOptions();
        if (options != null && options.size() > 0) {
            for (String optionName : options) {
                Option option = optionService.findOptionByName(optionName);
                if (option == null) {
                    throw new ArgsWrongException("There is no option with this name");
                } else {
                    roomFromDB.getOptions().add(option);
                }
            }
        }

        String response = bookingService.doBooking(new Booking(startDate, endDate, userFromDb, roomFromDB));
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
