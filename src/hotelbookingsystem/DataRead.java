package hotelbookingsystem;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataRead {
    private static final String DB_URL = "jdbc:derby://localhost:1527/HotelBookingSystemDatabase;create=true";
    private Connection connection;

    public DataRead() {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }

    public List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM bookings");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                int roomNum = resultSet.getInt("room_num");
                LocalDate checkInDate = LocalDate.parse(resultSet.getString("check_in_date"), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate checkOutDate = LocalDate.parse(resultSet.getString("check_out_date"), DateTimeFormatter.ISO_LOCAL_DATE);
                double totalCost = resultSet.getDouble("total_cost");

                // Find the room with the given room number
                HotelRoom room = null;
                for (HotelRoom availableRoom : HotelBookingSystem.rooms) {
                    if (availableRoom.getRoomNum() == roomNum) {
                        room = availableRoom;
                        break;
                    }
                }

                // Create the booking object
                if (room != null) {
                    Guest guest = new Guest(name, email, phone);
                    Booking booking = new Booking(guest, room, checkInDate, checkOutDate);
                    booking.setTotalCost(totalCost);
                    bookings.add(booking);
                    room.setOccupied(true);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error reading booking data from database.");
            e.printStackTrace();
        }
        return bookings;
    }
}