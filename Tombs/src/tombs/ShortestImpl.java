package tombs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestImpl extends Shortest {

    public ShortestImpl(int n, int m){
        super(n,m);
    }
    // @Override
    // public List<Coord> nearest(int row, int column) {
    //     // TODO Auto-generated method stub
    //     //throw new UnsupportedOperationException("Unimplemented method 'nearest'");
        
    // }

    // @Override
    // public boolean markPaths() {
    //     // TODO Auto-generated method stub
    //     //throw new UnsupportedOperationException("Unimplemented method 'markPaths'");
    // }

    // @Override
    // public Coord prev(Coord c) {
    //     // TODO Auto-generated method stub
    //     //throw new UnsupportedOperationException("Unimplemented method 'prev'");
    // }

    // @Override
    // public List<Coord> findPath() {
    //     // TODO Auto-generated method stub
    //     //throw new UnsupportedOperationException("Unimplemented method 'findPath'");
    // }
    /**
     * This method should return all the coordinates of
     * all (non-diagonally) adjacent squares that are not wall's, e.g.,
     * hash symbols (#).
     * The grid that you are working with is stored
     * in the field variable named "grid." If the value at
     * grid[row][column] == WALL, that means that there's a
     * wall at (row,column).
     *
     * To see what this means, consider the 5x3 grid
     * below. When nearest(1,3) is called, it should return
     * [(0,3), (1,4), (2,3)]. When nearest(2,2) is called,
     * it should return [(2,1), (2,3)].
     * <pre>
     *     0 1 2 3 4
     *   +----------+
     * 0 | . # . . .|
     * 1 | . # # . .|
     * 2 | . . . . .|
     *   +----------+
     * </pre>
     */
    
    
    @Override
    public List<Coord> nearest(int row, int column) {
        List<Coord> adjacentSquares = new ArrayList<>();
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Check the square above
        if (row > 0 && grid[row - 1][column] != WALL) {
            adjacentSquares.add(new Coord(row - 1, column));
        }

        // Check the square below
        if (row < numRows - 1 && grid[row + 1][column] != WALL) {
            adjacentSquares.add(new Coord(row + 1, column));
        }

        // Check the square to the left
        if (column > 0 && grid[row][column - 1] != WALL) {
            adjacentSquares.add(new Coord(row, column - 1));
        }

        // Check the square to the right
        if (column < numCols - 1 && grid[row][column + 1] != WALL) {
            adjacentSquares.add(new Coord(row, column + 1));
        }

        return adjacentSquares;
    }

    
    
    /**
     * The function markPaths() will help us compute the
     * distance from the start to the end. Given a grid
     * like this:
     * +---------+
     * | . # . # |
     * | . . # . |
     * | # . # . |
     * | # . . . |
     * | . . # . |
     * +---------+
     *
     * The algorithm below will fill in the squares with
     * the number of moves needed to reach it.
     *
     * +---------+
     * | 1 # . # |
     * | 2 3 # 9 |
     * | # 4 # 8 |
     * | # 5 6 7 |
     * | 7 6 # 8 |
     * +---------+
     *
     * The implementation looks like this:
     *
     * 0: Create a queue of Coord objects. Use a LinkedList as the queue implementation.
     *
     * 1: Put a 1 in the START square (i.e. grid[START.row][START.column]=1).
     *    Note: START is a constant that is set to the top left corner of the screen.
     *
     * 2: Add the START square in the queue.
     *
     * 3. begin loop
     *
     * 4. If the queue is empty, return false.
     *
     * 5: Remove a value c from the queue.
     *    Note: the remove() function on the queue
     *          returns the value removed. You need
     *          to capture that in a variable.
     *
     * 6: For each empty square p (i.e. each square with a value of EMPTY) near c....
     *    Note: p and c are variables of type Coord.
     *    Note: "for each" means you make a loop.
     *    Note: use the nearest() method you defined to help
     *    you with this step.
     *
     * 6(a): Update the value of the grid at p (i.e. grid[p.row][p.column])
     *       to be 1 more than the value at c.
     *
     * 6(b): Add p to the queue.
     *
     * 6(c): If p is equal to END, then exit and return true.
     *
     * 7: go to step (3)
     */
    @Override
    public boolean markPaths() {
        // Step 0: Create a queue of Coord objects
        Queue<Coord> queue = new LinkedList<>();

        // Step 1: Put a 1 in the START square
        grid[START.row][START.column] = 1;

        // Step 2: Add the START square to the queue
        queue.add(START);

        // Step 3: Begin loop
        while (!queue.isEmpty()) {
            // Step 4: If the queue is empty, return false
            // (This is not explicitly mentioned in the algorithm description,
            // but it's necessary to prevent an infinite loop)
            if (queue.isEmpty()) {
                return false;
            }

            // Step 5: Remove a value c from the queue
            Coord c = queue.remove();

            // Step 6: For each empty square p near c
            List<Coord> adjacentSquares = nearest(c.row, c.column);
            for (Coord p : adjacentSquares) {
                // Step 6(a): Update the value of the grid at p
                // to be 1 more than the value at c
                grid[p.row][p.column] = grid[c.row][c.column] + 1;

                // Step 6(b): Add p to the queue
                queue.add(p);

                // Step 6(c): If p is equal to END, then exit and return true
                if (p.equals(END)) {
                    return true;
                }
            }
        }

        // Step 7: If no path to END is found, return false
        return false;
    }

    
    
    
    /**
     * Find a nearby cell that has a value one lower
     * than the cell identified by row and column.
     * +---------+
     * | 1 # . # |
     * | 2 3 # 9 |
     * | # 4 # 8 |
     * | # 5 6 7 |
     * | 7 6 # 8 |
     * +---------+
     * So in the above grid[1][2] == 4. The nearest
     * cell with one less value is (1,1), which
     * has a value of 3 (grid[1][1] == 3).
     *
     * If there is no cell with a lower number,
     * return null.
     */
    @Override
    public Coord prev(Coord c) {
        // Get the value of the current cell
        int currentValue = grid[c.row][c.column];

        // Check adjacent cells for a value one less than the current value
        List<Coord> adjacentCells = nearest(c.row, c.column);
        for (Coord adjacentCell : adjacentCells) {
            int adjacentValue = grid[adjacentCell.row][adjacentCell.column];
            if (adjacentValue == currentValue - 1) {
                return adjacentCell; // Found the adjacent cell with the lower value
            }
        }

        // If no adjacent cell with a lower value is found, return null
        return null;
    }

    
    
    
    /**
     * Return a List of coords describing
     * the shortest path through the grid.
     *
     * Basically, this starts with the result
     * of markPaths() and removes everything that isn't
     * needed. So if you start with this:
     * +---------+
     * | 1 # . # |
     * | 2 3 # 9 |
     * | # 4 # 8 |
     * | # 5 6 7 |
     * | 7 6 # 8 |
     * +---------+
     * You will finish with this:
     * +---------+
     * | 1 # . # |
     * | 2 3 # . |
     * | # 4 # . |
     * | # 5 6 7 |
     * | . . # 8 |
     * +---------+
     *
     * 0: Create a list of Coord objects named path.
     *
     * 1. Add END to the list.
     *
     * 2. Create a variable of type Coord named c and
     *    set it to END.
     *
     * 3. Begin Loop...
     *
     * 4. Call the prev(c) and assign the result to p.

     * 5. if p is not null...
     *
     * 5(a) add p to the list named path.
     *    
     * 5(b) Set c to p.
     *
     * 6. If p is null, return the list named path.
     *
     * 7. Go to step (3)
     */
    @Override
    public List<Coord> findPath() {
        // Create a list to store the path
        List<Coord> path = new ArrayList<>();

        // Add the END coordinate to the path
        path.add(END);

        // Set the current coordinate to END
        Coord c = END;

        // Begin loop
        while (true) {
            // Call the prev() method to get the previous coordinate
            Coord p = prev(c);

            // If the previous coordinate is not null
            if (p != null) {
                // Add the previous coordinate to the path
                path.add(p);

                // Set the current coordinate to the previous coordinate
                c = p;
            } else {
                // If the previous coordinate is null, return the path
                return path;
            }
        }
    }
    
}



