package hotelbookingsystem;

import java.util.ArrayList;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelBookingSystem extends JFrame implements ActionListener {

    static ArrayList<HotelRoom> rooms = new ArrayList<>();
    public static ArrayList<Booking> bookings = new ArrayList<>();

    private final JButton bookButton;
    private final JButton checkoutButton;
    private final JButton viewAllButton;
    private final JButton viewUnoccupiedButton;
    private final JButton viewOccupiedButton;
    private final JButton viewSpecificButton;
    private final JButton findGuestButton;
    private final JButton listGuestsButton;
    private final JButton exitButton;

    public HotelBookingSystem() {
        setTitle("Hotel Booking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 2));
        
        JLabel titleLabel = new JLabel("Hotel Booking System");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        mainPanel.add(titleLabel);  
        
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
            if (BookRoom.bookroomcheck()) 
            {
                SwingUtilities.invokeLater(() -> {
                    BookRoom bookRoom = new BookRoom();
                    bookRoom.setVisible(true);
                    }
                );
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Sorry, there are no rooms available.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        else if (e.getSource() == checkoutButton) {
            if (CheckOut.checkoutcheck()) {
                SwingUtilities.invokeLater(() -> {
                    CheckOut checkOut = new CheckOut();
                    checkOut.setVisible(true);
                    }
                );
            } else {
                JOptionPane.showMessageDialog(this, "Sorry, all rooms are unoccupied.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
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
        DataRead dataRead = new DataRead();
        bookings.addAll(dataRead.loadBookings());
       
        HotelBookingSystem GUI = new HotelBookingSystem();
        GUI.setVisible(true);
    }
}