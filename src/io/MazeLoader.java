package io;

import exceptions.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class used for converting a maze file into a char[][] array. Reads file data
 * with a
 * BufferedReader and checks that all specifications provided by
 * {@link FileInterface} are met.
 */
public class MazeLoader implements FileInterface {
    private final MazeMalformedException wallException = new MazeMalformedException(
            "Maze should be surrounded by walls.");
    private final MazeMalformedException wonkyException = new MazeMalformedException(
            "Maze lines should all be the same length.");
    private final MazeSizeMissmatchException wrongSizeException = new MazeSizeMissmatchException(
            "Dimensions of maze do not match those specified.");

    /**
     * Reads a provided file and creates a 2-dimensional character array if the maze
     * data is valid.
     * 
     * @param filename The path to the maze file to be loaded.
     * @return a char[][] array with each 2-dimensional entry corresponding to a
     *         maze component.
     * @throws MazeMalformedException     if the maze is not formatted correctly.
     * @throws MazeSizeMissmatchException if the dimensions of the maze do not match
     *                                    those that
     *                                    were specified in the first line of the
     *                                    maze file.
     * @throws IllegalArgumentException   for other general validation errors
     * @throws FileNotFoundException      if the file provided is not found.
     */
    @Override
    public char[][] load(String filename)
            throws MazeMalformedException, MazeSizeMissmatchException,
            IllegalArgumentException, FileNotFoundException {
        BufferedReader getMazeData;
        String validMazeAlphabet = "# .SE";
        int height;
        int width;
        int starts = 0;
        int ends = 0;
        int initialLineLength = 0;

        try {
            getMazeData = new BufferedReader(new FileReader(filename));
        } catch (NullPointerException e) {
            throw new FileNotFoundException("file is null");
        }
        String[] dimensions;
        String firstLine;
        // Try to parse first line as maze dimensions
        try {
            firstLine = getMazeData.readLine();
            try {
                dimensions = firstLine.split(" ");

            } catch (NullPointerException e) {
                throw new IllegalArgumentException("File is empty");

            }

        } catch (IOException e) {
            throw new IllegalArgumentException();

        }

        try {
            height = Integer.parseInt(dimensions[0]);
            try {
                width = Integer.parseInt(dimensions[1]);

            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Too few dimensions.");

            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("First line cannot be interpreted as an integer.");

        }

        // Check that the dimensions are valid
        if (dimensions.length != 2 || height % 2 == 0 || width % 2 == 0 || height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Dimensions should be positive odd integers.");

        }

        char[][] mazeTemplate = new char[height][width];
        // Populate char[][] array with mazeblock char representations.
        int y = 0;
        boolean surrounding = true;
        try {
            for (String line = getMazeData.readLine(); line != null; line = getMazeData.readLine()) {
                surrounding = true;
                char[] checkLine = line.toCharArray();

                // check maze is surrounded by walls
                for (char checkWall : checkLine) {
                    if (checkWall != '#') {
                        if (y == 0) {
                            throw wallException;
                        }
                        surrounding = false;
                    }
                }

                if (y == 0) {
                    // Used for differentiating between missmatch and malformed mazes
                    initialLineLength = checkLine.length;

                    // maze should be enclosed by walls
                }

                // valid maze blocks
                for (char mazeBlockChar : checkLine) {
                    if (!validMazeAlphabet.contains(((Character) mazeBlockChar).toString())) {
                        throw new IllegalArgumentException("Maze data should only contain " +
                                "'S', 'E', '#', ' ', or '.'");
                    } else if (mazeBlockChar == 'S') {
                        starts++;
                    } else if (mazeBlockChar == 'E') {
                        ends++;
                    }

                    // maze should be surrounded by walls
                    if (checkLine[0] != '#' || checkLine[checkLine.length - 1] != '#') {
                        throw wallException;

                    }
                }

                // lines should be consistent and the same length as specified
                if (checkLine.length != initialLineLength) {
                    throw wonkyException;

                }
                if (y < height) {
                    mazeTemplate[y] = checkLine;
                }
                y++;
            }
        } catch (IOException f) {
            throw new IllegalArgumentException();

        }

        if (y != height || width != initialLineLength) {
            throw wrongSizeException;
        }
        // maze should be surrounded by walls
        if (!surrounding) {
            throw wallException;
        }

        if (ends != 1 || starts != 1) {
            throw new MazeMalformedException("Maze data should contain exactly one start " +
                    "and end point.");
        }

        return mazeTemplate;
    }
}