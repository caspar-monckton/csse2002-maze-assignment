package model;

/**
 * Solid MazeBlock through which no goose or other non MazeBlock MazeEntity can pass through.
 * Not even very royal ones.
 */
public class Wall extends MazeBlock {
    /**
     * Like {@link MazeBlock#MazeBlock()} but initialises a Wall and makes it non-traversable.
     * @see MazeBlock#traversable
     */
    public Wall(){
        this.traversable = false;
    }

    /**
     * Like {@link MazeBlock#MazeBlock(int[] position)} but initialises a non-traversable Wall.
     * @param position an array containing the x and y integer positions of the Wall.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public Wall(int[] position){
        this.traversable = false;
        this.position = position;
    }
}
