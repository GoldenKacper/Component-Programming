package org.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sudoku.exceptions.CloneSudokuBoardException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    private SudokuRow sudokuRow;

    @BeforeEach
    public void setUp() {
        sudokuRow = new SudokuRow(2);
    }

    @Test
    void getRowNumber() {
        assertEquals(sudokuRow.getRowNumber(), 2);
    }

    @Test
    public void toStringTest() {
        assertNotNull(sudokuRow.toString());
    }

    @Test
    public void hashCodeTest() {
        SudokuRow sudokuRow2 = new SudokuRow(2);
        assertEquals(sudokuRow2.hashCode(), sudokuRow.hashCode());

        sudokuRow2.setSudokuField(0, 4);
        assertNotEquals(sudokuRow2.hashCode(), sudokuRow.hashCode());
    }

    @Test
    public void equalsTest() {
        SudokuRow sudokuRow2 = new SudokuRow(2);
        assertTrue(sudokuRow.equals(sudokuRow2) && sudokuRow2.equals(sudokuRow));

        sudokuRow2.setSudokuField(1, 7);
        assertFalse(sudokuRow.equals(sudokuRow2) || sudokuRow2.equals(sudokuRow));

        SudokuField sudokuField = new SudokuField();
        assertFalse(sudokuRow.equals(sudokuField));

        assertTrue(sudokuRow.equals(sudokuRow));
        assertFalse(sudokuRow.equals(null));
    }

    @Test
    public void equalsHashCodeTest() {
        SudokuRow sudokuRow2 = new SudokuRow(2);
        assertEquals(sudokuRow2.hashCode(), sudokuRow.hashCode());
        assertTrue(sudokuRow.equals(sudokuRow2) && sudokuRow2.equals(sudokuRow));
        assertFalse(sudokuRow.equals(null));
        sudokuRow2.setSudokuField(0, 4);
        assertNotEquals(sudokuRow2.hashCode(), sudokuRow.hashCode());
        assertFalse(sudokuRow.equals(sudokuRow2) || sudokuRow2.equals(sudokuRow));
    }

    @Test
    public void cloneTest(){
        sudokuRow.setSudokuField(0, 5);
        sudokuRow.setSudokuField(1, 6);
        try {
            SudokuRow sudokuRow2 = sudokuRow.clone();
            assertTrue(sudokuRow.equals(sudokuRow2));
            sudokuRow2.setSudokuField(6, 9);
            sudokuRow.setSudokuField(2, 4);
            assertNotEquals(sudokuRow, sudokuRow2);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            throw new CloneSudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneableErrKey"), e);
        }
    }
}