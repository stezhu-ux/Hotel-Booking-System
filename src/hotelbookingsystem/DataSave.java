package hotelbookingsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataSave {

    private static final String FILENAME = "bookings.txt";
    private static final String DELIMITER = ";";

    public static void saveBookings(List<Booking> bookings) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
          for (Booking booking : bookings) {
                // format the booking data as a string
                String bookingData = booking.getGuest().getName() + DELIMITER
                        + booking.getGuest().getEmail() + DELIMITER
                        + booking.getGuest().getPhone() + DELIMITER
                        + booking.getRoom().getRoomNum() + DELIMITER
                       + booking.getCheckInDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + DELIMITER
                        + booking.getCheckOutDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + DELIMITER
                        + booking.getTotalCost();

                // write the string to the file
                writer.write(bookingData);
                writer.newLine();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing booking data to file.");
            e.printStackTrace();
        }
    }
}