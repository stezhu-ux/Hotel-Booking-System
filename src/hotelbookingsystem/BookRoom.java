package hotelbookingsystem;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookRoom {
    
public static boolean checkAvailability() {
    for (HotelRoom room : HotelBookingSystem.rooms) {
        if (!room.isOccupied()) {
            return true;
        }
    }
    return false;
    }
        public BookRoom() {


            Scanner scanner = new Scanner(System.in);

            // prompt user to enter guest name
            String name = "";
    while (name.isEmpty() || !name.matches("^[a-zA-Z]+$")) {
        System.out.println("Please enter guest name");
        name = scanner.nextLine().trim();
        if (!name.matches("^[a-zA-Z]+$")) {
            System.out.println("Name cannot contain numbers or special characters. Please try again.");
            name = "";
        }
    }
// prompt user to enter guest email
    Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}(\\.\\w{2,})?$");
String email = "";
while (email.isEmpty()) {
    System.out.println("Please enter guest email:");
    email = scanner.nextLine().trim();

    Matcher matcher = emailPattern.matcher(email);
    if (!matcher.matches()) {
        System.out.println("Invalid email format. Please enter a valid email address.");
        email = "";
    }
}

System.out.println("Email is valid: " + email);

    
// prompt user to enter guest pphone number
            String phone = "";
while (phone.isEmpty()) {
    System.out.println("Please enter guest New Zealand mobile number (NZ mobile number has between 9 and 11 digits)");
    phone = scanner.nextLine();
    // check if phone number contains non-digit characters or has a length outside the valid range
    if (!phone.matches("\\d{9,11}")) {
        System.out.println("Invalid phone number. Please enter a valid phone number containing only digits between 9 and 11 characters.");
        phone = "";
    }
}
        
        // find unoccupied rooms
        System.out.println("-------------------------");
        System.out.println("Current unoccupied Room(s):");
        System.out.println("-------------------------");
        for (HotelRoom room : HotelBookingSystem.rooms) {
            if (!room.isOccupied()) {
                System.out.println("Rooms: " + room.getRoomNum());
            }
        }

        System.out.println("-------------------------");

                // loop until the user enters a valid and unoccupied room number
        int roomNum;
        HotelRoom room = null;
        while (room == null || room.isOccupied()) {
            System.out.println("Please enter the room number you want to book:");
            roomNum = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

    // find the room with the given room number
    for (HotelRoom r : HotelBookingSystem.rooms) {
        if (r.getRoomNum() == roomNum) {
            room = r;
            break;
        }
    }

    // check if the room is available
    if (room == null) {
        System.out.println("Invalid room number. Please try again.");
    } else if (room.isOccupied()) {
        System.out.println("Room " + roomNum + " is already occupied. Please try again");
        // Print out the list of unoccupied rooms
    System.out.println("Current unoccupied room(s):");
    for (HotelRoom r : HotelBookingSystem.rooms) {
        if (!r.isOccupied()) {
            System.out.println("Rooms: " + r.getRoomNum());
        }
    }
    System.out.println("");
    }
        }

        // create a new guest object
        Guest guest = new Guest(name, email, phone);
        

// get the current local date
LocalDate currentDate = LocalDate.now();

// prompt user to enter check-in date
LocalDate checkInDate = null;
while (checkInDate == null || checkInDate.isBefore(currentDate)) {
    System.out.println("Please enter the check-in date (YYYY-MM-DD):");
    String input = scanner.nextLine();
    try {
        checkInDate = LocalDate.parse(input);
        if (checkInDate.isBefore(currentDate)) {
            System.out.println("Check-in date must be after the current date. Please try again.");
            checkInDate = null;
        }
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please try again.");
    }
}

// prompt user to enter check-out date
LocalDate checkOutDate = null;
while (checkOutDate == null || checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
    System.out.println("Please enter the check-out date (YYYY-MM-DD):");
    String input = scanner.nextLine();
    try {
        checkOutDate = LocalDate.parse(input);
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            System.out.println("Check-out date cannot be before or the same as check-in date. Please try again.");
            checkOutDate = null;
        }
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please try again.");
        checkOutDate = null;
    }
}

        // create the booking object
        Booking booking = new Booking(guest, room, checkInDate, checkOutDate);
        System.out.println("-------------------------");
        System.out.println("Booking successful!");
         System.out.println("-------------------------");
        System.out.println(booking.toString());
        System.out.println("-------------------------");

        HotelBookingSystem.bookings.add(booking); // add booking to the list of bookings
        DataSave.saveBookings(HotelBookingSystem.bookings); // save bookings to file
        room.setOccupied(true);

        }
    
    }


