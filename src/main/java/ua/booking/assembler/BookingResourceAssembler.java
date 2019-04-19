package ua.booking.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import ua.booking.entity.Booking;

@Component
public class BookingResourceAssembler implements ResourceAssembler<Booking, Resource<Booking>> {
    @Override
    public Resource<Booking> toResource(Booking booking) {
        return new Resource<>(booking);
    }
}
