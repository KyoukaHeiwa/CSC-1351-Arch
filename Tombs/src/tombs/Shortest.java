/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tombs;

/**
 *
 * @author steve
 */
import java.util.*;
import java.io.*;

/**
 * Find the shortest path through the Martian tomb.
 */
public abstract class Shortest {

    /** Grid is a 2d representation of the board on
      * which the map of the maze exists. As you
      * construct your code, you will assign numeric
      * values to each empty cell on the map (i.e.
      * cells which have the value EMPTY defined below).
      */
    int[][] grid;
    /** This is the code for a wall */
    public final static int WALL = Integer.MAX_VALUE;
    /** This is the code for an empty cell. */
    public final static int EMPTY = Integer.MAX_VALUE - 1;
    /** The start of the maze is START, which 
      * is the upper left hand corner. The END
      * is the lowier right.
      */
    final Coord START, END;
    /** The following two integers define the size
      * of the grid.
      */
    final int NROWS, NCOLS;

    LinkedList<Coord> q = new LinkedList<>();
    public final static Random RAND = new Random();
    static { RAND.setSeed(0); }

    public Shortest(int n,int m) {
        NROWS = n;
        NCOLS = m;
        grid = new int[n][m];
        clear();
        // always start at the top left...
        START = new Coord(0,0);
        // and end at the bottom right.
        END = new Coord(NROWS-1,NCOLS-1);
    }

    void clear() {
        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[i].length;j++) {
                if(grid[i][j] != WALL)
                    grid[i][j] = EMPTY;
            }
        }
    }

    /**
     * Attempt to place a wall on the map such
     * that it does not block progress through
     * the tomb.
     */
    boolean placeWall(boolean shouldExist) {
        for(int i=0;i<100;i++) {
            // A random row and column coordinate
            int row = RAND.nextInt(grid.length);
            int column = RAND.nextInt(grid[0].length);
            // is there a wall already on this square?
            if(grid[row][column] == WALL)
                continue;
            grid[row][column] = WALL;
            List<Coord> wallsBefore = getWalls();
            clear();
            boolean b = markPaths();
            List<Coord> wallsAfter = getWalls();
            assert wallsBefore.equals(wallsAfter) :
                "Calling markPaths() should not add or remove walls.";
            if(b) assert shouldExist;
            // Does a shortest path still exist?
            // If so, we're done.
            if(b) {
                clarify();
                print();
                checkMarked();
                checkPrev();
                return true;
            }
            // if the shortest path doesn't exist,
            // a wall shouldn't go here. Take it
            // away and try again.
            grid[row][column] = EMPTY;
        }
        return false;
    }

    /**
     * Convert a numerical value to
     * a letter for display purposes.
     */
    public char toChar(int val) {
        if(val == Integer.MAX_VALUE)
            return '#';
        if(val == Integer.MAX_VALUE-1)
            return '.';
        if(val <= '9'-'0')
            return (char)(val + '0');
        val -= '9'-'0';
        if(val <= 'z'-'a')
            return (char)(val+'a'-1);
        val -=  'z'-'a';
        if(val <= 'Z'-'A')
            return (char)(val+'A'-1);
        throw new Error();
    }

    /**
     * Create the map, displaying numerical values,
     * walls, and empty squares.
     */
    public String toString() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.print('+');
        for(int i=0;i<2*NCOLS;i++)
            pw.print("-");
        pw.println('+');

        for(int i=0;i<grid.length;i++) {
            pw.print('|');
            for(int j=0;j<grid[0].length;j++) {
                int val = grid[i][j];
                pw.print(' ');
                pw.print(toChar(val));
            }
            pw.println('|');
        }

        pw.print('+');
        for(int i=0;i<2*NCOLS;i++)
            pw.print("-");
        pw.println('+');
        return sw.toString();
    }

    public void print() {
        System.out.print(this);
    }

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
    public abstract List<Coord> nearest(int row,int column);

    /**
     * Check the nearest method to make sure
     * it is functioning properly.
     */
    void checkNearest() {
        Shortest s = makeShortest(3,5);
        s.grid[0][1] = WALL;
        s.grid[1][1] = WALL;
        s.grid[1][2] = WALL;
        s.grid[0][3] = 1;
        s.grid[2][3] = 2;
        s.print();
        checkNearest(s.nearest(1,3),"[(0,3), (1,4), (2,3)]");
        checkNearest(s.nearest(2,2),"[(2,1), (2,3)]");
        checkNearest(s.nearest(0,0),"[(1,0)]");
        checkNearest(s.nearest(2,4),"[(1,4), (2,3)]");
    }
    void checkNearest(List<Coord> c,String val) {
        Collections.sort(c);
        System.out.println(c);
        assert c.toString().equals(val) : "Incorrect list of nearest squares "+val;
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
    public abstract boolean markPaths();

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
    public abstract Coord prev(Coord c);

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
    public abstract List<Coord> findPath();

    public void checkPath(List<Coord> path) {
        for(int i=1;i<path.size();i++) {
            Coord c1 = path.get(i-1);
            Coord c2 = path.get(i);
            assert c1.row == c2.row || c1.column == c2.column : String.format("Cell %s is not adjacent to cell %s", c1, c2);
            assert c1.row+1 == c2.row || c1.row == c2.row+1 || c1.column+1 == c2.column || c1.column == c2.column+1 :
                String.format("Cell %s is not adjacent to cell %s", c1, c2);
            assert grid[c1.row][c1.column] == grid[c2.row][c2.column]+1 :
                String.format("Cell %s (value %d) is the next in the path, but its value is not one less than %s (value %d)",
                    c1, grid[c1.row][c1.column],
                    c2, grid[c2.row][c2.column]);
        }
        assert path.get(0).equals(END) : "The first path element is not the END square";
        assert path.get(path.size()-1).equals(START) : "The last path element is not the START square";
    }

    public void clarify() {
        List<Coord> path = findPath();
        assert path != null : "findPath() return null";
        assert path.size() != 0 : "findPath() returned an empty list";
        assert path.size() != 1 : "findPath() returned an list with 1 item";
        System.out.println("path.size() = "+path.size());
        checkPath(path);
        clear();
        for(int n=0;n<path.size();n++) {
            Coord c = path.get(n);
            grid[c.row][c.column] = path.size()-n;
        }
    }

    public List<Coord> getWalls() {
        List<Coord> walls = new ArrayList<>();
        for(int i=0;i<NROWS;i++) {
            for(int j=0;j<NCOLS;j++) {
                if(grid[i][j] == WALL)
                    walls.add(new Coord(i,j));
            }
        }
        return walls;
    }

    public void checkMarked() {
        Set<Integer> vals = new TreeSet<>();
        for(int i=0;i<NROWS;i++) {
            for(int j=0;j<NCOLS;j++) {
                if(grid[i][j] != WALL && grid[i][j] != EMPTY)
                    vals.add(grid[i][j]);
            }
        }
        assert vals.size() >= NROWS : "Too few marked cells: "+vals;
        assert vals.size() >= NCOLS : "Too few marked cells: "+vals;
        Integer last = null;
        for(Integer v : vals) {
            if(last == null) last = v;
            else {
                assert last + 1 == v : "Gap in marked cells at ["+last+" "+v+"]: "+vals;
                last = v;
            }
        }
    }
    public void checkPrev() {
        int count = 0;
        for(int i=0;i<NROWS;i++) {
            for(int j=0;j<NCOLS;j++) {
                if(grid[i][j] != WALL && grid[i][j] != EMPTY) {
                    Coord c = new Coord(i,j);
                    Coord p = prev(c);
                    if(p != null) {
                        assert grid[p.row][p.column] + 1 == grid[i][j] : "The prev() method returned a cell which did not have the right value at "+c;
                        count++;
                    }
                }
            }
        }
        assert count > 0 : "The prev() method did not find any previous cells on the grid";
    }

    static String className;

    public static Shortest makeShortest(int NROWS,int NCOLS) {
        try {
            Class<?> c = Class.forName(className);
            java.lang.reflect.Constructor con = c.getDeclaredConstructor(Integer.TYPE,Integer.TYPE);
            return (Shortest)con.newInstance(NROWS,NCOLS);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            assert false;
            throw new Error("Please enable assertions");
        } catch(AssertionError ae) {
        }
        if(args.length != 1) {
            System.err.println("Usage: java -ea Shortest ShortestImpl");
            System.exit(1);
        }
        className = args[0];
        final boolean generate = false;
        PrintWriter pw = null;
        if(generate) {
            pw = new PrintWriter(new File("Check.java"));
            pw.println("import java.util.*;");
            pw.println("public class Check {");
            pw.println("  Map<Integer,Integer> m = new HashMap<>();");
            pw.println("  public Check() {");
        }

        final int NROWS = 15, NCOLS = 30;
        Shortest s = makeShortest(NROWS,NCOLS);
        s.checkNearest();
        Check c = new Check();
        for(int i=0;i<NROWS*NCOLS/3;i++) {
            boolean b = s.placeWall(i < c.m.size());
            if(b) {
                if(generate) {
                    pw.printf("    m.put(%d,%d);%n",i,s.grid[s.END.row][s.END.column]);
                }
                assert c.m.get(i) == s.grid[s.END.row][s.END.column] : "Your algorithm did not find the shortest path";
            } else {
                if(i < c.m.size()) {
                    s.print();
                    assert false : "Your mark paths is incorrect";
                }
                break;
            }
        }
        if(generate) {
            pw.println("  }");
            pw.println("}");
            pw.close();
        }
        System.out.println("All tests passed.");
    }
}
