package hotelbookingsystem;

import java.util.Scanner;
import java.util.InputMismatchException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
           mainPanel.add(refreshButton, BorderLayout.SOUTH);
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshRoomInfo(roomInfoArea);
                }
            }
        );
        
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
        //add JFrame
        JFrame frame = new JFrame("View Specific Room");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //add JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        //add JLabel
        JLabel roomNumLabel = new JLabel("Room Number:");
         inputPanel.add(roomNumLabel);
         
        //add JTextField
        JTextField roomNumField = new JTextField(10);
         inputPanel.add(roomNumField);
         
        //text field only accepts numbers
        roomNumField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || Character.isSpaceChar(c)) {
                    evt.consume();
                    }
                }
            }
        );
        //add JButton
        JButton searchButton = new JButton("Search");
         inputPanel.add(searchButton);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        //add JTextArea
        JTextArea roomInfoArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(roomInfoArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
         
        //button fuction
        searchButton.addActionListener(e -> {
            int roomNum = Integer.parseInt(roomNumField.getText());
            refreshSpecificRoomInfo(roomNum, roomInfoArea);
            }
        );
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    private static void refreshSpecificRoomInfo(int roomNum, JTextArea roomInfoArea) {
        StringBuilder roomInfoBuilder = new StringBuilder();
        boolean found = false;
        
        //print room info
        for (HotelRoom room : HotelBookingSystem.rooms) {
            if (room.getRoomNum() == roomNum) {
                roomInfoBuilder.append(room.toString()).append("\n");
                roomInfoBuilder.append("-------------------------").append("\n");
                found = true;
                
                //print room and guest info
                for (Booking booking : HotelBookingSystem.bookings) {
                    if (booking.getRoom().getRoomNum() == roomNum) {
                        roomInfoBuilder.append(booking.getGuest().toString()).append("\n");
                        roomInfoBuilder.append("-------------------------").append("\n");   
                    }
                }
            }
        }
        //print error message if room not found
        if (!found) {
            roomInfoBuilder.append("Room not found.").append("\n");
        }

        roomInfoArea.setText(roomInfoBuilder.toString());
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
            
        JFrame frame = new JFrame("Find Guest By Name");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        // Create JLabel
        JLabel nameLabel = new JLabel("Guest Name:");
        inputPanel.add(nameLabel);

        // Create JTextField
        JTextField nameField = new JTextField(10);
        //text field only accepts letters
        nameField.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isLetter(c)) {
            evt.consume();
                    }
                }
            }
        );
        inputPanel.add(nameField);

        // Create JButton
        JButton searchButton = new JButton("Search");
        inputPanel.add(searchButton);
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Create JTextArea
        JTextArea guestInfoArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(guestInfoArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button function
        searchButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            refreshGuestInfoByName(name, guestInfoArea);
            }
        );

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void refreshGuestInfoByName(String name, JTextArea guestInfoArea) {
        StringBuilder guestInfoBuilder = new StringBuilder();
        boolean found = false;
        
        //print guest info
        for (Booking booking : HotelBookingSystem.bookings) {
            if (booking.getGuest().getName().equalsIgnoreCase(name)) {
                guestInfoBuilder.append("Guest found in room ").append(booking.getRoom().getRoomNum()).append(":").append("\n");
                guestInfoBuilder.append("-------------------------").append("\n");
                guestInfoBuilder.append(booking.getGuest().toString()).append("\n");
                guestInfoBuilder.append("-------------------------").append("\n");

            // Find room info
            HotelRoom room = null;
            for (HotelRoom r : HotelBookingSystem.rooms) {
                if (r.getRoomNum() == booking.getRoom().getRoomNum()) {
                    room = r;
                }
            }

            // Print room info if found
            if (room != null) {
                guestInfoBuilder.append("Room Info:").append("\n");
                guestInfoBuilder.append(room.toString()).append("\n");
                guestInfoBuilder.append("-------------------------").append("\n");
            }
            found = true;
        }
    }

    // Print message if no guest found
    if (!found) {
        guestInfoBuilder.append("Guest not found.").append("\n");
        guestInfoBuilder.append("-------------------------").append("\n");
    }

    guestInfoArea.setText(guestInfoBuilder.toString());
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
        refreshButton.addActionListener(e -> refreshUnoccupiedRoomInfo(roomInfoArea, unoccupiedRooms));
        mainPanel.add(refreshButton, BorderLayout.SOUTH);
        
        // Initial room information update
        refreshUnoccupiedRoomInfo(roomInfoArea, unoccupiedRooms);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
  
     //print and update list
    private static void refreshUnoccupiedRoomInfo(JTextArea roomInfoArea, List<HotelRoom> unoccupiedRooms) {
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
        // Create JFrame
        JFrame frame = new JFrame("View Occupied Rooms");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create JLabel
        JLabel titleLabel = new JLabel("List of occupied rooms:");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create JTextArea
        JTextArea roomInfoArea = new JTextArea();
        roomInfoArea.setEditable(false);
        roomInfoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(roomInfoArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create JButton
        JButton refreshButton = new JButton("Update");
        refreshButton.addActionListener(e -> refreshOccupiedRoomInfo(roomInfoArea));
        mainPanel.add(refreshButton, BorderLayout.SOUTH);

        // Initial room information update
        refreshOccupiedRoomInfo(roomInfoArea);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    //print and update list
   private static void refreshOccupiedRoomInfo(JTextArea roomInfoArea) {
       StringBuilder roomInfoBuilder = new StringBuilder();
       boolean occupied = false;
       for (Booking booking : HotelBookingSystem.bookings) {
           if (booking.getRoom().isOccupied()) {
               roomInfoBuilder.append("Room ").append(booking.getRoom().getRoomNum()).append(" is occupied by").append("\n");
               roomInfoBuilder.append("Guest name: ").append(booking.getGuest().getName()).append("\n");
               roomInfoBuilder.append("Guest email: ").append(booking.getGuest().getEmail()).append("\n");
               roomInfoBuilder.append("Guest phone: ").append(booking.getGuest().getPhone()).append("\n");
               roomInfoBuilder.append("Check-in date: ").append(booking.getCheckInDate()).append("\n");
               roomInfoBuilder.append("Check-out date: ").append(booking.getCheckOutDate()).append("\n");
               roomInfoBuilder.append("Nightly stay booked for: ").append(booking.getNightlyStay()).append(" day(s)").append("\n");
               roomInfoBuilder.append("Total amount due: $").append(booking.getTotalCost()).append("\n");
               roomInfoBuilder.append("-------------------------").append("\n");
               occupied = true;
           }
       }
       if (!occupied) {
           roomInfoBuilder.append("No rooms are currently occupied.").append("\n");
           roomInfoBuilder.append("-------------------------").append("\n");
       }
       roomInfoArea.setText(roomInfoBuilder.toString());
       }
}