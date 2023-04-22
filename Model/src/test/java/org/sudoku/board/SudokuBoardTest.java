package org.sudoku.board;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudoku.exceptions.CloneSudokuBoardException;
import org.sudoku.solver.BacktrackingSudokuSolver;
import org.sudoku.solver.SudokuSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    private SudokuBoard s1;
    private SudokuSolver solver;

    private static Logger logger = LogManager.getLogger(SudokuBoard.class);

    @BeforeEach
    public void setUp() {
        solver = new BacktrackingSudokuSolver();
        s1 = new SudokuBoard(solver);
    }

    @Test
    public void getterAndSetter() {
        s1.setBoard(1, 1, 5);
        assertEquals(s1.getBoard(1, 1), 5);
    }

    @Test
    public void getRow() {
        s1.solveGame();
        SudokuRow sudokuRow = s1.getRow(0);

        SudokuRow sudokuRow2 = new SudokuRow(0);
        for (int i = 0; i < 9; i++) {
            sudokuRow2.setSudokuField(i, s1.getBoard(0, i));
        }

        assertTrue(sudokuRow.equals(sudokuRow2) && sudokuRow2.equals(sudokuRow));

        sudokuRow2.setSudokuField(0, 0);
        assertFalse(sudokuRow.equals(sudokuRow2) || sudokuRow2.equals(sudokuRow));
    }

    @Test
    public void getColumn() {
        s1.solveGame();
        SudokuColumn sudokuColumn = s1.getColumn(0);

        SudokuColumn sudokuColumn2 = new SudokuColumn(0);
        for (int i = 0; i < 9; i++) {
            sudokuColumn2.setSudokuField(i, s1.getBoard(i, 0));
        }

        assertTrue(sudokuColumn.equals(sudokuColumn2) && sudokuColumn2.equals(sudokuColumn));

        sudokuColumn2.setSudokuField(0, 0);
        assertFalse(sudokuColumn.equals(sudokuColumn2) || sudokuColumn2.equals(sudokuColumn));
    }

    @Test
    public void getBox() {
        s1.solveGame();
        SudokuBox sudokuBox = s1.getBox(1, 2);

        int x = 0;
        SudokuBox sudokuBox2 = new SudokuBox(0, 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudokuBox2.setSudokuField(x, s1.getBoard(sudokuBox2.getXboxNumber() + i, sudokuBox2.getYboxNumber() + j));
                x++;
            }
        }

        assertTrue(sudokuBox.equals(sudokuBox2) && sudokuBox2.equals(sudokuBox));

        sudokuBox2.setSudokuField(0, 0);
        assertFalse(sudokuBox.equals(sudokuBox2) || sudokuBox2.equals(sudokuBox));
    }

    @Test
    public void checkBoardTest() {
        s1.solveGame();

        //check that checkBoard works correctly
        assertTrue(s1.checkBoard());    //should return true
        assertTrue(myCheckBoardForTest(s1));

        s1.setBoard(1, 1, 5);
        s1.setBoard(1, 2, 5);

        assertFalse(s1.checkBoard());   //should return false
        assertFalse(myCheckBoardForTest(s1));
    }

    @Test
    public void boardCorrectness() {
        s1.solveGame();                 //check when board is correct
        assertTrue(s1.checkBoard());

        s1.setBoard(1, 1, 5);
        s1.setBoard(1, 2, 5);
        assertFalse(s1.checkBoard());   //check row incorrect

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                s1.setBoard(i, j, 0);
            }
        s1.setBoard(1, 1, 5);
        s1.setBoard(2, 1, 5);
        assertFalse(s1.checkBoard());   //check column incorrect


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s1.setBoard(i, j, 0);
            }
        }
        s1.setBoard(1, 1, 5);
        s1.setBoard(2, 2, 5);
        assertFalse(s1.checkBoard()); //check square incorrect

    }

    @Test
    public void toStringTest() {
        assertNotNull(s1.toString());
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard s2 = new SudokuBoard(solver);
        assertEquals(s2.hashCode(), s1.hashCode());

        s1.solveGame();
        assertNotEquals(s2.hashCode(), s1.hashCode());
    }

    @Test
    public void equalsTest() {
        SudokuBoard s2 = new SudokuBoard(solver);
        assertTrue(s1.equals(s2) && s2.equals(s1));

        s1.solveGame();
        assertFalse(s1.equals(s2) || s2.equals(s1));

        SudokuField sudokuField = new SudokuField();
        assertFalse(s1.equals(sudokuField));

        assertTrue(s1.equals(s1));
        assertFalse(s1.equals(null));
    }
    @Test
    public void equalsHashCodeTest() {
        SudokuBoard s2 = new SudokuBoard(solver);
        assertEquals(s2.hashCode(), s1.hashCode());
        assertTrue(s1.equals(s2) && s2.equals(s1));
        assertFalse(s1.equals(null));
        s1.solveGame();
        assertNotEquals(s2.hashCode(), s1.hashCode());
        assertFalse(s1.equals(s2) || s2.equals(s1));
    }

    @Test
    public void cloneTest(){
        s1.solveGame();
        try {
            SudokuBoard sudokuBoard2 = s1.clone();
            assertTrue(s1.equals(sudokuBoard2));
            sudokuBoard2.setBoard(0,0, 0);
            assertNotEquals(s1, sudokuBoard2);
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage());
            throw new CloneSudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneableErrKey"), e);
        }
    }

    @Test
    public void setLanguage(){
        SudokuBoard.setLan("pl");
        assertEquals("pl", SudokuBoard.getLan());

        s1.showBoardLogger();
    }

    private boolean myCheckBoardForTest(SudokuBoard sudokuBoard) {
        for (int i = 0; i < 9; i++) {
            SudokuRow row = sudokuBoard.getRow(i);
            SudokuColumn column = sudokuBoard.getColumn(i);
            if (!row.verify()) {
                return false;
            }
            if (!column.verify()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                SudokuBox box = sudokuBoard.getBox(i, j);
                if (!box.verify()) {
                    return false;
                }
            }
        }
        return true;
    }
}