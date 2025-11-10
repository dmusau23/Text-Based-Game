/**Class: RoomDataHandler
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: November 08, 2025
 *
 * This class contains the logic for loading data from files (rooms, items, puzzles, monsters).
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

    /**
     * Load items with combat properties from file
     * Format: name, description, roomNumber, attackPoints, healthPoints
     */
    public void loadItems(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String name = null;
        String description = null;
        String roomLine = null;
        String attackLine = null;
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // skip blank lines

            if (name == null) {
                name = line.trim();
            } else if (description == null) {
                description = line.trim();
            } else if (roomLine == null) {
                roomLine = line.trim();
            } else if (attackLine == null) {
                attackLine = line.trim();
            } else {
                // We now have all 5 lines
                int roomNumber = Integer.parseInt(roomLine);
                int attackPoints = Integer.parseInt(attackLine);
                int healthPoints = Integer.parseInt(line.trim());

                Room room = rooms.get(roomNumber);
                if (room != null) {
                    Item item = new Item(name, description, roomNumber, attackPoints, healthPoints);
                    room.addItem(item);
                } else {
                    System.out.println("Warning: Room " + roomNumber + " not found for item " + name);
                }

                // Reset for next item
                name = null;
                description = null;
                roomLine = null;
                attackLine = null;
            }
        }
        br.close();
    }

    public void loadPuzzles(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String name = null;
        String description = null;
        String answer = null;
        String attemptsLine = null;
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            if (name == null) {
                name = line.trim();
            } else if (description == null) {
                description = line.trim();
            } else if (answer == null) {
                answer = line.trim();
            } else if (attemptsLine == null) {
                attemptsLine = line.trim();
            } else {
                // We have all data including room number
                int attempts = Integer.parseInt(attemptsLine);
                int roomNumber = Integer.parseInt(line.trim());

                Room room = rooms.get(roomNumber);
                if (room != null) {
                    room.setPuzzle(new Puzzle(name, description, answer, attempts));
                } else {
                    System.out.println("Warning: Room " + roomNumber + " not found for puzzle " + name);
                }

                // Reset
                name = null;
                description = null;
                answer = null;
                attemptsLine = null;
            }
        }
        br.close();
    }

    /**
     * Load monsters from file
     * Format (per monster, one field per line):
     * - name
     * - description
     * - health
     * - attackDamage
     * - threshold (0.0-1.0 for critical hit chance)
     * - roomNumber
     */
    public void loadMonsters(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String name = null;
        String description = null;
        String healthLine = null;
        String attackLine = null;
        String thresholdLine = null;
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            if (name == null) {
                name = line.trim();
            } else if (description == null) {
                description = line.trim();
            } else if (healthLine == null) {
                healthLine = line.trim();
            } else if (attackLine == null) {
                attackLine = line.trim();
            } else if (thresholdLine == null) {
                thresholdLine = line.trim();
            } else {
                // We have all 6 lines
                int health = Integer.parseInt(healthLine);
                int attackDamage = Integer.parseInt(attackLine);
                double threshold = Double.parseDouble(thresholdLine);
                int roomNumber = Integer.parseInt(line.trim());

                Room room = rooms.get(roomNumber);
                if (room != null) {
                    Monster monster = new Monster(name, description, health, attackDamage, threshold, roomNumber);
                    room.setMonster(monster);
                } else {
                    System.out.println("Warning: Room " + roomNumber + " not found for monster " + name);
                }

                // Reset for next monster
                name = null;
                description = null;
                healthLine = null;
                attackLine = null;
                thresholdLine = null;
            }
        }
        br.close();
    }
}