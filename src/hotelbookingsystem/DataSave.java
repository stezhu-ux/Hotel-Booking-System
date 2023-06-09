package hotelbookingsystem;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class DataSave {
    private static final String DB_URL = "jdbc:derby://localhost:1527/HotelBookingSystemDatabase;create=true";

    public static void saveBookings(List<Booking> bookings) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO bookings (name, email, phone, room_num, check_in_date, check_out_date, total_cost) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            for (Booking booking : bookings) {
               //format the booking data as a string
                statement.setString(1, booking.getGuest().getName());
                statement.setString(2, booking.getGuest().getEmail());
                statement.setString(3, booking.getGuest().getPhone());
                statement.setInt(4, booking.getRoom().getRoomNum());
                statement.setString(5, booking.getCheckInDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                statement.setString(6, booking.getCheckOutDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                statement.setDouble(7, booking.getTotalCost());

                 //write the string to the file
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error writing booking data to database.");
            e.printStackTrace();
        }
    }
         public static void removeBooking(Booking booking) {
                try (Connection connection = DriverManager.getConnection(DB_URL);
                     PreparedStatement statement = connection.prepareStatement(
                             "DELETE FROM bookings WHERE room_num = ?")) {

                    statement.setInt(1, booking.getRoom().getRoomNum());

                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error writing booking data to database");
                    e.printStackTrace();
                }
            }
        }