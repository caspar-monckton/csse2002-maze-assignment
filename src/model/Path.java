package model;

import java.util.ArrayList;

/**
 * A traversable MazeBlock through which a non MazeBlock MazeEntity may move.
 */
public class Path extends MazeBlock {
    /**
     * Whether the Path is currently occupied by a non MazeBlock MazeEntity.
     */
    private boolean occupied = false;

    /**
     * Contains all directions from which the Path was exited and entered from.
     * @see Directions
     */
    private ArrayList<Directions> flow = new ArrayList<>();

    /**
     * Like {@link MazeBlock#MazeBlock()} but initialises a Path instead.
     */
    public Path (){
        //See comment in analogous constructor of MazeBlock.
    }

    /**
     * Like {@link MazeBlock#MazeBlock(int[] position)} but initialises a Path instead.
     * @param position an array containing the x and y integer positions of the Path.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public Path (int[] position){
        super(position);
    }

    /**
     * Getter method for protected field {@link Path#occupied}.
     * @return Whether this Path is currently being occupied by a non MazeBlock MazeEntity.
     */
    public boolean getOccupied(){
        return this.occupied;
    }

    /**
     * Setter method for private field {@link Path#occupied}.
     * @param newOccupied whether this Path will be occupied by a non MazeBlock MazeEntity.
     */
    public void setOccupied(boolean newOccupied){
        this.occupied = newOccupied;
    }

    /**
     * Gets the number of times a non MazeBlock MazeEntity has occupied this Path.
     * @return number of visits the block has accrued.
     * @see Path#occupied
     */
    public int getVisits(){
        return (int) Math.ceil(((double) flow.size())/2.0);
    }

    /**
     * Add a new direction to private field {@link Path#flow}
     * @param direction Directions direction to be added.
     * @see Directions
     */
    public void addFlow(Directions direction){
        this.flow.add(direction);
    }

    /**
     * Checks whether the current Path is part of the currently active route of a non MazeBlock
     * MazeEntity by considering the net sum of horizontal and vertical movements made on it.
     * @return whether net sum of horizontal and vertical movements made on this Path is
     * greater than 0 according to {@link Directions#add(Directions)}.
     */
    public boolean isFlowing(){
        int flowX = 0;
        int flowY = 0;
        for (Directions direction: this.flow){
            flowX += Directions.RIGHT.add(direction);
            flowY += Directions.UP.add(direction);
        }
        return (Math.abs(flowX) + Math.abs(flowY)) > 0;
    }
}
