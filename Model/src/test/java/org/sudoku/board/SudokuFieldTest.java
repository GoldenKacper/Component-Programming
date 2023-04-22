package org.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sudoku.exceptions.CloneSudokuBoardException;
import org.sudoku.exceptions.NullPointerSudokuFieldException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {
    private SudokuField sudokuField;

    @BeforeEach
    public void setUp() {
        sudokuField = new SudokuField();
    }

    @Test
    public void setAndGet() {
        sudokuField.setFieldValue(3);
        assertEquals(sudokuField.getFieldValue(), 3);

        sudokuField.setFieldValue(12);
        assertNotEquals(sudokuField.getFieldValue(), 12);

        sudokuField.setFieldValue(-5);
        assertNotEquals(sudokuField.getFieldValue(), -5);
    }

    @Test
    public void constructors() {
        SudokuField sudokuField = new SudokuField(7);
        assertEquals(sudokuField.getFieldValue(), 7);

        SudokuField sudokuField2 = new SudokuField(12);
        assertNotEquals(sudokuField2.getFieldValue(), 12);

        SudokuField sudokuField3 = new SudokuField();
        assertEquals(sudokuField3.getFieldValue(), 0);
    }

    @Test
    public void toStringTest() {
        assertNotNull(sudokuField.toString());
    }

    @Test
    public void hashCodeTest() {
        SudokuField sudokuField2 = new SudokuField();
        assertEquals(sudokuField2.hashCode(), sudokuField.hashCode());

        sudokuField2.setFieldValue(5);
        assertNotEquals(sudokuField2.hashCode(), sudokuField.hashCode());
    }

    @Test
    public void equalsTest() {
        SudokuField sudokuField2 = new SudokuField();
        assertTrue(sudokuField.equals(sudokuField2) && sudokuField2.equals(sudokuField));

        sudokuField2.setFieldValue(3);
        assertFalse(sudokuField.equals(sudokuField2) || sudokuField2.equals(sudokuField));

        SudokuRow sudokuRow = new SudokuRow(0);
        assertFalse(sudokuField.equals(sudokuRow));

        assertTrue(sudokuField.equals(sudokuField));
        assertFalse(sudokuField.equals(null));
    }

    @Test
    public void equalsHashCodeTest() {
        SudokuField sudokuField2 = new SudokuField();
        assertEquals(sudokuField2.hashCode(), sudokuField.hashCode());
        assertTrue(sudokuField.equals(sudokuField2) && sudokuField2.equals(sudokuField));
        assertFalse(sudokuField.equals(null));
        sudokuField2.setFieldValue(5);
        assertNotEquals(sudokuField2.hashCode(), sudokuField.hashCode());
        assertFalse(sudokuField.equals(sudokuField2) || sudokuField2.equals(sudokuField));
    }

    @Test
    public void cloneTest() {
        sudokuField.setFieldValue(3);
        try {
            SudokuField sudokuField2 = sudokuField.clone();

            assertEquals(sudokuField2.getFieldValue(), 3);
            sudokuField2.setFieldValue(5);
            assertNotEquals(sudokuField, sudokuField2);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            throw new CloneSudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneableErrKey"), e);
        }
    }

    @Test
    public void compareTest() {
        sudokuField.setFieldValue(3);
        SudokuField sudokuField2 = new SudokuField(3);
        try {
            assertEquals(sudokuField.compareTo(sudokuField2), 0);
        } catch (NullPointerSudokuFieldException e) {
            System.err.println(e.getMessage());
            throw new NullPointerSudokuFieldException(SudokuBoard.getLanguageVersion().getString("nullObjectKey"), e);
        }
    }

    @Test
    public void compareException() {
        assertThrows(NullPointerSudokuFieldException.class, () -> {
            sudokuField.compareTo(null);
        });
    }
}
