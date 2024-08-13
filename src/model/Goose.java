package model;

/**
 * The most majestic MazeEntity to ever bless this earth. Moves around a maze and sometimes honks.
 */
public class Goose extends MazeEntity{
    /**
     * THE class invariant. Simple facts.
     */
    public final boolean isUndeniablyTheMostRoyalGuy = true;

    /**
     * Like {@link MazeEntity#MazeEntity()} but initialises a Goose instead.
     */
    public Goose() {
        //see comment in empty constructor of MazeBlock.
    }

    /**
     * Like {@link MazeEntity#MazeEntity(int[] position)} but initialises a Goose instead.
     * @param position an array containing the x and y integer positions of the MazeBlock.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public Goose(int[] position){
        this.position = position;
    }

    /**
     * Moves the goose within the maze by one step by incrementing or decrementing x and y values
     * of position. Does NOT account for what type of MazeBlock it may be moving onto.
     * @param direction direction to move the goose.
     * @throws IllegalArgumentException if the direction specified is not from {@link Directions}.
     * @see Goose#getNeighbouringPosition(Directions)
     */
    public void move(Directions direction) throws IllegalArgumentException{
        switch (direction) {
            case UP -> this.position[1]--;
            case DOWN -> this.position[1]++;
            case LEFT -> this.position[0]--;
            case RIGHT -> this.position[0]++;
            default -> throw new IllegalArgumentException();
        }
    }


}
