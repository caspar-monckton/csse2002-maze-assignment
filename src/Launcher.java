import controller.GUIController;
import controller.MazeController;
import java.util.Objects;

/**
 * Class that kickstarts the application based on view and maze file
 * specifications.
 */
public class Launcher {
    /**
     * Entry point for the entire program. Takes in an argument string and
     * determines the
     * maze file to be displayed and whether text based view or gui based view
     * should be
     * used. Defaults to text based if no argument is passed. Passes a null filepath
     * to controller
     * if no file is selected. Does not ensure maze filepath is valid; to be handled
     * by controller.
     * 
     * @param args keyboard input from terminal containing maze filepath and view
     *             data. Valid
     *             values include "root/my_maze_file.txt GUI" or "text" or "" or
     *             "root/maze.txt".
     *             If including a view specification, it must either be "text" or
     *             "GUI".
     * @requires arguments to only contain maze file path or view specifications
     *           with view coming
     *           after mazefile. Requires a valid view specification.
     */
    public static void main(String[] args) {
        assert args.length <= 2 : "Commandline arguments should consist only of (1) a maze file" +
                "path and/or (2) a display type or neither.";

        String mazeFilePath = null;
        String displayType = "text";

        if (args.length != 0) {
            if (args.length == 2) {
                assert Objects.equals(args[1], "text") || Objects.equals(args[1], "GUI")
                        : "A valid view specification should come second. Try \"text\" or \"GUI\".";

                mazeFilePath = args[0];
                displayType = args[1];

            } else if (Objects.equals(args[0], "text") || Objects.equals(args[0], "GUI")) {
                displayType = args[0];

            } else {
                mazeFilePath = args[0];

            }
        }

        if (Objects.equals(displayType, "text")) {
            MazeController doMazeThings = new MazeController(mazeFilePath);
            doMazeThings.update();

        } else if (Objects.equals(displayType, "GUI")) {
            new GUIController(mazeFilePath);

        }
    }
}