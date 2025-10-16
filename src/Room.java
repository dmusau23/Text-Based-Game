/**Class: Room
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class is our blue print; contains details regarding the rooms.
 */

import java.util.HashMap;
import java.util.Map;
//A Room class to represent each room in the game
public class Room {
int roomNumber;
String name;
String description;
boolean visited;
Map<String, Integer> exits; // Directions and corresponding room numbers

// Constructor
public Room(int roomNumber, String name, String description) {
this.roomNumber = roomNumber;
this.name = name;
this.description = description;
this.visited = false;
this.exits = new HashMap<>();
}

// Method to add an exit
public void addExit(String direction, int roomNumber) {
exits.put(direction, roomNumber);
}

// Method to mark the room as visited
public void visit() {
visited = true;
}
}
