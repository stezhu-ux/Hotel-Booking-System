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

public class BookRoom extends JFrame implements ActionListener {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JComboBox<String> roomComboBox;
    private JButton bookButton;

    // Check if any room is unoccupied
    public static boolean checkAvailability() {
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
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10));

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
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number \n(NZ mobile numbers have 9-11 digits).");
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
                JOptionPane.showMessageDialog(this, "Please enter valid check-in and check-out dates (YYYY-MM-DD).");
                return;
            }

            if (checkInDate.isBefore(currentDate)) {
                JOptionPane.showMessageDialog(this, "Check-in date must be after the current date.");
                return;
            }

            if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                JOptionPane.showMessageDialog(this, "Check-out date cannot be before or the same as check-in date.");
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

            // Create guest and booking objects
            Guest guest = new Guest(name, email, phone);
            Booking booking = new Booking(guest, room, checkInDate, checkOutDate);
            room.setOccupied(true);
            HotelBookingSystem.bookings.add(booking);
            DataSave.saveBookings(HotelBookingSystem.bookings);

            // Display booking details
            JOptionPane.showMessageDialog(this, "------------------------- \nBooking successful!\n-------------------------\n" + booking.toString());
            dispose();
        }
    }
    
    // Update combo box with unoccupied rooms
    private void updateRoomComboBox() {
        roomComboBox.removeAllItems();
        for (HotelRoom room : HotelBookingSystem.rooms) {
            if (!room.isOccupied()) {
                roomComboBox.addItem(String.valueOf(room.getRoomNum()));
            }
        }
    }
}