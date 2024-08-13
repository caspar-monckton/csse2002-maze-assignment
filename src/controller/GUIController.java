package controller;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import io.MazeLoader;
import io.MoveMapper;
import model.*;
import view.MazeViewGUI;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

/**
 * Class responsible for synthesising user input to a maze with visual representation from a GUI.
 * Relies heavily on ActionListeners. Is not related via any inheritance pathways to
 * {@link MazeController}.
 */
public class GUIController extends JFrame implements KeyListener, MenuListener {
    private JPanel view;
    private Maze maze;
    private JMenuBar menu;
    private JMenu fileOption;

    /**
     * Construct new GUIController object from the filepath of a maze file. Automatically create
     * an appropriate {@link MazeViewGUI} class to display the {@link Maze}. Includes file
     * dialogue for selecting filepath of maze file.
     * @param mazeFilePath filepath to desired maze. Can be null.
     */

    public GUIController (String mazeFilePath){
        // Default blank view in case filepath is not supplied or an error occurs with loading
        // the maze
        this.view = new JPanel();

        try {
            this.maze = new Maze((new MazeLoader()).load(mazeFilePath));
            this.view = new MazeViewGUI(this.maze, 1000, 1000);
        } catch (NullPointerException e){
            System.err.println("FilePath is null");
        } catch (MazeMalformedException | MazeSizeMissmatchException | FileNotFoundException f){
            System.err.println(f.getMessage());
        }
        //add menu to view
        this.menu = new JMenuBar();
        this.fileOption = new JMenu("File");
        this.fileOption.addMenuListener(this);
        this.menu.add(this.fileOption);
        this.view.add(this.menu);
        //add view and configure window
        this.addKeyListener(this);
        this.add(this.view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setVisible(true);
    }

    /**
     * Called every time something happens to the underlying maze model. Redraws the entire
     * gui and add menu to it again.
     */
    public void update(){
        this.remove(this.view);
        if (this.maze.endReached()){
            this.view = new JPanel();
            this.view.add(new JLabel("Congratulations! You have solved the maze."));
        } else {
            this.view = new MazeViewGUI(this.maze, 1000, 1000);
        }
        this.view.add(this.menu);
        this.add(this.view);
        this.invalidate();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Move the goose in the maze according to w, a, s, d, keys via
     * {@link MoveMapper#stringToDirection} and update the display accordingly. If 'j' is pressed,
     * display whether the maze is solvable.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'j'){
            if (this.maze.isSolvable())
                System.out.println("\u001B[32m" + "Maze is solvable." + "\u001B[0m");
            else {
                System.out.println("\u001B[31m" + "Maze is not solvable." + "\u001B[0m");
            }
        } else {
            this.maze.moveGoose(MoveMapper.stringToDirection(((Character) e.getKeyChar()).toString()));
            this.update();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Open file dialogue to set the current maze through {@link MazeLoader} and update view. If
     * an error is encountered trying to load the selected file as a maze then the method is
     * called again.
     * @param e  a MenuEvent object
     */
    @Override
    public void menuSelected(MenuEvent e) {
        JFileChooser mazeSelector = new JFileChooser("c:");
        mazeSelector.setDialogTitle("Please select a maze file");
        int choice = mazeSelector.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION){
            char[][] mazeArray;
            try {
                mazeArray = (new MazeLoader()).load(mazeSelector.getSelectedFile().getAbsolutePath());
                this.maze = new Maze(mazeArray);
            } catch (Exception f){
                System.err.println(f.getMessage());
                this.menuSelected(e);
            }
            this.update();
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}

