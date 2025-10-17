/**Class: RoomDataHandler
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class contains the logic behind getting the data from a file and stores it in an HashMap.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoomDataHandler {
    private Map<Integer, Room> rooms = new HashMap<>();

    public RoomDataHandler() {}

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }

    public Map<Integer, Room> getAllRooms() {
        return rooms;
    }

    public void loadRooms(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", 4);
            if (parts.length < 4) {
                System.out.println("Invalid room data line: " + line);
                continue;
            }

            int roomNumber = Integer.parseInt(parts[0].trim());
            String roomName = parts[1].trim();
            String description = parts[2].trim();
            String exitsPart = parts[3].trim();

            Room room = new Room(roomNumber, roomName, description);

            String[] exits = exitsPart.split(",");
            for (String exit : exits) {
                String[] exitData = exit.split(":");
                if (exitData.length == 2) {
                    String direction = exitData[0].trim().toUpperCase();
                    int destRoom = Integer.parseInt(exitData[1].trim());
                    room.addExit(direction, destRoom);
                }
            }

            rooms.put(roomNumber, room);
        }
        br.close();
    }

    public void loadItems(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String name = null;
        String description = null;
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // skip blank lines

            if (name == null) {
                name = line.trim();
            } else if (description == null) {
                description = line.trim();
            } else {
                int roomNumber = Integer.parseInt(line.trim());
                Room room = rooms.get(roomNumber);
                if (room != null) {
                    Item item = new Item(name, description, roomNumber);
                    room.addItem(item);
                } else {
                    System.out.println("Warning: Room " + roomNumber + " not found for item " + name);
                }
                // reset for next item
                name = null;
                description = null;
            }
        }
        br.close();
    }

    public void loadPuzzles(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String name = null;
        String description = null;
        String answer = null;
        String line;
        int attempts = 0;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            if (name == null) {
                name = line.trim();
            } else if (description == null) {
                description = line.trim();
            } else if (answer == null) {
                answer = line.trim();
            } else {
                attempts = Integer.parseInt(line.trim());

                for (Room r : rooms.values()) {
                    if (r.getPuzzle() == null) {
                        r.setPuzzle(new Puzzle(name, description, answer, attempts));
                        break;
                    }
                }
                // Reset
                name = null;
                description = null;
                answer = null;
                attempts = 0;
            }
        }
        br.close();
    }
}