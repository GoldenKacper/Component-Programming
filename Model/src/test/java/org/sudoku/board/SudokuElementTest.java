package org.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sudoku.exceptions.CloneSudokuBoardException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SudokuElementTest {
    private SudokuElement sudokuElement;

    @BeforeEach
    public void setUp() {
        sudokuElement = new SudokuElement();

    }

    @Test
    public void setAndGetSudokuField() {
        sudokuElement.setSudokuField(0, 7);
        assertEquals(sudokuElement.getSudokuField(0), 7);
        assertNotEquals(sudokuElement.getSudokuField(0), 6);
    }

    @Test
    public void verify() {
        //setting example of filling board
        for (int i = 0; i < 9; i++) {
            sudokuElement.setSudokuField(i, i + 1);
        }

        //both should be correct
        assertTrue(sudokuElement.verify());
        assertTrue(myVerifyForTests(sudokuElement));

        //try to repeat any value
        sudokuElement.setSudokuField(1, 1);

        //both should be incorrect
        assertFalse(sudokuElement.verify());
        assertFalse(myVerifyForTests(sudokuElement));
    }

    @Test
    public void toStringTest() {
        assertNotNull(sudokuElement.toString());
    }

    @Test
    public void hashCodeTest() {
        SudokuElement sudokuElement2 = new SudokuElement();
        assertEquals(sudokuElement2.hashCode(), sudokuElement.hashCode());

        sudokuElement2.setSudokuField(0, 5);
        assertNotEquals(sudokuElement2.hashCode(), sudokuElement.hashCode());
    }

    @Test
    public void equalsTest() {
        SudokuElement sudokuElement2 = new SudokuElement();
        assertTrue(sudokuElement.equals(sudokuElement2) && sudokuElement2.equals(sudokuElement));

        sudokuElement2.setSudokuField(0, 3);
        assertFalse(sudokuElement.equals(sudokuElement2) || sudokuElement2.equals(sudokuElement));

        SudokuField sudokuField = new SudokuField();
        assertFalse(sudokuElement.equals(sudokuField));

        assertTrue(sudokuElement.equals(sudokuElement));
        assertFalse(sudokuElement.equals(null));
    }

    @Test
    public void equalsHashCodeTest() {
        SudokuElement sudokuElement2 = new SudokuElement();
        assertEquals(sudokuElement2.hashCode(), sudokuElement.hashCode());
        assertTrue(sudokuElement.equals(sudokuElement2) && sudokuElement2.equals(sudokuElement));
        assertFalse(sudokuElement.equals(null));
        sudokuElement2.setSudokuField(0, 3);
        assertNotEquals(sudokuElement2.hashCode(), sudokuElement.hashCode());
        assertFalse(sudokuElement.equals(sudokuElement2) || sudokuElement2.equals(sudokuElement));
    }

    @Test
    public void cloneTest(){
        sudokuElement.setSudokuField(0, 5);
        sudokuElement.setSudokuField(1, 6);
        try {
            SudokuElement sudokuElement2 = sudokuElement.clone();
            assertTrue(sudokuElement.equals(sudokuElement2));
            sudokuElement2.setSudokuField(0, 1);
            assertNotEquals(sudokuElement, sudokuElement2);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            throw new CloneSudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneableErrKey"), e);
        }
    }

    boolean myVerifyForTests(SudokuElement sE) {
        int[] sup = new int[9];

        for (int i = 0; i < 9; i++) {
            if (sE.getSudokuField(i) != 0) {
                sup[sE.getSudokuField(i) - 1]++;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (sup[i] >= 2) {
                return false;
            }
        }
        return true;
    }
}