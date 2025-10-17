/**Class: RoomTester
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class is the main point of entry; contains the main class.
 */
import java.io.IOException;
import java.util.*;

public class RoomTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path to Rooms.txt: ");
        String path = scanner.nextLine();

        try {
            RoomDataHandler handler = new RoomDataHandler();
            handler.loadRooms(path);
            handler.loadItems("items.txt");
            handler.loadPuzzles("puzzle.txt");

            Game game = new Game(handler);
            game.startGame();
        } catch (IOException e) {
            System.out.println("Error loading rooms: " + e.getMessage());
        }

        scanner.close();

    }
}
