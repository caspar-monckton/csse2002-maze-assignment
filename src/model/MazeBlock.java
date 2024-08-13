package model;

/**
 * A stationary block component of a maze with a vertical and horizontal position.
 */
public abstract class MazeBlock extends MazeEntity {
    /**
     * Whether the MazeBlock can be traversed by a non MazeBlock MazeEntity.
     */
    protected boolean traversable = true;

    /**
     * Like {@link MazeEntity#MazeEntity()} but initialises a MazeBlock instead.
     */
    public MazeBlock(){
        //calling super is redundant and wastes resources.
    }

    /**
     * Like {@link MazeEntity#MazeEntity(int x, int y)} but initialises a MazeBlock instead.
     * @param x integer coordinate of the MazeBlock's horizontal position.
     * @param y integer coordinate of the MazeBlock's vertical position.
     */
    public MazeBlock(int x, int y) {
        super(x, y);
    }

    /**
     * Like {@link MazeEntity#MazeEntity(int[] position)} but initialises a MazeBlock instead.
     * @param position an array containing the x and y integer positions of the MazeBlock.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public MazeBlock(int[] position) {
        super(position);
    }

    /**
     * Getter method for protected field {@link MazeBlock#traversable}.
     * @return whether a non MazeBlock MazeEntity can traverse this MazeBlock.
     */
    public boolean isTraversable(){
        return this.traversable;
    }

}
