package hotelbookingsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataRead {
    private static final String FILENAME = "bookings.txt";
    private static final String DELIMITER = ";";

    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                // split the line into parts using the delimiter
                String[] parts = line.split(DELIMITER);

                // parse the parts into the correct data types
                String name = parts[0];
                String email = parts[1];
                String phone = parts[2];   
                int roomNum = Integer.parseInt(parts[3]);
                LocalDate checkInDate = LocalDate.parse(parts[4], DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate checkOutDate = LocalDate.parse(parts[5], DateTimeFormatter.ISO_LOCAL_DATE);
                double totalCost = Double.parseDouble(parts[6]);

                // find the room with the given room number
                HotelRoom room = null;
               for (HotelRoom availableRoom : HotelBookingSystem.rooms) {
                    if (availableRoom.getRoomNum() == roomNum) {
                        room = availableRoom;
                        
                        break;
                    }
                }

                // create the booking object
                if (room != null) {
                    Guest guest = new Guest(name, email, phone);
                    Booking booking = new Booking(guest, room, checkInDate, checkOutDate);
                    booking.setTotalCost(totalCost);
                    bookings.add(booking);
                    room.setOccupied(true);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading booking data from file.");
            e.printStackTrace();
        } 
        
        return bookings;
    }
}
