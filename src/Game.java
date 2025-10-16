/**Class: Game
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class contains the game's logic.
 */

import java.util.Scanner;

public class Game {
    private int currentRoomNumber;
    RoomDataHandler rooms;
    public Game(RoomDataHandler rooms){
        this.rooms = rooms;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        currentRoomNumber = 1;  // Start in room 1 (Entrance Hall)
        boolean playing = true;

        gameMap();
        while (playing) {
            Room currentRoom = rooms.getRooms(currentRoomNumber);

            // Display description
            System.out.println("\n" + currentRoom.name);
            System.out.println(currentRoom.description);

            if (currentRoom.visited) {
                System.out.println("Seems like you've been here before...");
            }
            currentRoom.visit();


            System.out.print("Available exits: ");
            for (String dir : currentRoom.exits.keySet()) {
                System.out.print(dir + " ");
            }
            System.out.println();

            System.out.print("Enter direction (NORTH, SOUTH, EAST, WEST) or QUIT: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("QUIT")) {
                playing = false;
                System.out.println("Thanks for playing!");
            } else if (currentRoom.exits.containsKey(input)) {
                currentRoomNumber = currentRoom.exits.get(input);
            } else {
                System.out.println("You can't go this way.");
            }
        }

        scanner.close();
    }

    public void gameMap(){
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
