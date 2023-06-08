package hotelbookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckOut extends JFrame implements ActionListener {

    private final JComboBox<Integer> roomComboBox;
    private final JButton checkOutButton;

     // Check if any room is occupied
    public static boolean checkAvailability() {
    for (HotelRoom room : HotelBookingSystem.rooms) {
        if (room.isOccupied()) {
            return true;
        }
    }
    return false;
}
   
    public CheckOut() {
        
        //add JFrame
        setTitle(" Check-out a Room");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //add JLabel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 10, 10));
        mainPanel.add(new JLabel("Select a room to check-out:"));
        
        //add ComboBox
        roomComboBox = new JComboBox<>();
        //add occupied rooms to combo box
        updateRoomComboBox();
        mainPanel.add(roomComboBox);

        //add JButton
        checkOutButton = new JButton("Check-out");
        checkOutButton.addActionListener(this);
        mainPanel.add(new JLabel());
        mainPanel.add(checkOutButton);
        add(mainPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkOutButton) {
            int roomNum = (int) roomComboBox.getSelectedItem();
            
            // find the booking with the given room number
            Booking booking = null;
            for (Booking b : HotelBookingSystem.bookings) {
                if (b.getRoom().getRoomNum() == roomNum) {
                    booking = b;
                    break;
                }
            }
                
                // set the room to unoccupied
                booking.getRoom().setOccupied(false);

                // remove the booking from the list of bookings
                HotelBookingSystem.bookings.remove(booking);

                // save the updated list of bookings to file
                DataSave.saveBookings(HotelBookingSystem.bookings);

                // Display checkout details
            JOptionPane.showMessageDialog(this,
                    "------------------------- \n"
                    + "Check-out successful!"
                    + "\n-------------------------\n" + booking.toString());
            dispose();
        }
    }

    // Update combo box with occupied rooms
    private void updateRoomComboBox() {
        roomComboBox.removeAllItems();
        for (Booking booking : HotelBookingSystem.bookings) {
            if (booking.getRoom().isOccupied()) {
                roomComboBox.addItem(booking.getRoom().getRoomNum());
            }
        }
    }
}