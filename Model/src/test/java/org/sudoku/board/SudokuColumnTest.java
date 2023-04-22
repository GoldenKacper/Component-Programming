package org.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sudoku.exceptions.CloneSudokuBoardException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    private SudokuColumn sudokuColumn;

    @BeforeEach
    public void setUp() {
        sudokuColumn = new SudokuColumn(3);
    }

    @Test
    public void getColumnNumber() {
        SudokuColumn sudokuColumn = new SudokuColumn(0);
        assertEquals(sudokuColumn.getColumnNumber(), 0);
    }

    @Test
    public void toStringTest() {
        assertNotNull(sudokuColumn.toString());
    }

    @Test
    public void hashCodeTest() {
        SudokuColumn sudokuColumn2 = new SudokuColumn(3);
        assertEquals(sudokuColumn2.hashCode(), sudokuColumn.hashCode());

        sudokuColumn2.setSudokuField(6, 1);
        assertNotEquals(sudokuColumn2.hashCode(), sudokuColumn.hashCode());
    }

    @Test
    public void equalsTest() {
        SudokuColumn sudokuColumn2 = new SudokuColumn(3);
        assertTrue(sudokuColumn.equals(sudokuColumn2) && sudokuColumn2.equals(sudokuColumn));

        sudokuColumn2.setSudokuField(5, 2);
        assertFalse(sudokuColumn.equals(sudokuColumn2) || sudokuColumn2.equals(sudokuColumn));

        SudokuField sudokuField = new SudokuField();
        assertFalse(sudokuColumn.equals(sudokuField));

        assertTrue(sudokuColumn.equals(sudokuColumn));
        assertFalse(sudokuColumn.equals(null));
    }

    @Test
    public void equalsHashCodeTest() {
        SudokuColumn sudokuColumn2 = new SudokuColumn(3);
        assertEquals(sudokuColumn2.hashCode(), sudokuColumn.hashCode());
        assertTrue(sudokuColumn.equals(sudokuColumn2) && sudokuColumn2.equals(sudokuColumn));
        assertFalse(sudokuColumn.equals(null));
        sudokuColumn2.setSudokuField(1, 5);
        assertNotEquals(sudokuColumn2.hashCode(), sudokuColumn.hashCode());
        assertFalse(sudokuColumn.equals(sudokuColumn2) || sudokuColumn2.equals(sudokuColumn));
    }

    @Test
    public void cloneTest(){
        sudokuColumn.setSudokuField(0, 5);
        sudokuColumn.setSudokuField(1, 6);
        try {
            SudokuColumn sudokuColumn2 = sudokuColumn.clone();
            assertTrue(sudokuColumn.equals(sudokuColumn2));
            sudokuColumn2.setSudokuField(6, 8);
            assertNotEquals(sudokuColumn, sudokuColumn2);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            throw new CloneSudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneableErrKey"), e);
        }
    }
}