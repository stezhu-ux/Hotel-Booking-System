package hotelbookingsystem;

public class DeluxeRoom extends HotelRoom {
    private int numBeds;
    private BedType bedType;
 
    public DeluxeRoom(int roomNum, double nightlyRate, int numBeds, BedType bedType) {
        super(roomNum, "Deluxe", nightlyRate);
        this.numBeds = numBeds;
        this.bedType = bedType;
      
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    @Override
    public String toString() {
        return "Room " + getRoomNum() + "\n" + "Type " + getType() + "\n" + numBeds + " " + bedType + " bed(s)" + "\n" + "Price $" + getNightlyRate() + " per night" + "\n" + "Room is " + (isAvailable() ? "Available" : "Occupied");
  
    }
}