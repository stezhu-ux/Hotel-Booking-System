package hotelbookingsystem;

import java.util.Scanner;

public class CheckOut {
    
    public static boolean checkAvailability() {
    for (HotelRoom room : HotelBookingSystem.rooms) {
        if (room.isOccupied()) {
            return true;
        }
    }
    return false;
    
}
    
    public static void checkOutGuest() {
        
        Scanner scanner = new Scanner(System.in);

        int roomNum;
        
        Booking booking = null;
        System.out.println("-------------------------");
        while (true) {           
            // find unoccupied rooms
        System.out.println("Current occupied Room(s):");
for (HotelRoom room : HotelBookingSystem.rooms) {
    if (room.isOccupied()) {
        System.out.println("Rooms: " + room.getRoomNum());
    }
}

System.out.println("-------------------------");

            // prompt user to enter the room number
            System.out.println("Please enter the room number you want to check-out:");
            if (scanner.hasNextInt()) 
            {
                roomNum = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                // find the booking with the given room number
                for (Booking b : HotelBookingSystem.bookings) {
                    if (b.getRoom().getRoomNum() == roomNum) {
                        booking = b;
                        break;
                    }
                }

                if (booking == null) {
                    System.out.println("No booking found for room number " + roomNum);
                } else {
                    break; // valid input, break out of the loop
                }
            } else {
                System.out.println("Invalid input. Please enter a valid room number.");
                scanner.nextLine(); // consume the invalid input
            }
        }

        // calculate the total amount due
        double totalAmount = booking.getTotalCost();

        // set the room to unoccupied
        booking.getRoom().setOccupied(false);

        // remove the booking from the list of bookings
        HotelBookingSystem.bookings.remove(booking);
        
        System.out.println("-------------------------");
        System.out.println("Check-out successful!");
        System.out.println("-------------------------");
        System.out.println("Guest name: " + booking.getGuest().getName());
        System.out.println("Guest email: " + booking.getGuest().getEmail());
        System.out.println("Guest phone: " + booking.getGuest().getPhone());
        System.out.println("Room number: " + roomNum);
        System.out.println("Check-in date: " + booking.getCheckInDate());
        System.out.println("Check-out date: " + booking.getCheckOutDate());
        System.out.println("Nightly stay booked for: " + booking.getNightlyStay() + " day(s)");
        System.out.println("Total amount due: $" + totalAmount);
        System.out.println("-------------------------");

        // save the updated list of bookings to file
        DataSave.saveBookings(HotelBookingSystem.bookings);
    }
}


