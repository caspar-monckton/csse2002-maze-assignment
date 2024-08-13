package exceptions;

/**
 * To be thrown if Maze has dimensions which do not meet those specified.
 */
public class MazeSizeMissmatchException extends Exception {
    public MazeSizeMissmatchException(){
        super();
    }
    public MazeSizeMissmatchException(String errMessage){
        super(errMessage);
    }
}
