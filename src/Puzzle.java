/**Class: Room
 * @author Daniel Musau
 * @version 1.0
 * Course: ITEC 3860 Fall 2025
 * Written: August 25, 2025
 *
 * This class contains details regarding the puzzles.
 */

public class Puzzle {
    private String name;
    private String description;
    private String answer;
    private int attemptsAllowed;
    private int remainingAttempts;
    private boolean solved;

    public Puzzle(String name, String description, String answer, int attemptsAllowed) {
        this.name = name;
        this.description = description;
        this.answer = answer.toLowerCase();
        this.attemptsAllowed = attemptsAllowed;
        this.remainingAttempts = attemptsAllowed;
        this.solved = false;
    }

    public String getDescription() {
        return description;
    }
    public boolean isSolved() {
        return solved;
    }
    public void reset() {
        remainingAttempts = attemptsAllowed;
    }

    public String trySolve(String input) {
        if (solved) return "You already solved the puzzle correctly!";
        if (input.toLowerCase().equals(answer)) {
            solved = true;
            return "You solved the puzzle correctly!";
        } else {
            remainingAttempts--;
            if (remainingAttempts > 0) {
                return "The answer you provided is wrong, you still have " + remainingAttempts + " attempt(s). Try one more time.";
            } else {
                reset();
                return "Failed to solve the puzzle.";
            }
        }
    }
}
