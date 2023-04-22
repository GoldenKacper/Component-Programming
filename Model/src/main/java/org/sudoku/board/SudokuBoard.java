package org.sudoku.board;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudoku.solver.SudokuSolver;


/**
 * The type Sudoku board.
 */

public class SudokuBoard implements Serializable, Cloneable {
    public static final int size = 9;
    public static final int boardSize = 81;
    //pole klasy reprezentujace tablice sudoku 9*9
    //private SudokuField[][] board = new SudokuField[9][9];
    private List<SudokuField> board = Arrays.asList(new SudokuField[boardSize]);

    private final SudokuSolver solver;

    private static String lan = "en"; // en | pl

    private transient Logger logger = LogManager.getLogger(getClass());

    public static ResourceBundle languageVersion;

    public static ResourceBundle getLanguageVersion() {
        return languageVersion;
    }

    /**
     * Instantiates a new Sudoku board.
     *
     * @param solver the solver
     */
    public SudokuBoard(SudokuSolver solver) {
        languageVersion = ResourceBundle.getBundle("MyResources_" + lan);
        this.solver = solver;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(backToTheOldBoard(i, j), new SudokuField());
            }
        }
    }

    /**
     * Gets the current board's filed value.
     *
     * @param row the row
     * @param col the column
     * @return the value of the current field
     */
    public int getBoard(int row, int col) {  //getter
        return board.get(backToTheOldBoard(row, col)).getFieldValue();
    }

    /**
     * Sets the current board's field value.
     *
     * @param row   the row
     * @param col   the column
     * @param value the value
     */
    public void setBoard(int row, int col, int value) {
        board.get(backToTheOldBoard(row, col)).setFieldValue(value);
    }

    public static void setLan(String lan) {
        SudokuBoard.lan = lan;
    }

    public static String getLan() {
        return lan;
    }

    /**
     * Checks board correction.
     *
     * @return the boolean
     */
    public boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            SudokuRow row = this.getRow(i);
            SudokuColumn column = this.getColumn(i);
            if (!row.verify()) {
                return false;
            }
            if (!column.verify()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                SudokuBox box = this.getBox(i, j);
                if (!box.verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets row.
     *
     * @param row the row
     * @return the row object
     */
    public SudokuRow getRow(int row) {
        SudokuRow sudokuRow = new SudokuRow(row);
        for (int i = 0; i < 9; i++) {
            sudokuRow.setSudokuField(i, board.get(backToTheOldBoard(row, i)).getFieldValue());
        }
        return sudokuRow;
    }

    /**
     * Gets column.
     *
     * @param col the column
     * @return the column object
     */
    public SudokuColumn getColumn(int col) {
        SudokuColumn sudokuColumn = new SudokuColumn(col);
        for (int i = 0; i < 9; i++) {
            sudokuColumn.setSudokuField(i, board.get(backToTheOldBoard(i, col)).getFieldValue());
        }
        return sudokuColumn;
    }

    /**
     * Gets small box.
     *
     * @param row the row
     * @param col the col
     * @return the small box object
     */
    public SudokuBox getBox(int row, int col) {
        int x = 0;                                  // counter
        SudokuBox sudokuBox = new SudokuBox(row, col);  // first coordinates of box
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudokuBox.setSudokuField(x,
                        board.get(backToTheOldBoard(i + sudokuBox.getXboxNumber(),
                                j + sudokuBox.getYboxNumber())).getFieldValue());
                x++;
            }
        }
        return sudokuBox;
    }

    /**
     * Solves the sudoku.
     */
    public void solveGame() {  //rozwiazywanie sudoku
        solver.solve(this);
    }

    /**
     * Back to the old board's numeration.
     * Give old indexes of numeration, and you will receive a new one
     *
     * @param row the row
     * @param col the col
     * @return the index of new list
     */
    private int backToTheOldBoard(int row, int col) {
        return row * 9 + col;
    }

    /**
     * Shows the board.
     */
    public void showBoardLogger() {  //wyswietla tablice na ekranie
        logger.info("-------------------------------------");
        for (int i = 0; i < 9; i++) {
            logger.info("|  ");
            for (int j = 0; j < 9; j++) {
                if (board.get(backToTheOldBoard(i, j)).getFieldValue() == 0) {
                    logger.info("   ");
                } else {
                    logger.info(board.get(backToTheOldBoard(i, j)).getFieldValue() + "  ");
                }
                if (j == 2 || j == 5 || j == 8) {
                    logger.info("|  ");
                }
            }
            if (i == 2 || i == 5) {
                logger.info("\n");
                for (int k = 0; k < 37; k++) {
                    logger.info("-");
                }
            }
            logger.info("\n");
        }
        logger.info("-------------------------------------");
    }

    public void showBoard() {  //wyswietla tablice na ekranie
        System.out.println("-------------------------------------");
        for (int i = 0; i < 9; i++) {
            System.out.print("|  ");
            for (int j = 0; j < 9; j++) {
                if (board.get(backToTheOldBoard(i, j)).getFieldValue() == 0) {
                    System.out.print("   ");
                } else {
                    System.out.print(board.get(backToTheOldBoard(i, j)).getFieldValue() + "  ");
                }
                if (j == 2 || j == 5 || j == 8) {
                    System.out.print("|  ");
                }
            }
            if (i == 2 || i == 5) {
                System.out.println();
                for (int k = 0; k < 37; k++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------");
    }

    /**
     * To string method, it converts objects into string.
     *
     * @return converted object into string
     */
    @Override
    public String toString() {
        ToStringBuilder controlString = new ToStringBuilder(this);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                controlString.append(" (row,col: " + Integer.toString(i)
                        + " " + Integer.toString(j) + ") ", getBoard(i, j));
            }
        }
        controlString.append("solver", solver);
        return controlString.toString();
    }

    /**
     * Hash code method, calculate the unique integer for objects.
     *
     * @return calculated unique integer
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37)
                .append(board).append(solver).toHashCode();
    }

    /**
     * Equals method, compare two object and return if that they are the same or not.
     *
     * @param obj the object which will be comparing
     * @return the boolean value of this comparison
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SudokuBoard) {
            return new EqualsBuilder().append(board, ((SudokuBoard) obj).board)
                    .append(solver, ((SudokuBoard) obj).solver).isEquals();
        }
        return false;
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clone = (SudokuBoard) super.clone();
        clone.board = Arrays.asList(new SudokuField[boardSize]);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                clone.board.set(backToTheOldBoard(i, j), new SudokuField());
                clone.setBoard(i, j, this.getBoard(i, j));
            }
        }
        return clone;
    }

    public SudokuField getSudokuField(int row, int col) {
        return board.get(backToTheOldBoard(row, col));
    }
}