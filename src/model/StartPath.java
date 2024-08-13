package model;

/**
 * Path that the royal goose should start on.
 */
public class StartPath extends Path {
    /**
     * Like {@link Path#Path()} but initialises a StartPath instead.
     */
    public StartPath(){
        //see comment in empty Path constructor
    }

    /**
     * Like {@link Path#Path(int[] position)} but initialises a StartPath instead.
     * @param position an array containing the x and y integer positions of the StartPath.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public StartPath(int[] position){
        super(position);
    }
}
