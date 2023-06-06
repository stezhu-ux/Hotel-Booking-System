package hotelbookingsystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelBookingSystem extends JFrame implements ActionListener {

    static ArrayList<HotelRoom> rooms = new ArrayList<>();
    public static ArrayList<Booking> bookings = new ArrayList<>();
    
    private static Scanner scanner = new Scanner(System.in); 

    private JButton bookButton;
    private JButton checkoutButton;
    private JButton viewAllButton;
    private JButton viewUnoccupiedButton;
    private JButton viewOccupiedButton;
    private JButton viewSpecificButton;
    private JButton findGuestButton;
    private JButton listGuestsButton;
    private JButton exitButton;

    public HotelBookingSystem() {
        setTitle("Hotel Booking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(9, 1));
        
        bookButton = new JButton("Book a Room");
        bookButton.addActionListener(this);
        mainPanel.add(bookButton);
        
        checkoutButton = new JButton("Check-out a Room");
        checkoutButton.addActionListener(this);
        mainPanel.add(checkoutButton);

        viewAllButton = new JButton("View All Rooms and Details");
        viewAllButton.addActionListener(this);
        mainPanel.add(viewAllButton);

        viewUnoccupiedButton = new JButton("View All Unoccupied Rooms");
        viewUnoccupiedButton.addActionListener(this);
        mainPanel.add(viewUnoccupiedButton);

        viewOccupiedButton = new JButton("View All Occupied Rooms");
        viewOccupiedButton.addActionListener(this);
        mainPanel.add(viewOccupiedButton);

        viewSpecificButton = new JButton("View Specific Room");
        viewSpecificButton.addActionListener(this);
        mainPanel.add(viewSpecificButton);

        findGuestButton = new JButton("Find Guest by Name");
        findGuestButton.addActionListener(this);
        mainPanel.add(findGuestButton);

        listGuestsButton = new JButton("List All Guests");
        listGuestsButton.addActionListener(this);
        mainPanel.add(listGuestsButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        mainPanel.add(exitButton);

        add(mainPanel);
    }

        public void actionPerformed(ActionEvent e) {
            
          if (e.getSource() == bookButton) {
            if (BookRoom.checkAvailability()) 
            {
                SwingUtilities.invokeLater(() -> {
                    BookRoom bookRoom = new BookRoom();
                    bookRoom.setVisible(true);
                    }
                );
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Sorry, there are no rooms available.");
            }
        }
        
        else if (e.getSource() == checkoutButton) {
            if (CheckOut.checkAvailability()) {
                SwingUtilities.invokeLater(() -> {
                    CheckOut checkOut = new CheckOut();
                    checkOut.setVisible(true);
                    }
                );
            } else {
                JOptionPane.showMessageDialog(this, "Sorry, all rooms are unoccupied.");
            }
        }
        
        else if (e.getSource() == viewAllButton) 
        {
            RoomInfo.viewAllRooms();
        }
        
        else if (e.getSource() == viewUnoccupiedButton) 
        {
            RoomInfo.listUnoccupiedRooms();
        }
        
        else if (e.getSource() == viewOccupiedButton) 
        {
            RoomInfo.listOccupiedRooms();
        }
        
        else if (e.getSource() == viewSpecificButton) 
        {
            RoomInfo.viewSpecificRoomNumber();
        } 
        
        else if (e.getSource() == findGuestButton) 
        {
            RoomInfo.findGuestByName();
        } 
        
        else if (e.getSource() == listGuestsButton) 
        {
            RoomInfo.listAllGuests();
        }
        
        else if (e.getSource() == exitButton) 
        {
            System.out.println("Exiting program...");
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
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
        HotelBookingSystem GUI = new HotelBookingSystem();
        GUI.setVisible(true);
    }
}