package hotelbookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckOut extends JFrame implements ActionListener {

    private final JComboBox<Integer> roomComboBox;
    private final JButton checkOutButton;
    private final JButton updateButton;

     // Check if any room is occupied
    public static boolean checkoutcheck() {
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
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));
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
              if (booking == null) {
                JOptionPane.showMessageDialog(this, "The selected room is no longer available. Please choose another room.", "Error Message", JOptionPane.INFORMATION_MESSAGE);
                // Update combo box
                updateRoomComboBox();
                return;
            }   
                
                // set the room to unoccupied
                booking.getRoom().setOccupied(false);

                // remove the booking from the list of bookings
                HotelBookingSystem.bookings.remove(booking);

                // save the updated list of bookings to file              
                DataSave.removeBooking(booking);

                // Display checkout details
            JOptionPane.showMessageDialog(this,
                    "------------------------- \n"
                    + "Check-out successful!"
                    + "\n-------------------------\n" + booking.toString());
            
            // Update combo box
               updateRoomComboBox();
        }
    }

    // Update combo box with occupied rooms
    private void updateRoomComboBox() {
       roomComboBox.removeAllItems();

        List<Integer> occupiedRoomNumbers = new ArrayList<>();
        for (Booking booking : HotelBookingSystem.bookings) {
            if (booking.getRoom().isOccupied()) {
                occupiedRoomNumbers.add(booking.getRoom().getRoomNum());
            }
        }

        if (!occupiedRoomNumbers.isEmpty()) {
            Collections.sort(occupiedRoomNumbers);

            for (Integer roomNum : occupiedRoomNumbers) {
                roomComboBox.addItem(roomNum);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No rooms are available for Check-out. \nclosing window", "No Availability", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}