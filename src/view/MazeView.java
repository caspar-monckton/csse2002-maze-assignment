package view;
import model.*;

/**
 * Constructs a new text based terminal representation of a given {@link Maze}. Displays the
 * {@link MazeBlock}s and {@link Goose}, and colours paths based on whether they are part of
 * the currently traversed track. View can be updated by calling redraw.
 */
public class MazeView {
    Maze maze;

    /**
     * Initialise the view with a {@link Maze} object.
     * @param maze Maze to be visually displayed
     */
    public MazeView(Maze maze){
        this.maze = maze;
    }

    /**
     * Redraw all parts of the current view based on the information in the underlying
     * {@link Maze}.
     */
    public void redraw(){
        for (int y = 0; y < this.maze.getDimensions()[1]; y++){
            for (int x = 0; x < this.maze.getDimensions()[0]; x++){
                MazeBlock current = this.maze.getBlock(x, y);
                if (current instanceof EndPath){
                    //red
                    System.out.print("\u001B[31m" + "\u2591" + "\u001B[0m");

                } else if (current instanceof Path){
                    if (((Path) current).getOccupied()){
                        //yellow
                        System.out.print("\u001B[33m" + "\u2584" + "\u001B[0m");

                    } else {
                        if (((Path) current).getVisits() > 0){
                            if(((Path) current).isFlowing()){
                                //cyan
                                System.out.print("\u001B[32m" + "\u2591" + "\u001B[0m");

                            } else {
                                //blue
                                System.out.print("\u001B[35m" + "\u2591" + "\u001B[0m");

                            }
                        } else {
                            //black
                            System.out.print("\u001B[30m" + "\u2591" + "\u001B[0m");

                        }
                    }
                } else if (current instanceof Wall){
                    //grey
                    System.out.print("\u001B[90m" + "\u2588" + "\u001B[0m");

                }
            }
            System.out.print("\n");
        }
    }
}