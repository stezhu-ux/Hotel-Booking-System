package hotelbookingsystem;

public abstract class HotelRoom {
    private int roomNum;
    private String type;
    private double nightlyRate;
    private boolean isOccupied;

    public HotelRoom(int roomNum, String type, double nightlyRate) {
        this.roomNum = roomNum;
        this.type = type;
        this.nightlyRate = nightlyRate;
        this.isOccupied = false;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getNightlyRate() {
        return nightlyRate;
    }

    public void setNightlyRate(double nightlyRate) {
        this.nightlyRate = nightlyRate;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    @Override
    public String toString() {
        return "Room " + roomNum + " type " + type + " price $" + nightlyRate;
    }
}