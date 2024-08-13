package model;

/**
 * Four relative directions from the perspective of a {@link MazeEntity}.
 * @see Directions#opposite()
 * @see Directions#add(Directions)
 */
public enum Directions {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    /**
     * Gets the opposite direction.
     * @return opposite direction.
     */
    public Directions opposite(){
        Directions out = null;
        switch(this){
            case LEFT -> out = RIGHT;
            case RIGHT -> out = LEFT;
            case UP -> out = DOWN;
            case DOWN -> out = UP;
        }
        return out;
    }

    /**
     * provides an integer representing the result of a type of vector algebra
     * with directions. Orthogonal directions sum to 0, opposite directions add destructively
     * and similar directions add constructively.
     * @param other direction to be added to.
     * @return 0 for orthogonal addition, -1 for opposite addition, 1 for similar addition.
     */
    public int add(Directions other){
        int out = 0;
        if (this == other){
            out++;
        } else if (this == other.opposite()){
            out--;
        }
        return out;
    }
}
