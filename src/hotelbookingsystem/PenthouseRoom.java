package hotelbookingsystem;

public class PenthouseRoom extends HotelRoom {
    
    private int numBeds;
    private BedType bedType;
    private boolean hasKitchen;

    public PenthouseRoom(int roomNum, double nightlyRate, int numBeds, BedType bedType, boolean hasKitchen) {
        super(roomNum, "Penthouse", nightlyRate);
        this.numBeds = numBeds;
        this.bedType = bedType;
        this.hasKitchen = hasKitchen;
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

    public boolean hasKitchen() {
        return hasKitchen;
    }

    public void setHasKitchen(boolean hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    @Override
    public String toString() {
        return "Room " + getRoomNum() + "\n" + "Type " + getType() + "\n" + numBeds + " " + bedType + " bed(s)" + "\n" + "Price $" + getNightlyRate() + " per night" + "\n" + "Kitchen: " + hasKitchen + "\n" + "Room is " + (isAvailable() ? "Available" : "Occupied");
    }
}