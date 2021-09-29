import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    private final Tile[][] tileArray;
    private final double X;
    private final double Y;
    int arrayHeight, arrayWidth;
    int completed; // if board is not completed 0, completed incorrectly 1, completed correctly 2

    public Board(Frame frame) {
        tileArray = new Tile[9][9];
        this.X = frame.getWidth() / 12.0;
        this.Y = frame.getHeight() / 12.0;
        arrayHeight = tileArray.length;
        arrayWidth = tileArray[0].length;
        this.completed = 0;     // determines if the board is completed,    0 = still working on it
        //                                          1 = there is an error
        //                                          2 = there are no errors

        // populates board by creating a tile, and adding tile to an arraylist
        for (int row = 0; row < arrayHeight; row++) {
            for (int col = 0; col < arrayWidth; col++) {
                Tile tile = new Tile(X, Y);
                // sets x and y coordinate of a tile
                tile.setXY(X + (col * tile.getHEIGHT()), Y + (row * tile.getWIDTH()));
                // sets the position of the tile on the board
                tile.setPosXY(row, col);
                tileArray[row][col] = tile;
            }
        }
    }


    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int isCompleted() {
        return completed;
    }

    // draws the board on the screen
    public void drawBoard(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // draws all 81 tiles
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tileArray[i][j].drawTile(g);
            }
        }

        // draws the big 9 tiles
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double tile_width = tileArray[i][j].getWIDTH();
                g2.setStroke(new java.awt.BasicStroke(4));
                Rectangle2D bigRect = new Rectangle2D.Double(this.X + (tile_width * 3 * i), this.Y + (tile_width * 3 * j), tile_width * 3, tile_width * 3);
                g2.draw(bigRect);
            }
        }

        // draws an outline based on the completion of the puzzle
        // if there are no errors, draw a big green square around the board
        if (completed == 2) {
            g2.setColor(Color.green);
            Rectangle2D completedRectangle = new Rectangle2D.Double(this.X - 3, this.Y - 3, (tileArray[0][0].getWIDTH() * 9) + 6, (tileArray[0][0].getWIDTH() * 9) + 6);
            g2.draw(completedRectangle);
        }
        // if there is at least 1 error, draw a big red square around the board
        else if (completed == 1) {
            g2.setColor(Color.red);
            Rectangle2D completedRectangle = new Rectangle2D.Double(this.X - 3, this.Y - 3, (tileArray[0][0].getWIDTH() * 9) + 6, (tileArray[0][0].getWIDTH() * 9) + 6);
            g2.draw(completedRectangle);
        }
    }

    // returns the tileArray
    public Tile[][] getTileArrayList() {
        return tileArray;
    }

    // returns tile at x and y
    public Tile getTile(int x, int y) {
        return tileArray[x][y];
    }

    // an attempt to populate a sudoku board
    private void populateBoard() {
        ArrayList<Integer> numbers = new ArrayList<>();
        populateList(numbers);
        populateFirstRow(tileArray, numbers);
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                numbers.clear();
                populateList(numbers);
                setCol(j, numbers);
                setRow(i, numbers);
                setGrid(j, i, numbers);
                insertRandomNumber(j, i, numbers);
            }
        }
    }

    // sets a random number chosen in array as the tile number
    private void insertRandomNumber(int col, int row, ArrayList<Integer> array) {
        int rand = new Random().nextInt(array.size());
        int inserted_number = array.get(rand);
        tileArray[row][col].setNumber(inserted_number);
        array.remove((Integer) inserted_number);
    }

    // populates arraylist with 1-9
    private void populateList(ArrayList<Integer> list) {
        for (int k = 1; k <= 9; k++) {
            list.add(k);
        }
    }

    // populates the first row of the puzzle
    private void populateFirstRow(Tile[][] arr, ArrayList<Integer> list) {
        for (int i = 0; i < 9; i++) {
            int rand = new Random().nextInt(list.size());
            int inserted_number = list.get(rand);
            arr[0][i].setNumber(inserted_number);
            list.remove(rand);
        }
    }

    // checks the numbers in the row, and removes it from the list
    private void setRow(int row, ArrayList<Integer> list) {
        for (int i = 0; i < 9; i++) {
            int number = tileArray[row][i].getNumber();
            if (number != 0) {
                if (list.contains(number)) {
                    list.remove((Integer) number);
                }
            }
        }
    }

    // checks the numbers in the column, and removes it from the list
    private void setCol(int col, ArrayList<Integer> list) {
        for (int i = 0; i < 9; i++) {
            int number = tileArray[i][col].getNumber();
            if (number != 0) {
                if (list.contains(number)) {
                    list.remove((Integer) number);
                }
            }
        }
    }

    // returns true if number is between a and b (inclusive)
    private boolean isBetween(int a, int number, int b) {
        return (a <= number && number <= b);
    }

    // checks if tileArray[x][y]'s grid is a valid grid
    // removes a number from arraylist while checking
    private void setGrid(int col, int row, ArrayList<Integer> p) {
        if (isBetween(0, col, 2)) {
            if (isBetween(0, row, 2)) {
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
            if (isBetween(3, row, 5)) {
                for (int i = 3; i <= 5; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
            if (isBetween(6, row, 8)) {
                for (int i = 6; i <= 8; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
        }
        if (isBetween(3, col, 5)) {
            if (isBetween(0, row, 2)) {
                for (int i = 0; i <= 2; i++) {
                    for (int j = 3; j <= 5; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
            if (isBetween(3, row, 5)) {
                for (int i = 3; i <= 5; i++) {
                    for (int j = 3; j <= 5; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
            if (isBetween(6, row, 8)) {
                for (int i = 6; i <= 8; i++) {
                    for (int j = 3; j <= 5; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
        }
        if (isBetween(6, col, 8)) {
            if (isBetween(0, row, 2)) {
                for (int i = 0; i <= 2; i++) {
                    for (int j = 6; j <= 8; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
            if (isBetween(3, row, 5)) {
                for (int i = 3; i <= 5; i++) {
                    for (int j = 6; j <= 8; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
            if (isBetween(6, row, 8)) {
                for (int i = 6; i <= 8; i++) {
                    for (int j = 6; j <= 8; j++) {
                        if (p.contains(tileArray[i][j].getNumber())) {
                            p.remove((Integer) tileArray[i][j].getNumber());
                        }
                    }
                }
            }
        }
    }

    // generates a board until it is a full valid sudoku board
    public void makeBoard() {
        boolean generate = false;
        while (!generate) {
            try {
                clearBoard();
                populateBoard();
                if (getTile(8, 8).getNumber() != 0) {
                    generate = true;
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    // sets the difficulty of the board
    // 0 is easy, 1 is normal, 2 is hard
    public void setDifficulty(int difficulty) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int rand = new Random().nextInt(81);
                if (difficulty == 0) {
                    if (rand >= 65) {
                        tileArray[i][j].setNumber(0);
                    }
                }
                if (difficulty == 1) {
                    if (rand >= 43) {
                        tileArray[i][j].setNumber(0);
                    }
                }
                if (difficulty == 2) {
                    if (rand >= 19) {
                        tileArray[i][j].setNumber(0);
                    }
                }
            }
        }
    }

    // sets all the tiles to 0
    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tileArray[i][j].setNumber(0);
                tileArray[i][j].setLocked(false);
            }
        }
    }

    // if the tile number is not 0, the tile number will be unable to change
    public void lockBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tileArray[i][j].getNumber() != 0) {
                    tileArray[i][j].setLocked(true);
                }
            }
        }
    }

    // when the board is full, verifies if it is a completed board, or if there are errors
    // if there are errors,     set completed = 1
    // if no errors,            set completed = 2
    public void verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (checkCol(i, j) || checkRow(i, j) || checkGrid(i, j)) {
                    setCompleted(1);
                    return;
                }
            }
        }
        setCompleted(2);
    }

    // checks to see if the column has duplicate numbers
    // returns true if it does
    private boolean checkCol(int row, int col) {
        for (int i = 0; i < 9; i++) {
            if (tileArray[row][col].getNumber() == tileArray[i][col].getNumber() && i != row) {
                return true;
            }
        }
        return false;
    }

    // checks to see if the row has duplicate numbers
    // returns true if it does
    private boolean checkRow(int row, int col) {
        for (int i = 0; i < 9; i++) {
            if (tileArray[row][col].getNumber() == tileArray[row][i].getNumber() && i != col) {
                return true;
            }
        }
        return false;
    }

    // checks each 3x3 grid to see if it contains a duplicate number
    // returns true if it does
    // we only need to check the bottom right 4 tiles of each 3x3 grid
    // since the rest will be checked by the checkRow and checkCol methods
    private boolean checkGrid(int row, int col) {
        if (row % 3 == 0) {
            if (col % 3 == 0) {
                return tileArray[row][col].getNumber() == tileArray[row + 1][col + 1].getNumber() ||
                        tileArray[row][col].getNumber() == tileArray[row + 1][col + 2].getNumber() ||
                        tileArray[row][col].getNumber() == tileArray[row + 2][col + 1].getNumber() ||
                        tileArray[row][col].getNumber() == tileArray[row + 2][col + 2].getNumber();
            }
        }
        return false;
    }


}
