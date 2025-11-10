/**Class: Player
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: November 08, 2025
 *
 * This class represents the player with health, combat stats, and equipment.
 */

import java.util.ArrayList;

public class Player {
    private int health;
    private int maxHealth;
    private int baseDamage;
    private int currentDamage;
    private ArrayList<Item> inventory;
    private Item equippedWeapon;

    public Player(int health, int baseDamage) {
        this.health = health;
        this.maxHealth = health;
        this.baseDamage = baseDamage;
        this.currentDamage = baseDamage;
        this.inventory = new ArrayList<>();
        this.equippedWeapon = null;
    }

    // --- Getters ---
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    public boolean isAlive() {
        return health > 0;
    }

    // --- Combat Methods ---

    /**
     * Player attacks and returns damage dealt
     */
    public int attack() {
        return currentDamage;
    }

    /**
     * Player takes damage
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    // --- Equipment Methods ---

    /**
     * Equip an item to increase attack damage
     */
    public String equip(String itemName) {
        Item found = null;
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = item;
                break;
            }
        }

        if (found == null) {
            return "You don't have that item in your inventory.";
        }

        if (found.getAttackPoints() <= 0) {
            return found.getName() + " cannot be equipped as a weapon.";
        }

        // Unequip current weapon if any
        if (equippedWeapon != null) {
            currentDamage = baseDamage;
        }

        equippedWeapon = found;
        currentDamage = baseDamage + found.getAttackPoints();
        return "You equipped " + found.getName() + ". Attack damage increased to " + currentDamage + "!";
    }

    /**
     * Unequip current weapon and return to base damage
     */
    public String unequip() {
        if (equippedWeapon == null) {
            return "You don't have any weapon equipped.";
        }

        String weaponName = equippedWeapon.getName();
        equippedWeapon = null;
        currentDamage = baseDamage;
        return "You unequipped " + weaponName + ". Attack damage returned to " + currentDamage + ".";
    }

    /**
     * Use a healing item to restore health
     */
    public String heal(String itemName) {
        Item found = null;
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = item;
                break;
            }
        }

        if (found == null) {
            return "You don't have that item in your inventory.";
        }

        if (found.getHealthPoints() <= 0) {
            return found.getName() + " cannot be used for healing.";
        }

        int healAmount = found.getHealthPoints();
        health += healAmount;
        if (health > maxHealth) {
            health = maxHealth;
        }

        // Remove consumed healing item
        inventory.remove(found);
        return "You used " + found.getName() + " and restored " + healAmount + " HP. Current health: " + health + "/" + maxHealth;
    }

    // --- Inventory Methods ---

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
        // If it was equipped, unequip it
        if (equippedWeapon != null && equippedWeapon.equals(item)) {
            equippedWeapon = null;
            currentDamage = baseDamage;
        }
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You didn't pickup any items yet.");
        } else {
            System.out.println("\n=== Inventory ===");
            for (Item item : inventory) {
                String equipped = (equippedWeapon != null && equippedWeapon.equals(item)) ? " [EQUIPPED]" : "";
                String stats = "";
                if (item.getAttackPoints() > 0) {
                    stats += " (ATK: +" + item.getAttackPoints() + ")";
                }
                if (item.getHealthPoints() > 0) {
                    stats += " (HP: +" + item.getHealthPoints() + ")";
                }
                System.out.println("- " + item.getName() + stats + equipped);
            }
        }
    }

    /**
     * Display player stats
     */
    public void displayStats() {
        System.out.println("\n=== Player Stats ===");
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Attack Damage: " + currentDamage);
        if (equippedWeapon != null) {
            System.out.println("Equipped: " + equippedWeapon.getName());
        }
    }

    /**
     * Reset player to full health (for new game)
     */
    public void reset() {
        health = maxHealth;
        currentDamage = baseDamage;
        equippedWeapon = null;
        inventory.clear();
    }
}