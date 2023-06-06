package hotelbookingsystem;

public class StandardRoom extends HotelRoom {
    private BedType bedType;

    public StandardRoom(int roomNum, double nightlyRate, BedType bedType) {
        super(roomNum, "Standard", nightlyRate);
        this.bedType = bedType;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    @Override
    public String toString() {
        return "Room " + getRoomNum() + "\n" + "Type " + getType() + "\n" + getBedType() + " bed" + "\n" + "Price $" + getNightlyRate() + " per night" + "\n" + "Room is " + (isAvailable() ? "Available" : "Occupied");
    }
}


