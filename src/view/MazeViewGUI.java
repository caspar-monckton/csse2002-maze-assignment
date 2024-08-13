package view;
import javax.swing.*;
import model.*;
import java.awt.*;

/**
 * Constructs a new GUI based JPanel canvas representation of a given {@link Maze}.
 * Displays the {@link MazeBlock}s and {@link Goose}, and colours paths based on whether
 * they are part of the currently traversed track. This view cannot be redrawn, and a new
 * MazeViewGUI object must be created for each change to the underlying maze.
 */
public class MazeViewGUI extends JPanel {
    private Maze maze;
    private int height;
    private int width;

    /**
     * Constructs a new GUI view from a given {@link Maze} and sets the height and width according
     * to what is specified. The size of the underlying JPanel is then set accordingly.
     * @param maze Maze to be visually displayed
     * @param width width of display
     * @param height height of display
     */
    public MazeViewGUI (Maze maze, int width, int height) {
        this.maze = maze;
        this.width = width;
        this.height = height;
        this.setSize(this.width, this.height);
    }

    /**
     * Iterates through all blocks in the maze and colours the corresponding location based on
     * what type of MazeBlock it is.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int cellWidth = (int) Math.round(((double) this.width)/((double) this.maze.getDimensions()[0]));
        int cellHeight = (int) Math.round(((double) this.height)/((double) this.maze.getDimensions()[1]));

        for (int y = 0;  y < this.maze.getDimensions()[1]; y++){
            for (int x = 0;  x < this.maze.getDimensions()[0]; x++){
                MazeBlock current = this.maze.getBlock(x, y);
                Color colour = Color.BLACK;
                //set appropriate colour
                if (current instanceof Wall){
                   colour = new Color(100, 100, 100);

                } else if (current instanceof EndPath){
                    colour = new Color(230, 50, 70);

                } else if (current instanceof Path){
                    if (((Path) current).getOccupied()){
                        colour = new Color(200, 200, 0);

                    } else {
                        if (((Path) current).isFlowing()){
                            colour = new Color(50, 200, 100);

                        } else if (((Path) current).getVisits() > 0){
                            colour = new Color(200, 50, 200);

                        } else {
                            colour = new Color(0, 0, 70);

                        }
                    }
                }

                g.setColor(colour);
                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }
    }

}
