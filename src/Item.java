/**Class: Item
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: November 08, 2025
 *
 * This class represents items with combat properties (attack points, health points).
 */

public class Item {
    private String name;
    private String description;
    private int roomNumber;
    private int attackPoints;    // For equippable weapons
    private int healthPoints;    // For healing items

    public Item(String name, String description, int roomNumber, int attackPoints, int healthPoints) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.attackPoints = attackPoints;
        this.healthPoints = healthPoints;
    }

    // Backward compatibility constructor
    public Item(String name, String description, int roomNumber) {
        this(name, description, roomNumber, 0, 0);
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

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    @Override
    public String toString() {
        return name;
    }
}