package hotelbookingsystem;

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RoomInfo {

   // Method to view all rooms
    public static void viewAllRooms() {
        //add JFrame
        JFrame frame = new JFrame("View All Rooms");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //add JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        //add JLabel
        JLabel titleLabel = new JLabel("List of all rooms and details");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        //add JTextArea
        JTextArea roomInfoArea = new JTextArea();
        roomInfoArea.setEditable(false);
        roomInfoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(roomInfoArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //add JButton
        JButton refreshButton = new JButton("Update");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshRoomInfo(roomInfoArea);
                }
            }
        );
        mainPanel.add(refreshButton, BorderLayout.SOUTH);
        
         // Initial room information update
        refreshRoomInfo(roomInfoArea);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
  
    //print and update list
    private static void refreshRoomInfo(JTextArea roomInfoArea) {
        StringBuilder roomInfoBuilder = new StringBuilder();
        for (HotelRoom room : HotelBookingSystem.rooms) {
            roomInfoBuilder.append(room.toString()).append("\n");
            roomInfoBuilder.append("-------------------------").append("\n");
        }
        roomInfoArea.setText(roomInfoBuilder.toString());   
    }

    // Method to view room details by room number
public static void viewSpecificRoomNumber() {
    Scanner scanner = new Scanner(System.in);
    int roomNum;
    boolean isValidRoomNum = false;

    while (!isValidRoomNum) {
        try {
            System.out.println("Please enter the room number you would like to view:");
            roomNum = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            System.out.println("-------------------------");
            for (HotelRoom room : HotelBookingSystem.rooms) {
                if (room.getRoomNum() == roomNum) {
                    isValidRoomNum = true;
                    System.out.println(room.toString());
                    for (Booking booking : HotelBookingSystem.bookings) {
                        if (booking.getRoom().getRoomNum() == roomNum) {
                            System.out.println(booking.getGuest().toString());
                            System.out.println("-------------------------");
                            return;
                        }
                    }
                }
            }

            if (!isValidRoomNum) {
                System.out.println("Invalid room number. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid room number.");
            scanner.nextLine(); // consume the invalid input
        }
    }
} 
    public static void listAllGuests() {
         System.out.println("-------------------------");
        System.out.println("List of all guests:");
         System.out.println("-------------------------");
        if (HotelBookingSystem.bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : HotelBookingSystem.bookings) {
                Guest guest = booking.getGuest();
                HotelRoom room = booking.getRoom();

                System.out.println(guest.toString() + " Ocupied in room " + room.getRoomNum());
                System.out.println("-------------------------");
        }
    }
}
 
        public static void findGuestByName() {
        Scanner scanner = new Scanner(System.in);
        String name = "";

        while (name.isEmpty()) {
            System.out.println("Please enter the name of the guest you would like to find:");
            name = scanner.nextLine().trim();

            if (!name.matches("[a-zA-Z]+")) {
                System.out.println("Invalid input. Please enter letters only.");
                name = "";
            }
        }

        boolean found = false;
        for (Booking booking : HotelBookingSystem.bookings) {
            if (booking.getGuest().getName().equalsIgnoreCase(name)) {
                System.out.println("-------------------------");
                System.out.println("Guest found in room " + booking.getRoom().getRoomNum() + ":");
                System.out.println(booking.getGuest().toString());
                System.out.println("-------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Guest not found.");
            System.out.println("-------------------------");
         }
        }

    public static void listUnoccupiedRooms() {
        List<HotelRoom> unoccupiedRooms = new ArrayList<>();

        //add JFrame
        JFrame frame = new JFrame("View Unoccupied Rooms");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //add JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        //add JLLabel
        JLabel titleLabel = new JLabel("List of unoccupied rooms:");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        //add JTextArea
        JTextArea roomInfoArea = new JTextArea();
        roomInfoArea.setEditable(false);
        roomInfoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(roomInfoArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        //add JButton
        JButton refreshButton = new JButton("Update");
        refreshButton.addActionListener(e -> refreshRoomInfo(roomInfoArea, unoccupiedRooms));
        mainPanel.add(refreshButton, BorderLayout.SOUTH);
        
        // Initial room information update
        refreshRoomInfo(roomInfoArea, unoccupiedRooms);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
  
     //print and update list
    private static void refreshRoomInfo(JTextArea roomInfoArea, List<HotelRoom> unoccupiedRooms) {
        // Clear the list before populating it again
        unoccupiedRooms.clear(); 
        
        //add unoccupied rooms to list
        for (HotelRoom room : HotelBookingSystem.rooms) {
            if (!room.isOccupied()) {
                unoccupiedRooms.add(room);
            }
        }
        
        //print unoccupied rooms
        StringBuilder roomInfoBuilder = new StringBuilder();
        if (!unoccupiedRooms.isEmpty()) {
            for (HotelRoom room : unoccupiedRooms) 
            {
                roomInfoBuilder.append(room.toString()).append("\n");
                roomInfoBuilder.append("-------------------------").append("\n");
            }
        }
        //print message if no unoccupied rooms
        else 
        {
            roomInfoBuilder.append("No unoccupied rooms found.").append("\n");
            roomInfoBuilder.append("-------------------------").append("\n");
        }
        roomInfoArea.setText(roomInfoBuilder.toString());
    }
    
public static void listOccupiedRooms() {
     System.out.println("-------------------------");
    System.out.println("List of occupied rooms:");
    System.out.println("-------------------------");
    boolean occupied = false;
    for (Booking booking : HotelBookingSystem.bookings) {
        if (booking.getRoom().isOccupied()) {
            System.out.println("Room " + booking.getRoom().getRoomNum() + " is occupied by");
            System.out.println("Guest name: " + booking.getGuest().getName());
            System.out.println("Guest email: " + booking.getGuest().getEmail());
            System.out.println("Guest phone: " + booking.getGuest().getPhone());    
            System.out.println("Check-in date: " + booking.getCheckInDate());
            System.out.println("Check-out date: " + booking.getCheckOutDate());
            System.out.println("Nightly stay booked for: " + booking.getNightlyStay() + " day(s)");
            System.out.println("Total amount due: $" + booking.getTotalCost());
            System.out.println("-------------------------");
            occupied = true;
        }
    }
    if (!occupied) {
        System.out.println("No rooms are currently occupied.");
        System.out.println("-------------------------");
        
    }
  }
}