/**Class: Monster
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: November 08, 2025
 *
 * This class represents a monster in the game with combat capabilities.
 */

import java.util.Random;

public class Monster {
    private String name;
    private String description;
    private int health;
    private int attackDamage;
    private double threshold;
    private int roomNumber;
    private boolean alive;
    private Random random;

    public Monster(String name, String description, int health, int attackDamage, double threshold, int roomNumber) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.attackDamage = attackDamage;
        this.threshold = threshold;
        this.roomNumber = roomNumber;
        this.alive = true;
        this.random = new Random();
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAlive() {
        return alive;
    }

    // --- Combat Methods ---

    /**
     * Monster attacks and returns damage dealt.
     * If random number is below threshold, deals double damage.
     */
    public int attack() {
        double roll = random.nextDouble();
        if (roll < threshold) {
            System.out.println("🔥 CRITICAL HIT! " + name + " deals double damage!");
            return attackDamage * 2;
        } else {
            return attackDamage;
        }
    }

    /**
     * Monster takes damage from player attack
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    /**
     * Display monster information including attack damage
     */
    public void examine() {
        System.out.println("\n=== " + name + " ===");
        System.out.println(description);
        System.out.println("Attack Damage: " + attackDamage);
        System.out.println("Critical Hit Threshold: " + (threshold * 100) + "%");
        System.out.println("Current Health: " + health + " HP");
    }

    @Override
    public String toString() {
        return name + " (HP: " + health + ")";
    }
}