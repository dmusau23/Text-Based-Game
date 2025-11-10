/**Class: RoomTester
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: November 08, 2025
 *
 * This class is the main point of entry; contains the main class.
 */

import java.io.IOException;
import java.util.*;

public class RoomTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path to rooms.txt: ");
        String roomsPath = scanner.nextLine();

        try {
            RoomDataHandler handler = new RoomDataHandler();

            // Load all game data
            handler.loadRooms(roomsPath);
            handler.loadItems("items.txt");
            handler.loadPuzzles("puzzle.txt");
            handler.loadMonsters("monster.txt");

            System.out.println("\nGame data loaded successfully!");
            System.out.println("Starting game...\n");

            Game game = new Game(handler);
            game.startGame();

        } catch (IOException e) {
            System.out.println("Error loading game data: " + e.getMessage());
            System.out.println("Make sure all required files are in the correct location:");
            System.out.println("  - rooms.txt");
            System.out.println("  - items.txt");
            System.out.println("  - puzzle.txt");
            System.out.println("  - monster.txt");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}