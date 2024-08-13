package model;

import java.util.Arrays;
import java.util.HashSet;

/**
 * A class for representing a maze consisting of walls, paths, and a wonderful
 * little goose.
 * There should only ever be a single occupied {@link Path} at a time.
 */
public class Maze {
    /**
     * A 2-dimensional array containing MazeBlocks indexed by their horizontal and
     * vertical positions.
     */
    protected MazeBlock[][] blocks;
    /**
     * Our favourite goosey guy.
     */
    public Goose goose;
    private int width;
    private int height;

    /**
     * {@link Path} from {@link Maze#blocks} which is currently occupied by
     * {@link Maze#goose}.
     */
    private Path currentlyOccupiedCell;

    /**
     * Initialises a new Maze from a 2-dimensional character array by populating
     * {@link Maze#blocks} and setting the goose's position based on the
     * {@link StartPath}.
     * 
     * @param mazeTemplate a 2-dimensional character array holding the types of
     *                     MazeBlocks
     *                     encoded as characters.
     * @requires A valid Maze char[][] array.
     * @ensures goose starting position is the same as the position of
     *          {@link StartPath},
     *          {@link Maze#currentlyOccupiedCell} is the {@link StartPath}
     *          which should be occupied.
     */
    public Maze(char[][] mazeTemplate) {
        this.goose = new Goose();
        this.height = mazeTemplate.length;
        this.width = mazeTemplate[0].length;
        this.blocks = new MazeBlock[width][height];

        // populate this.blocks.
        int yIndex = 0;
        for (char[] blockLine : mazeTemplate) {
            int xIndex = 0;
            for (char block : blockLine) {
                MazeBlock newBlock = charToBlock(block);
                newBlock.setPosition(xIndex, yIndex);
                this.blocks[xIndex][yIndex] = newBlock;

                // ensuring postcondition
                if (block == 'S') {
                    this.goose.setPosition(xIndex, yIndex);
                    this.currentlyOccupiedCell = (Path) newBlock;
                    this.currentlyOccupiedCell.setOccupied(true);
                }
                xIndex++;
            }
            yIndex++;
        }
    }

    /**
     * Get the dimensions of the maze as an array containing width and height.
     * 
     * @return integer array with width at index 0 and height at index 1.
     */
    public int[] getDimensions() {
        return (new int[] { this.width, this.height });
    }

    /**
     * Converts the character encoding of a {@link MazeBlock} to an instance of the
     * corresponding type.
     * 
     * @param blockType character encoding
     * @return MazeBlock reference to a {@link Path}, {@link Wall}, {@link EndPath},
     *         or {@link EndPath} object.
     * @throws IllegalArgumentException if the character input is not one of the 5
     *                                  accepted values.
     */
    private static MazeBlock charToBlock(char blockType) throws IllegalArgumentException {
        MazeBlock output;
        switch (blockType) {
            case '.', ' ' -> output = new Path();
            case '#' -> output = new Wall();
            case 'S' -> output = new StartPath();
            case 'E' -> output = new EndPath();
            default -> throw new IllegalArgumentException();
        }
        return output;
    }

    /**
     * Sets the provided {@link Path} to be occupied.
     * 
     * @param path Path to move the goose to.
     * @requires Path should be in reach of goose.
     * @ensures only a single occupied Path.
     */
    private void setCurrentlyOccupiedCell(Path path) {
        assert Arrays.equals(path.getPosition(), this.goose.getNeighbouringPosition(Directions.LEFT)) ||
                Arrays.equals(path.getPosition(), this.goose.getNeighbouringPosition(Directions.RIGHT)) ||
                Arrays.equals(path.getPosition(), this.goose.getNeighbouringPosition(Directions.UP)) ||
                Arrays.equals(path.getPosition(), this.goose.getNeighbouringPosition(Directions.DOWN)) ||
                Arrays.equals(path.getPosition(), this.goose.getPosition()) : "Path not in reach of goose";

        this.currentlyOccupiedCell.setOccupied(false);
        this.currentlyOccupiedCell = path;
        this.currentlyOccupiedCell.setOccupied(true);
    }

    /**
     * Checks if the goose has reached the {@link EndPath} of the maze.
     * 
     * @return boolean whether the goose has reached the end of the maze.
     */
    public boolean endReached() {
        return this.currentlyOccupiedCell instanceof EndPath;
    }

    /**
     * Iterates through all paths in {@link Maze#blocks} to ascertain if the maze
     * can be traversed.
     * from the {@link StartPath} to {@link EndPath}.
     * 
     * @return boolean whether the maze is solvable
     */
    public boolean isSolvable() {
        // all visited paths
        HashSet<Path> visited = new HashSet<>();
        visited.add(this.currentlyOccupiedCell);
        // paths to check neighbours of
        HashSet<Path> frontier = new HashSet<>();
        frontier.add(this.currentlyOccupiedCell);

        while (true) {
            // to store the neighbouring paths of paths in frontier
            HashSet<Path> newFrontier = new HashSet<>();
            // get neighbours
            for (Path currentCheckPoint : frontier) {
                for (Directions direction : Directions.values()) {
                    int[] position = currentCheckPoint.getNeighbouringPosition(direction);
                    MazeBlock blockOfInterest = this.blocks[position[0]][position[1]];

                    // check if end is reached. otherwise store it.
                    if (blockOfInterest instanceof Path && visited.add((Path) blockOfInterest)) {
                        if (blockOfInterest instanceof EndPath) {
                            return true;
                        }
                        newFrontier.add((Path) blockOfInterest);
                    }
                }
            }
            // if no new paths are found and the end is not reached, then there is nowhere
            // to go.
            if (newFrontier.isEmpty()) {
                return false;
            }

            // frontier's neighbours now need to get searched in the same way.
            visited.addAll(frontier);
            frontier.clear();
            frontier.addAll(newFrontier);
            newFrontier.clear();
        }
    }

    /**
     * From {@link Maze#blocks} get the block with position (x, y).
     * 
     * @param x int horizontal position
     * @param y int vertical position
     * @return {@link MazeBlock} at the specified position
     */
    public MazeBlock getBlock(int x, int y) {
        return this.blocks[x][y];
    }

    /**
     * Move the goose in a specified direction to an adjacent path.
     * 
     * @param direction direction to move the goose
     * @requires all external {@link MazeBlock}s to be {@link Wall}s
     * @ensures only one occupied {@link Path}, goose is on a Path
     */
    public void moveGoose(Directions direction) {
        int[] potentialPosition = this.goose.getNeighbouringPosition(direction);
        MazeBlock newBlock = this.getBlock(potentialPosition[0], potentialPosition[1]);
        if (newBlock.isTraversable() && newBlock instanceof Path) {
            this.goose.move(direction);
            this.currentlyOccupiedCell.addFlow(direction);
            this.setCurrentlyOccupiedCell((Path) newBlock);
            this.currentlyOccupiedCell.addFlow(direction);
        }

    }
}
