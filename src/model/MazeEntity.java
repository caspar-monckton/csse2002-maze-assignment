package model;

/**
 * An entity contained within a maze at a specific 2-dimensional position.
 */
public abstract class MazeEntity {
    /**
     * Stores the position inside a maze of the MazeEntity.
     * Defaults to positioning at the origin, (0, 0).
     */
    protected int[] position = new int[2];

    /**
     * Constructs a MazeEntity at the origin if no position is specified.
     */
    public MazeEntity(){

    }

    /**
     * Constructs a MazeEntity at a position specified by two integers.
     * @param x integer coordinate of the MazeEntity's horizontal position.
     * @param y integer coordinate of the MazeEntity's vertical position.
     */
    public MazeEntity(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    /**
     * Constructs a MazeEntity at a position specified by a single integer array.
     * @param position an array containing the x and y integer positions of the MazeEntity.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public MazeEntity(int[] position) {
        assert position.length == 2: "\n\n parameter 'int[] position' should only contain" +
                " an x and y value. \n\n";
        this.position = position;
    }

    /**
     * Provides the position of the MazeEntity as an array containing x and y positions.
     * @return integer array containing x as first element and y as second element.
     */
    public int[] getPosition(){
        return this.position;
    }

    /**
     * Sets the position of the MazeEntity to a given value.
     * @param newX integer representing the new x position of entity.
     * @param newY integer representing the new y position of entity.
     */
    public void setPosition(int newX, int newY){
        this.position[0] = newX;
        this.position[1] = newY;
    }
    /**
     * Gets the position one step in the specified direction of the entity.
     * @param direction direction to get position
     * @return int[] position in direction with x and y values as 0th and 1st indices
     * respectively
     * @throws IllegalArgumentException if direction specified is not from {@link Directions}.
     * @see Goose#move(Directions)
     */
    public int[] getNeighbouringPosition(Directions direction) throws IllegalArgumentException{
        int[] out = this.position;
        switch (direction) {
            case UP -> out = new int[] {this.position[0], this.position[1] - 1};
            case DOWN -> out = new int[] {this.position[0], this.position[1] + 1};
            case LEFT -> out = new int[] {this.position[0] - 1, this.position[1]};
            case RIGHT -> out = new int[] {this.position[0] + 1, this.position[1]};
            default -> throw new IllegalArgumentException();
        }
        return out;
    }

}
