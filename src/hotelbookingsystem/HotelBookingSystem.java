package hotelbookingsystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

public class HotelBookingSystem {

    static ArrayList<HotelRoom> rooms = new ArrayList<>();
    public static ArrayList<Booking> bookings = new ArrayList<>();
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
       //test
       
    //initialize hotel rooms
    rooms.add(new StandardRoom(1, 100, BedType.SINGLE));
    rooms.add(new StandardRoom(2, 100, BedType.SINGLE));
    rooms.add(new DeluxeRoom(3, 300, 2, BedType.QUEEN));
    rooms.add(new DeluxeRoom(4, 300, 2, BedType.QUEEN));
    rooms.add(new PenthouseRoom(5, 500, 3, BedType.KING, true));
    
        // load previous bookings
       File bookingsFile = new File("bookings.txt");
   if (bookingsFile.exists()) {
       bookings.addAll(DataRead.loadBookings());
   } else {
       System.out.println("No bookings file found.");
   }
    
    System.out.println("-------------------------");
    System.out.println("Welcome to Hotel Booking System app");
    System.out.println("The purpose of this app is for hotel receptionists to book and check-out rooms for guests wanting to stay at their hotel.");
    System.out.println("-------------------------");
    
    // main loop to handle user input
    while (true) {
        System.out.println("Please select an option:");
        System.out.println("-------------------------");
        System.out.println("Press 1 to: Book a room");
        System.out.println("Press 2 to: Check-out a room");
        System.out.println("Press 3 to: View all rooms and details");
        System.out.println("Press 4 to: View all unoccupied rooms");
        System.out.println("Press 5 to: View all occupied rooms");
        System.out.println("Press 6 to: View specific room number");
        System.out.println("Press 7 to: Find guest by name");
        System.out.println("Press 8 to: List all current guest");
        System.out.println("Press 9 to: Exit app");
        
        // try catch to handle user errors outside of number range        
        try {
            int option = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            
            switch (option) {
                 
                    case 1:
                        //  check if any unoccupied rooms available
                    if (BookRoom.checkAvailability()) {
                        BookRoom bookRoom = new BookRoom();
                    } else {
                        System.out.println("Sorry, there are no unoccupied rooms available");
                        System.out.println("-------------------------");
                    }
    
                break;
                    
                case 2:
                    if (CheckOut.checkAvailability()) {
                    CheckOut.checkOutGuest();
                    } else {
                     System.out.println("Sorry, all rooms are unoccupied");
                     System.out.println("-------------------------");
                    }
                    
                    break;
                    
                case 3:    
                    RoomInfo.viewAllRooms();
                    break;
                    
                case 4:
                    RoomInfo.listUnoccupiedRooms();

                    break;
                    
                case 5:
                     RoomInfo.listOccupiedRooms();
                    
                    break;
                    
                case 6: 
                    RoomInfo.viewSpecificRoomNumber();
                    break;
                    
                case 7:
                    RoomInfo.findGuestByName();
                    break;
                    
                case 8:
                     RoomInfo.listAllGuests();
                    break;
                
                case 9:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid option, please try again");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again");
            scanner.nextLine(); //consumes invalid input
        }
    }
}
}

