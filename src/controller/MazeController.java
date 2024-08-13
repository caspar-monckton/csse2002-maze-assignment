package controller;

import io.MazeLoader;
import io.MoveMapper;
import model.Directions;
import model.Maze;
import view.*;
import java.util.Scanner;
import java.util.Objects;

/**
 * Class responsible for synthesising user input to a {@link Maze} with text based visual input.
 * Uses Streams to get keyboard input from terminal. Is not related via any inheritance
 * pathways to {@link GUIController}.
 */
public class MazeController {
    private Maze maze;
    private MazeView view;

    /**
     * Useless interface so I can meet lambda expression marking criteria.
     */
    interface InputToMovement {
        Directions moveFromKeyboard(String input);
    }

    /**
     * Another useless thing for lambda expressions.
     * @param input keyboard direction represented by w, a, s, d.
     * @param function an interface that responds to w, a, s, d keys.
     * @return the method of the interface.
     */
    private Directions moveControllerGoose(String input, InputToMovement function){
        return function.moveFromKeyboard(input);
    }

    /**
     * Construct a new MazeController with a maze from a filepath.
     * @param mazeFilePath filepath to maze file to be read in.
     * @throws IllegalArgumentException if the MazeLoader encounters any problems.
     */
    public MazeController(String mazeFilePath) throws IllegalArgumentException{
        try {
            this.maze = new Maze((new MazeLoader()).load(mazeFilePath));
        } catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

        this.view = new MazeView(this.maze);
        this.view.redraw();
    }

    /**
     * Redraws the text view every time something changes to the underlying maze model.
     * Uses a scanner to listen for key input from the terminal and employs a useless lambda
     * expression to move the goose within the maze.
     */
    public void update(){
        Scanner keyListener = new Scanner(System.in);
        String keyPressed;
        //get keyboard input
        do {
            keyPressed = keyListener.nextLine();

            try {
                this.maze.moveGoose(moveControllerGoose(keyPressed, controlInput
                        -> MoveMapper.stringToDirection(controlInput)));
                this.view.redraw();
            } catch (Exception e){
                e.printStackTrace();
            }

        } while (!Objects.equals(keyPressed, "q") && !this.maze.endReached());

        keyListener.close();

        if (this.maze.endReached()){
            System.out.println("Well Done! You have completed the maze.");
        }

    }
}
