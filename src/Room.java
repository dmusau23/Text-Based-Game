/**Class: Room
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class is our blue print; contains details regarding the rooms.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private int roomNumber;
    private String roomName;
    private String description;
    private Map<String, Integer> exits;
    private ArrayList<Item> items;
    private Puzzle puzzle;
    boolean  visited;

    public Room(int roomNumber, String roomName, String description) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.puzzle = null;
        this.visited = false;
    }

    // --- Basic Getters ---
    public int getRoomNumber() {
        return roomNumber;
    }
    public String getRoomName() {
        return roomName;
    }
    public String getDescription() {
        return description;
    }

    // --- Exits Handling ---
    public void addExit(String direction, int destinationRoom) {
        exits.put(direction.toUpperCase(), destinationRoom);
    }

    public Integer getExit(String direction) {
        return exits.get(direction.toUpperCase());
    }

    public Map<String, Integer> getExits() {
        return exits;
    }

    // --- Items Handling ---
    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public ArrayList<Item> getItems() {
        return items;
    }

    // --- Puzzle Handling ---
    public Puzzle getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    // --- Display Info ---
    public void displayExits() {
        if (exits.isEmpty()) {
            System.out.println("No visible exits.");
        } else {
            System.out.print("Exits: ");
            for (String dir : exits.keySet()) {
                System.out.print(dir + " ");
            }
            System.out.println();
        }
    }

    public void visit() {
        visited = true;
    }
}