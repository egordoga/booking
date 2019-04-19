Description of "Booking" project
This project is a REST application for interaction with the hotel site
Capabilities:

View list of available rooms for specified dates --- /api/v1/rooms/free/{startDate}/{endDate}
View rooms filtered by category --- /api/v1/rooms/{categoryName}
Create user ---/api/v1/users/user_new
User can book the room for specified days ---/api/v1/bookings/order
User can view his booking ---/api/v1/bookings/username/{name}
User can get the total price of the booking ---/api/v1/rooms/cost/{roomNumber}/{startDate}/{endDate}
View all bookings for the hotel ---/api/v1/bookings/all
All dates of application have the format d-MM-yy