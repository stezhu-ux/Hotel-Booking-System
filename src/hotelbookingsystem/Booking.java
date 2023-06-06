package hotelbookingsystem;
import java.time.LocalDate;

public class Booking {
    private Guest guest;
    private HotelRoom room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalCost;
    private int nightlyStay;

    public Booking(Guest guest, HotelRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.nightlyStay = calculateNightlyStay();
        this.totalCost = calculateTotalCost();
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public HotelRoom getRoom() {
        return room;
    }

    public void setRoom(HotelRoom room) {
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        this.nightlyStay = calculateNightlyStay();
        this.totalCost = calculateTotalCost();
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
        this.nightlyStay = calculateNightlyStay();
        this.totalCost = calculateTotalCost();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public int getNightlyStay() {
        return nightlyStay;
    }
    
    public void setNightlyStay(int nightlyStay) {
        this.nightlyStay = nightlyStay;
    }

    private int calculateNightlyStay() {
        return (int) checkInDate.until(checkOutDate).getDays();
    }

    private double calculateTotalCost() {
        return nightlyStay * room.getNightlyRate();
    }

    @Override
    public String toString() {
        return "Booking Information:\n" +
                "Guest Name: " + guest.getName() + "\n" +
                "Guest Email: " + guest.getEmail() + "\n" +
                "Guest Phone: " + guest.getPhone() + "\n" +
                "Room Number: " + room.getRoomNum() + "\n" +
                "Check-in Date: " + checkInDate + "\n" +
                "Check-out Date: " + checkOutDate + "\n" +
                "Nightly Stay: " + nightlyStay + " day(s)" + "\n" +
                "Total Cost: $" + getTotalCost();
    }
}
