public class Item {
    private String name;
    private String description;
    private int roomNumber;

    public Item(String name, String description, int roomNumber) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return name;
    }
}
