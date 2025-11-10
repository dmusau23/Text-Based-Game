# Mini Text-Based Adventure Game - Final Assignment
***
**Author:** Daniel Musau  
**Course:** ITEC 3860 Fall 2025  
**Assignment:** Final Individual Assignment with Monster & Enhanced Item Features

## 📋 Table of Contents
1. [Game Map](#game-map)
2. [Text File Data Structures](#text-file-data-structures)
3. [User Manual](#user-manual)
4. [Features Implemented](#features-implemented)
5. [How to Run](#how-to-run)
6. [AI Usage](#ai-usage)

---

## 🗺️ Game Map

```
                +---------+
                |    6    |
+-------------------  ----+
|       |       |         |
|   2       4       5     |
|       |       |         |
+---  ------  ------------+
|       |       |
|    1      3   |
|       |       |    
+---------------+
```

### Room Legend
- **[1] Entrance Hall** → NORTH to Library (2), EAST to Kitchen (3)
- **[2] Library** → SOUTH to Entrance Hall (1), EAST to Conservatory (4) | Contains: lamp, rusty sword
- **[3] Kitchen** → WEST to Entrance Hall (1), NORTH to Conservatory (4) | Contains: health potion | Puzzle Location
- **[4] Conservatory** → WEST to Library (2), SOUTH to Kitchen (3), EAST to Ballroom (5) | Contains: iron shield
- **[5] Ballroom** → WEST to Conservatory (4), NORTH to Tower Room (6) | Contains: magic elixir | **Monster: Shadow Beast**
- **[6] Tower Room** → SOUTH to Ballroom (5) | Contains: legendary blade | **Monster: Ancient Guardian**

---

## 📁 Text File Data Structures

### rooms.txt
**Format:** `roomNumber,roomName,description,DIRECTION:destinationRoom,...`

**Example:**
```
1,Entrance Hall,You are standing in the grand entrance hall of an old mansion.,NORTH:2,EAST:3
2,Library,A quiet library filled with dusty books.,SOUTH:1,EAST:4
```

### items.txt
**Format:** (6 lines per item)
```
itemName
itemDescription
roomNumber
attackPoints
healthPoints
```

**Example:**
```
rusty sword
A rusty but still sharp sword.
2
15
0
```
- **attackPoints:** Damage bonus when equipped (0 if not a weapon)
- **healthPoints:** HP restored when used (0 if not a healing item)

### puzzle.txt
**Format:** (5 lines per puzzle)
```
puzzleName
puzzleDescription/Question
correctAnswer
attemptsAllowed
roomNumber
```

**Example:**
```
riddle
What has keys but can't open locks?
keyboard
3
3
```

### monsters.txt
**Format:** (6 lines per monster)
```
monsterName
monsterDescription
health
attackDamage
criticalHitThreshold
roomNumber
```

**Example:**
```
Shadow Beast
A dark creature with glowing red eyes that lurks in the shadows. Its claws are razor sharp.
60
12
0.3
5
```
- **criticalHitThreshold:** Probability (0.0-1.0) of dealing double damage

---

## 📖 User Manual

### Starting the Game
1. Run the program
2. Enter the path to `rooms.txt` when prompted
3. The game loads all data files and starts at the Entrance Hall
4. You begin with 100 HP and 10 base attack damage

### Movement Commands
- `NORTH` / `SOUTH` / `EAST` / `WEST` - Move between rooms

### Item Commands
- `EXPLORE` - Look around for items in current room
- `PICKUP [item]` - Pick up an item
- `DROP [item]` - Drop an item from inventory
- `INSPECT [item]` - View detailed item description
- `INVENTORY` - View all items in your inventory

### Combat & Equipment Commands
- `EQUIP [item]` - Equip a weapon to increase attack damage
- `UNEQUIP` - Remove equipped weapon (returns to base damage)
- `HEAL [item]` - Use a healing item to restore health
- `STATS` - View your current health and attack stats

### Monster Interaction
When encountering a monster, you have three options:
1. **EXAMINE** - View monster description and attack damage
2. **ATTACK** - Engage in combat
3. **IGNORE** - Flee (monster disappears forever from that room)

### Combat Mode
During combat, you can:
- `ATTACK` - Attack the monster (uses your turn)
- `HEAL [item]` - Use a healing item (uses your turn)
- `EQUIP [item]` - Change weapon (doesn't use turn)
- `UNEQUIP` - Remove weapon (doesn't use turn)
- `INVENTORY` - Check items (doesn't use turn)

After your action, the monster attacks. Combat continues until either you or the monster is defeated.

### Other Commands
- `MAP` - Display the mansion layout
- `HELP` - Show all available commands
- `QUIT` - Exit the game

### Game Over
If you die in combat:
- Option 1: **EXIT** - Quit the game
- Option 2: **START NEW GAME** - Restart from the beginning

---

## ✅ Features Implemented

### ✓ Core Features (Previous Assignments)
- [x] Navigation between rooms
- [x] Room descriptions and exit display
- [x] Tracking visited rooms
- [x] Item examination, pickup, and drop
- [x] Puzzle system with limited attempts
- [x] Inventory management
- [x] Map display

### ✓ Expanded Item Feature (Current Assignment)
- [x] **EQUIP command** - Increases player attack damage based on item's attack points
- [x] **UNEQUIP command** - Returns player to base attack damage
- [x] **HEAL command** - Restores player health based on item's health points
- [x] Items can be equipped/used before or during combat
- [x] Healing items are consumed after use
- [x] Visual indicators for equipped items in inventory

### ✓ Monster Feature (Current Assignment)
- [x] **EXAMINE MONSTER** - Displays description and attack damage
- [x] **Critical Hit System** - Monsters deal double damage based on threshold
- [x] **ATTACK** - Engages combat mode
- [x] **IGNORE** - Makes monster disappear permanently
- [x] **Combat System** - Turn-based with player HP and monster HP display
- [x] Player can use items during combat (equip, heal, inventory)
- [x] Combat ends when either player or monster dies
- [x] Monster disappears from room after defeat
- [x] Victory message displayed on win
- [x] Game over handling with restart option

### ✓ Additional Features
- [x] Player stats display (health, damage, equipped weapon)
- [x] Item stats display (attack points, healing points)
- [x] Combat status display each turn
- [x] Emoji indicators for better UX (⚔️, 💀, 🎉, etc.)

---

## 🚀 How to Run

### Prerequisites
- Java JDK 17 or higher
- IntelliJ IDEA (recommended) or Eclipse

### Setup Instructions
1. Clone/download the repository
2. Open the project in IntelliJ IDEA
3. Ensure all files are in the correct locations:
   ```
   project-root/
   ├── src/
   │   ├── RoomTester.java (main)
   │   ├── Game.java
   │   ├── Room.java
   │   ├── RoomDataHandler.java
   │   ├── Item.java
   │   ├── Player.java
   │   ├── Monster.java
   │   └── Puzzle.java
   ├── rooms.txt
   ├── items.txt
   ├── puzzle.txt
   └── monster.txt
   ```

### Running the Game
1. Run `RoomTester.java`
2. When prompted, enter: `rooms.txt`
3. The game will automatically load all data files
4. Follow on-screen instructions to play

### Sample Gameplay Flow
1. Start at Entrance Hall
2. Go NORTH to Library
3. EXPLORE to find items
4. PICKUP rusty sword
5. EQUIP rusty sword (increases attack from 10 to 25)
6. Navigate to Ballroom (EAST, EAST, EAST)
7. Encounter Shadow Beast
8. Choose EXAMINE to see stats
9. Choose ATTACK to fight
10. Use ATTACK, HEAL, and EQUIP commands strategically
11. Defeat monster and continue exploring

---

## 🤖 AI Usage

I used ChatGPT for:
- Debugging syntax errors
- Understanding Java file I/O operations
- Troubleshooting combat system logic
- Formatting and structure suggestions

All core game logic, design decisions, and implementation were done independently.

---

## 📸 Screenshots

Screenshots demonstrating the following scenarios are available in the `Screenshots/` folder:
1. **Navigation** - Moving between rooms
2. **Item Interaction** - Exploring, picking up, inspecting, and dropping items
3. **Equipment System** - Equipping and unequipping weapons
4. **Healing System** - Using healing items
5. **Puzzle Solving** - Answering puzzle with attempts tracking
6. **Monster Encounter** - Examining, attacking, and ignoring monsters
7. **Combat Mode** - Active fight with turn-by-turn display
8. **Victory** - Defeating a monster
9. **Game Over** - Death and restart options
10. **Inventory Display** - Items with stats and equipped indicators

---

## 📝 Notes

- **No hardcoded file paths** - User enters path at runtime
- **Clean, organized code** - Commented for readability
- **Follows assignment requirements** - All specified features implemented
- **Error handling** - Graceful handling of invalid inputs and file errors
- **Extensible design** - Easy to add more rooms, items, puzzles, and monsters

---

**END OF README**