package exceptions;

/**
 * To be thrown if a maze is incorrectly formatted, such as uneven line length, or too many starts
 * or ends.
 */
public class MazeMalformedException extends Exception  {
    public MazeMalformedException(){
        super();
    }
    public MazeMalformedException(String errMessage){
        super(errMessage);
    }
}
