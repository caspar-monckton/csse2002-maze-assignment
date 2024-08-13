package io;

import exceptions.MazeMalformedException;
import exceptions.MazeSizeMissmatchException;
import model.Maze;
import org.junit.*;

import java.io.FileNotFoundException;

public class MazeLoaderTest {
    public MazeLoader loader;

    @Before
    public void setup(){
        loader = new MazeLoader();
    }

    @After
    public void packup(){
        loader = null;
    }

    public interface Failer{
        public boolean failFunction(String file, Class<?> t);
    }

    public Failer exceptionFailer = (file, errorType) -> {
        boolean out = false;
        try {
            loader.load("test/io/resources/" + file);
        } catch (Exception e){
            if (e.getClass() == errorType){
                out = true;
            }

        }
        return out;
    };

    public static int failCounter(String[] files, Failer failer, Class<?> t){
        int fails = 0;
        for (String file: files){
            if(failer.failFunction(file, t)){
                fails++;
            }
        }
        return fails;
    }

    //double, float, string, character
    @Test
    public void checkDimensionType() {
        String[] files = new String[] {"dim_not_integers_d.txt", "dim_not_integers_c.txt",
                "dim_not_integers_f.txt", "dim_not_integers_s.txt"};

        int fails = failCounter(files, exceptionFailer, IllegalArgumentException.class);

        Assert.assertEquals(files.length, fails);
    }

    //  - even (1)
    //  - negative (1)
    @Test
    public void checkDimensionValue() {
        String[] files = new String[] {"even_dimension.txt", "neg_dimensions.txt",
                "0_dimension.txt"};

        int fails = failCounter(files, exceptionFailer, IllegalArgumentException.class);

        Assert.assertEquals(files.length, fails);

    }

    //  - too many arguments (1)
    //  - too few arguments (1)
    @Test
    public void checkDimensionNumber() {
        String[] files = new String[] {"no_dimension.txt", "high_dimension.txt",
                "low_dimension.txt"};

        int fails = failCounter(files, exceptionFailer, IllegalArgumentException.class);

        Assert.assertEquals(files.length, fails);

    }

    //Invalid Filepath (1)
    @Test (expected = FileNotFoundException.class)
    public void fileDoesntExist() throws FileNotFoundException, MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException{
        loader.load("test/io/resources/nonexistent_maze.txt");
    }
    //null filepath
    @Test (expected = FileNotFoundException.class)
    public void nullFile() throws FileNotFoundException, MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException{
        loader.load(null);
    }
    //empty file
    @Test (expected = IllegalArgumentException.class)
    public void emptyFile() throws FileNotFoundException, MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException{
        loader.load("test/io/resources/empty.txt");
    }

    //inconsistent rows (1)
    //inconsistent last row with consistent previous rows (1)
    @Test
    public void wonky(){
        String[] files = new String[] {"wonky1.txt", "wonky2.txt"};

        int fails = failCounter(files, exceptionFailer, MazeMalformedException.class);

        Assert.assertEquals(files.length, fails);
    }

    //consistent rows of length too long (1)
    //consistent rows of length too short (1)
    //consistent rows with too many columns (1)
    //consistent rows with too few columns (1)
    @Test
    public void missMatchedHorizontalDimensions(){
        String[] files = new String[] {
        "width_too_long.txt", "width_too_short.txt"};

        int fails = failCounter(files, exceptionFailer, MazeSizeMissmatchException.class);

        Assert.assertEquals(files.length, fails);
    }

    @Test
    public void missMatchedVerticalDimensions(){
        String[] files = new String[] {
                "length_too_long.txt", "length_too_short.txt"};

        int fails = failCounter(files, exceptionFailer, MazeSizeMissmatchException.class);

        Assert.assertEquals(files.length, fails);
    }

    @Test (expected = MazeSizeMissmatchException.class)
    public void tooLong() throws FileNotFoundException, MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException{
        loader.load("test/io/resources/length_too_long.txt");
    }

    @Test (expected = MazeSizeMissmatchException.class)
    public void tooShort() throws FileNotFoundException, MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException{
        loader.load("test/io/resources/length_too_short.txt");
    }

    @Test (expected = MazeSizeMissmatchException.class)
    public void widthtooShort() throws FileNotFoundException, MazeMalformedException,
            MazeSizeMissmatchException, IllegalArgumentException{
        loader.load("test/io/resources/width_too_short.txt");
    }

    //a single invalid character (2)
    //  - general
    //  - last
    @Test
    public void invalidCharacters(){
        String[] files = new String[] {
                "singleblip1.txt", "singleblip2.txt"};

        int fails = failCounter(files, exceptionFailer, IllegalArgumentException.class);

        Assert.assertEquals(files.length, fails);
    }

    //too many start/end characters (2)

    //no end/start (2)

    //two end characters and no start characters (and vise versa) (2)


    //valid mazes:

    //mixed '.' and ' ' characters (1)

    //height =/= width (1)

    //unsolvable maze (1)

    //not surrounded by walls (horizontal/vertical walls) (3)

    //absolute vs relative file path (1)

    //really large (1)

    //small cases 3x5, 5x3 (2)

    //random start and end points
}
