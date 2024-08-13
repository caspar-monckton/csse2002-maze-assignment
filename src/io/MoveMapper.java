package io;

import model.Directions;

/**
 * Maps Strings to {@link Directions}.
 */
public class MoveMapper {
    /**
     * Map String representing direction to the corresponding {@link Directions} enum.
     * @param inputDirection String keyboard input for direction
     * @return corresponding direction
     */
    public static Directions stringToDirection(String inputDirection){
        Directions output = null;
        switch (inputDirection) {
            case "w" -> output = Directions.UP;
            case "a" -> output = Directions.LEFT;
            case "s" -> output = Directions.DOWN;
            case "d" -> output = Directions.RIGHT;
        }
        return output;
    }
}
