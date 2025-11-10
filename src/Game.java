/**Class: Game
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: November 08, 2025
 *
 * This class contains the game's logic including combat system.
 */

import java.util.*;

public class Game {
    private int currentRoomNumber;
    private RoomDataHandler rooms;
    private boolean playing;
    private Player player;
    private boolean inCombat;
    private Monster currentMonster;

    public Game(RoomDataHandler rooms) {
        this.rooms = rooms;
        this.currentRoomNumber = 1; // Start in Entrance Hall
        this.playing = true;
        this.player = new Player(100, 10); // 100 HP, 10 base damage
        this.inCombat = false;
        this.currentMonster = null;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        displayMap();
        System.out.println("\n🎮 Welcome to the Text Adventure Game!");
        player.displayStats();

        while (playing) {
            Room currentRoom = rooms.getRoom(currentRoomNumber);
            displayRoom(currentRoom);

            // Trigger puzzle if exists
            handlePuzzle(currentRoom, scanner);

            // Check for monster encounter
            if (currentRoom.getMonster() != null && currentRoom.getMonster().isAlive()) {
                handleMonsterEncounter(currentRoom, scanner);
                if (!player.isAlive()) {
                    handleGameOver(scanner);
                    continue;
                }
            }

            if (!inCombat) {
                System.out.print("\nCommand > ");
                String input = scanner.nextLine().trim().toLowerCase();
                handleCommand(input, currentRoom);
            }
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    // ---------- ROOM AND MOVEMENT ----------

    private void displayRoom(Room room) {
        System.out.println("\n=== " + room.getRoomName() + " ===");
        System.out.println(room.getDescription());
        if (room.visited) {
            System.out.println("(You've been here before.)");
        }
        room.visit();
        room.displayExits();

        // Show monster if present
        if (room.getMonster() != null && room.getMonster().isAlive()) {
            System.out.println("⚠️  A " + room.getMonster().getName() + " is here!");
        }
    }

    private void move(String direction, Room currentRoom) {
        if (inCombat) {
            System.out.println("You can't leave during combat!");
            return;
        }

        Integer nextRoomId = currentRoom.getExit(direction.toUpperCase());
        if (nextRoomId == null) {
            System.out.println("You can't go that way.");
        } else {
            currentRoomNumber = nextRoomId;
            System.out.println("You move " + direction.toUpperCase() + "...");
        }
    }

    // ---------- PUZZLE LOGIC ----------

    private void handlePuzzle(Room currentRoom, Scanner scanner) {
        Puzzle puzzle = currentRoom.getPuzzle();
        if (puzzle != null && !puzzle.isSolved()) {
            System.out.println("\n🧩 You encounter a puzzle: " + puzzle.getDescription());
            while (!puzzle.isSolved()) {
                System.out.print("Answer (or 'skip' to ignore): ");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("skip")) {
                    System.out.println("You chose to skip the puzzle.");
                    break;
                }
                System.out.println(puzzle.trySolve(answer));
                if (puzzle.isSolved() || (puzzle.getRemainingAttempts() ==0)) break;
            }
        }
    }

    // ---------- MONSTER LOGIC ----------

    private void handleMonsterEncounter(Room currentRoom, Scanner scanner) {
        Monster monster = currentRoom.getMonster();
        System.out.println("\n⚔️  You encounter a " + monster.getName() + "!");
        System.out.println("What do you do?");
        System.out.println("1. EXAMINE - Learn about the monster");
        System.out.println("2. ATTACK - Engage in combat");
        System.out.println("3. IGNORE - Flee and never return");

        while (true) {
            System.out.print("\nChoice > ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "examine":
                case "1":
                    monster.examine();
                    System.out.println("\nWhat do you do?");
                    System.out.println("1. ATTACK - Engage in combat");
                    System.out.println("2. IGNORE - Flee");
                    break;
                case "attack":
                case "2":
                    engageCombat(monster, currentRoom, scanner);
                    return;
                case "ignore":
                case "3":
                    System.out.println("You chose to flee. The " + monster.getName() + " disappears forever.");
                    currentRoom.removeMonster();
                    return;
                default:
                    System.out.println("Invalid choice. Type 'examine', 'attack', or 'ignore'.");
            }
        }
    }

    private void engageCombat(Monster monster, Room currentRoom, Scanner scanner) {
        System.out.println("\n⚔️  COMBAT INITIATED ⚔️");
        inCombat = true;
        currentMonster = monster;

        while (player.isAlive() && monster.isAlive()) {
            // Display status
            System.out.println("\n--- Combat Status ---");
            System.out.println("Your HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(monster.getName() + " HP: " + monster.getHealth());
            System.out.println("\nAvailable commands:");
            System.out.println("- ATTACK: Attack the monster");
            System.out.println("- HEAL [item]: Use a healing item");
            System.out.println("- EQUIP [item]: Equip a weapon");
            System.out.println("- UNEQUIP: Unequip current weapon");
            System.out.println("- INVENTORY: View your items");

            System.out.print("\nCombat Action > ");
            String action = scanner.nextLine().trim().toLowerCase();
            String[] parts = action.split(" ", 2);
            String command = parts[0];

            boolean playerTurnTaken = false;

            switch (command) {
                case "attack":
                    int playerDamage = player.attack();
                    monster.takeDamage(playerDamage);
                    System.out.println("You attack " + monster.getName() + " for " + playerDamage + " damage!");
                    playerTurnTaken = true;
                    break;
                case "heal":
                    if (parts.length > 1) {
                        String result = player.heal(parts[1]);
                        System.out.println(result);
                        playerTurnTaken = true;
                    } else {
                        System.out.println("Heal with what item?");
                    }
                    break;
                case "equip":
                    if (parts.length > 1) {
                        String result = player.equip(parts[1]);
                        System.out.println(result);
                    } else {
                        System.out.println("Equip what?");
                    }
                    break;
                case "unequip":
                    System.out.println(player.unequip());
                    break;
                case "inventory":
                    player.showInventory();
                    break;
                default:
                    System.out.println("Invalid combat command.");
            }

            // Check if monster is dead
            if (!monster.isAlive()) {
                System.out.println("\n🎉 Victory! You defeated the " + monster.getName() + "!");
                currentRoom.removeMonster();
                inCombat = false;
                currentMonster = null;
                return;
            }

            // Monster's turn (only if player took an action that uses a turn)
            if (playerTurnTaken) {
                int monsterDamage = monster.attack();
                player.takeDamage(monsterDamage);
                System.out.println(monster.getName() + " attacks you for " + monsterDamage + " damage!");

                // Check if player is dead
                if (!player.isAlive()) {
                    System.out.println("\n💀 You have been defeated...");
                    inCombat = false;
                    currentMonster = null;
                    return;
                }
            }
        }
    }

    private void handleGameOver(Scanner scanner) {
        System.out.println("\n========== GAME OVER ==========");
        System.out.println("Your adventure has come to an end.");
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. EXIT - Quit the game");
        System.out.println("2. START NEW GAME - Begin again");

        while (true) {
            System.out.print("\nChoice > ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "exit":
                case "1":
                    playing = false;
                    return;
                case "start new game":
                case "2":
                    restartGame();
                    return;
                default:
                    System.out.println("Invalid choice. Type '1' for EXIT or '2' for START NEW GAME.");
            }
        }
    }

    private void restartGame() {
        currentRoomNumber = 1;
        player.reset();
        inCombat = false;
        currentMonster = null;

        // Reset all monsters in rooms
        for (Room room : rooms.getAllRooms().values()) {
            Monster monster = room.getMonster();
            if (monster != null) {
                // Reload monster data would go here
                // For now, we'll just reset the room's monster reference
            }
        }

        System.out.println("\n🎮 Starting new game...\n");
        player.displayStats();
    }

    // ---------- ITEM & INVENTORY LOGIC ----------

    private void explore(Room currentRoom) {
        if (currentRoom.getItems().isEmpty()) {
            System.out.println("There are no items here.");
        } else {
            System.out.println("You see:");
            for (Item item : currentRoom.getItems()) {
                String info = "";
                if (item.getAttackPoints() > 0) {
                    info += " (Weapon: +" + item.getAttackPoints() + " ATK)";
                }
                if (item.getHealthPoints() > 0) {
                    info += " (Healing: +" + item.getHealthPoints() + " HP)";
                }
                System.out.println("- " + item.getName() + info);
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
            player.addItem(found);
            currentRoom.removeItem(found);
            System.out.println(found.getName() + " has been picked up and added to your inventory.");
        } else {
            System.out.println("No such item in this room.");
        }
    }

    private void inspect(String name) {
        for (Item i : player.getInventory()) {
            if (i.getName().equalsIgnoreCase(name)) {
                System.out.println("\n" + i.getName());
                System.out.println(i.getDescription());
                if (i.getAttackPoints() > 0) {
                    System.out.println("Attack: +" + i.getAttackPoints());
                }
                if (i.getHealthPoints() > 0) {
                    System.out.println("Healing: +" + i.getHealthPoints());
                }
                return;
            }
        }
        System.out.println("You don't have that item.");
    }

    private void drop(String name, Room currentRoom) {
        Item found = null;
        for (Item i : player.getInventory()) {
            if (i.getName().equalsIgnoreCase(name)) {
                found = i;
                break;
            }
        }
        if (found != null) {
            player.removeItem(found);
            currentRoom.addItem(found);
            System.out.println(found.getName() + " has been dropped in the current room.");
        } else {
            System.out.println("You don't have that item.");
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
                player.showInventory();
                break;
            case "equip":
                if (parts.length > 1) {
                    System.out.println(player.equip(parts[1]));
                } else {
                    System.out.println("Equip what?");
                }
                break;
            case "unequip":
                System.out.println(player.unequip());
                break;
            case "heal":
                if (parts.length > 1) {
                    System.out.println(player.heal(parts[1]));
                } else {
                    System.out.println("Use which item to heal?");
                }
                break;
            case "stats":
                player.displayStats();
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
        System.out.println("\n=== Commands ===");
        System.out.println("\nMovement:");
        System.out.println("  NORTH, SOUTH, EAST, WEST - move between rooms");
        System.out.println("\nItems:");
        System.out.println("  EXPLORE - look around for items");
        System.out.println("  PICKUP [item] - collect an item");
        System.out.println("  INSPECT [item] - view an item description");
        System.out.println("  DROP [item] - drop an item");
        System.out.println("  INVENTORY - show what you're carrying");
        System.out.println("\nCombat & Equipment:");
        System.out.println("  EQUIP [item] - equip a weapon to increase attack");
        System.out.println("  UNEQUIP - remove equipped weapon");
        System.out.println("  HEAL [item] - use a healing item");
        System.out.println("  STATS - view your current stats");
        System.out.println("\nOther:");
        System.out.println("  MAP - show the mansion layout");
        System.out.println("  HELP - show this message");
        System.out.println("  QUIT - exit the game");
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