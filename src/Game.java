/**Class: Game
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class contains the game's logic.
 */


import java.util.*;

public class Game {
    private int currentRoomNumber;
    private RoomDataHandler rooms;
    private boolean playing;
    private ArrayList<Item> inventory;

    public Game(RoomDataHandler rooms) {
        this.rooms = rooms;
        this.currentRoomNumber = 1; // Start in Entrance Hall
        this.playing = true;
        this.inventory = new ArrayList<>();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        displayMap();

        while (playing) {
            Room currentRoom = rooms.getRoom(currentRoomNumber);
            displayRoom(currentRoom);

            // Trigger puzzle if exists
            handlePuzzle(currentRoom, scanner);

            System.out.print("\nCommand > ");
            String input = scanner.nextLine().trim().toLowerCase();

            handleCommand(input, currentRoom);
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    // ---------- ROOM AND MOVEMENT ----------

    private void displayRoom(Room room) {
        System.out.println("\n=== " + room.getRoomName() + " ===");
        System.out.println(room.getDescription());
        if (room.visited) {
            System.out.println("(You’ve been here before.)");
        }
        room.visit();
        room.displayExits();
    }

    private void move(String direction, Room currentRoom) {
        Integer nextRoomId = currentRoom.getExit(direction.toUpperCase());
        if (nextRoomId == null) {
            System.out.println("You can’t go that way.");
        } else {
            currentRoomNumber = nextRoomId;
            System.out.println("You move " + direction.toUpperCase() + "...");
        }
    }

    // ---------- PUZZLE LOGIC ----------

    private void handlePuzzle(Room currentRoom, Scanner scanner) {
        Puzzle puzzle = currentRoom.getPuzzle();
        if (puzzle != null && !puzzle.isSolved()) {
            System.out.println("\nYou encounter a puzzle: " + puzzle.getDescription());
            while (!puzzle.isSolved()) {
                System.out.print("Answer: ");
                String answer = scanner.nextLine();
                System.out.println(puzzle.trySolve(answer));
                if (puzzle.isSolved() || answer.equalsIgnoreCase("quit")) break;
            }
        }
    }

    // ---------- ITEM & INVENTORY LOGIC ----------

    private void explore(Room currentRoom) {
        if (currentRoom.getItems().isEmpty()) {
            System.out.println("There are no items here.");
        } else {
            System.out.println("You see:");
            for (Item item : currentRoom.getItems()) {
                System.out.println("- " + item.getName());
            }
        }
    }

    private void pickup(String name, Room currentRoom) {
        Item found = null;
        for (Item i : currentRoom.getItems()) {
            if (i.getName().equalsIgnoreCase(name)) {
                found = i;
                break;
            }
        }
        if (found != null) {
            inventory.add(found);
            currentRoom.removeItem(found);
            System.out.println(found.getName() + " has been picked up from the room and successfully added to the player inventory.");
        } else {
            System.out.println("No such item in this room.");
        }
    }

    private void inspect(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) {
                System.out.println(i.getDescription());
                return;
            }
        }
        System.out.println("You don’t have that item.");
    }

    private void drop(String name, Room currentRoom) {
        Item found = null;
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) {
                found = i;
                break;
            }
        }
        if (found != null) {
            inventory.remove(found);
            currentRoom.addItem(found);
            System.out.println(found.getName() + " has been dropped successfully from the player inventory and is in the current room.");
        } else {
            System.out.println("You don’t have that item.");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You didn’t pickup any items yet.");
        } else {
            System.out.println("Inventory:");
            for (Item i : inventory) {
                System.out.println("- " + i.getName());
            }
        }
    }

    // ---------- COMMAND HANDLER ----------

    private void handleCommand(String input, Room currentRoom) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];

        switch (command) {
            case "north":
            case "south":
            case "east":
            case "west":
                move(command, currentRoom);
                break;
            case "explore":
                explore(currentRoom);
                break;
            case "pickup":
                if (parts.length > 1) pickup(parts[1], currentRoom);
                else System.out.println("Pick up what?");
                break;
            case "inspect":
                if (parts.length > 1) inspect(parts[1]);
                else System.out.println("Inspect what?");
                break;
            case "drop":
                if (parts.length > 1) drop(parts[1], currentRoom);
                else System.out.println("Drop what?");
                break;
            case "inventory":
                showInventory();
                break;
            case "help":
                showHelp();
                break;
            case "map":
                displayMap();
                break;
            case "quit":
                playing = false;
                break;
            default:
                System.out.println("Unknown command. Type 'help' for options.");
        }
    }

    // ---------- UTILITY ----------

    private void showHelp() {
        System.out.println("\nCommands:");
        System.out.println("NORTH, SOUTH, EAST, WEST - move between rooms");
        System.out.println("EXPLORE - look around for items");
        System.out.println("PICKUP [item] - collect an item");
        System.out.println("INSPECT [item] - view an item description");
        System.out.println("DROP [item] - drop an item");
        System.out.println("INVENTORY - show what you’re carrying");
        System.out.println("MAP - show the mansion layout");
        System.out.println("HELP - show this message");
        System.out.println("QUIT - exit the game");
    }

    private void displayMap() {
        System.out.println("\t\t\t\t\t+---------+");
        System.out.println("\t\t\t\t\t|    6    |");
        System.out.println("\t+-------------------  ----+");
        System.out.println("\t|       |       |         |");
        System.out.println("\t|   2       4       5     |");
        System.out.println("\t|       |       |         |");
        System.out.println("\t+---  ------  ------------+");
        System.out.println("\t|       |       |");
        System.out.println("\t|    1      3   |");
        System.out.println("\t|       |       |");
        System.out.println("\t+---------------+");
        System.out.println("\n [1] Entrance Hall, [2] Library, [3] Kitchen, [4] Conservatory, [5] Ball Room, [6] Tower Room");
    }
}