package hotelbookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookRoom extends JFrame implements ActionListener {

    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField phoneField;
    private final JTextField checkInField;
    private final JTextField checkOutField;
    private final JComboBox<String> roomComboBox;
    private final JButton bookButton;
    private final JButton updateButton;

    // Check if any room is unoccupied
    public static boolean bookroomcheck() {
        for (HotelRoom room : HotelBookingSystem.rooms) {
            if (!room.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    public BookRoom() {
        setTitle("Book a Room");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 2, 10, 10));

        mainPanel.add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>();
        //add unoccupied rooms to combo box
        updateRoomComboBox();
        mainPanel.add(roomComboBox);
        
        mainPanel.add(new JLabel("Guest Name:"));
        nameField = new JTextField();
        //text field only accepts letters
        nameField.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isLetter(c)) {
            evt.consume();
                }
            }
        });
    mainPanel.add(nameField);

        mainPanel.add(new JLabel("Guest Email:"));
        emailField = new JTextField();
        mainPanel.add(emailField);

        mainPanel.add(new JLabel("Guest Phone:"));
        phoneField = new JTextField();
        //text field only accepts numbers
        phoneField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || Character.isSpaceChar(c)) {
                    evt.consume();
                }
            }
        });
        mainPanel.add(phoneField);

        mainPanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        checkInField = new JTextField();
        mainPanel.add(checkInField);

        mainPanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"));
        checkOutField = new JTextField();
        mainPanel.add(checkOutField);

        bookButton = new JButton("Book");
        bookButton.addActionListener(this);
        mainPanel.add(new JLabel());
        mainPanel.add(bookButton);

       updateButton = new JButton("Update");
       updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRoomComboBox();
            }
        }
       );
        mainPanel.add(new JLabel());
        mainPanel.add(updateButton);

        add(mainPanel);
    }
    
    public void actionPerformed(ActionEvent e) {     
        if (e.getSource() == bookButton) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String checkInString = checkInField.getText().trim();
            String checkOutString = checkOutField.getText().trim();

            // checks and prompt user to enter valid guest name
            if (name.isEmpty() || !name.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid guest name.\n(letters only)", "Error Message", JOptionPane.INFORMATION_MESSAGE);  
                return;
            }

            //checks and prompt user to enter valid guest email
            Pattern emailPattern = Pattern.compile("^(?i)[\\w-\\.]+@(gmail\\.com|hotmail\\.com|hotmail\\.co\\.nz|outlook\\.co\\.nz|yahoo\\.com|AOL\\.com|iCloud\\.com|aut.ac.nz)$");
            Matcher emailMatcher = emailPattern.matcher(email);
            if (!emailMatcher.matches()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address with a domain of either \naut.ac.nz \ngmail.com \nhotmail.com \nhotmail.co.nz \noutlook.com \noutlook.co.nz \nAOL.com \nyahoo.com \niCloud.com", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //checks and prompt user to enter valid guest phone number
            if (!phone.matches("\\d{9,11}")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number \n(NZ mobile numbers have 9-11 digits).", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate checkInDate;
            LocalDate checkOutDate;

            //checks and prompt user to enter valid check-in/out date
            try {
                checkInDate = LocalDate.parse(checkInString);
                checkOutDate = LocalDate.parse(checkOutString);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid check-in and check-out dates (YYYY-MM-DD).", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (checkInDate.isBefore(currentDate)) {
                JOptionPane.showMessageDialog(this, "Check-in date must be after the current date.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                JOptionPane.showMessageDialog(this, "Check-out date cannot be before or the same as check-in date.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Get the selected room
            String selectedRoom = roomComboBox.getSelectedItem().toString();
            HotelRoom room = null;
            for (HotelRoom r : HotelBookingSystem.rooms) {
                if (r.getRoomNum() == Integer.parseInt(selectedRoom) && !r.isOccupied()) {
                    room = r;
                    break;
                }
            }
            if (room == null) {
                JOptionPane.showMessageDialog(this, "The selected room is no longer available. Please choose another room.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                // Update combo box
                updateRoomComboBox();
                return;
            }   
            
            // Create guest and booking objects
            Guest guest = new Guest(name, email, phone);
            Booking booking = new Booking(guest, room, checkInDate, checkOutDate);
            
             // set the room to occupied
            room.setOccupied(true);
            
            // remove the booking from the list of bookings
            HotelBookingSystem.bookings.add(booking);
            
            // save the updated list of bookings to file
            DataSave.saveBookings(HotelBookingSystem.bookings);     
            
            // Display booking details
            JOptionPane.showMessageDialog(this,
                    "------------------------- \n"
                    + "Booking successful!"
                    + "\n-------------------------\n" + booking.toString());
            
            // Clear the text fields
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            checkInField.setText("");
            checkOutField.setText("");
            
             // Update combo box
                updateRoomComboBox();
        }
    }
    
    // Update combo box with unoccupied rooms
    private void updateRoomComboBox() {
        roomComboBox.removeAllItems();
        
        List<Integer> unoccupiedRoomNumbers = new ArrayList<>();
        for (HotelRoom room : HotelBookingSystem.rooms) {
            if (!room.isOccupied()) {
                unoccupiedRoomNumbers.add(room.getRoomNum());
            }
        }

        if (!unoccupiedRoomNumbers.isEmpty()) {
            Collections.sort(unoccupiedRoomNumbers);

            for (Integer roomNum : unoccupiedRoomNumbers) {
                roomComboBox.addItem(String.valueOf(roomNum));
            }
        } else {
            JOptionPane.showMessageDialog(this, "No rooms are available for booking. \nclosing window", "No Availability", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}