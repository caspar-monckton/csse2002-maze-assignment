package model;

/**
 * Path that the royal goose should end his arduous journey on.
 */
public class EndPath extends Path {
    /**
     * Like {@link Path#Path()} but initialises an EndPath instead.
     */
    public EndPath(){

    }

    /**
     * Like {@link Path#Path(int[] position)} but initialises an EndPath instead.
     * @param position an array containing the x and y integer positions of the EndPath.
     * @requires the position parameter to have a length of 2 with x as the first element and
     *           y as the second element.
     */
    public EndPath(int[] position){
        super(position);
    }
}
