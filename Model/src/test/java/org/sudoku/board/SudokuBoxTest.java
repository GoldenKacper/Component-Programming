package org.sudoku.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sudoku.exceptions.CloneSudokuBoardException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {
    SudokuBox sudokuBox1;
    SudokuBox sudokuBox2;

    @BeforeEach
    public void setUp() {
        sudokuBox1 = new SudokuBox(1, 2);
        sudokuBox2 = new SudokuBox(4, 7);
    }

    @Test
    void getXboxnumber() {
        assertEquals(sudokuBox1.getXboxNumber(), 0);
        assertEquals(sudokuBox2.getXboxNumber(), 3);
    }

    @Test
    void getYboxnumber() {
        assertEquals(sudokuBox1.getYboxNumber(), 0);
        assertEquals(sudokuBox2.getYboxNumber(), 6);
    }

    @Test
    public void toStringTest() {
        assertNotNull(sudokuBox1.toString());
    }

    @Test
    public void hashCodeTest() {
        SudokuBox sudokuBox3 = new SudokuBox(0, 1);
        assertEquals(sudokuBox3.hashCode(), sudokuBox1.hashCode());

        sudokuBox3.setSudokuField(1, 5);
        assertNotEquals(sudokuBox3.hashCode(), sudokuBox1.hashCode());
    }

    @Test
    public void equalsTest() {
        SudokuBox sudokuBox3 = new SudokuBox(1, 1);
        assertTrue(sudokuBox1.equals(sudokuBox3) && sudokuBox3.equals(sudokuBox1));

        sudokuBox3.setSudokuField(4, 4);
        assertFalse(sudokuBox1.equals(sudokuBox3) || sudokuBox3.equals(sudokuBox1));

        SudokuField sudokuField = new SudokuField();
        assertFalse(sudokuBox1.equals(sudokuField));

        assertTrue(sudokuBox1.equals(sudokuBox1));
        assertFalse(sudokuBox1.equals(null));
    }

    @Test
    public void equalsHashCodeTest() {
        SudokuBox sudokuBox3 = new SudokuBox(1, 1);
        assertEquals(sudokuBox3.hashCode(), sudokuBox1.hashCode());
        assertTrue(sudokuBox1.equals(sudokuBox3) && sudokuBox3.equals(sudokuBox1));
        assertFalse(sudokuBox1.equals(null));
        sudokuBox3.setSudokuField(1, 5);
        assertNotEquals(sudokuBox3.hashCode(), sudokuBox1.hashCode());
        assertFalse(sudokuBox1.equals(sudokuBox3) || sudokuBox3.equals(sudokuBox1));
    }

    @Test
    public void cloneTest(){
        sudokuBox1.setSudokuField(0, 5);
        sudokuBox1.setSudokuField(1, 6);
        try {
            SudokuBox sudokuBox3 = sudokuBox1.clone();
            assertTrue(sudokuBox1.equals(sudokuBox3));
            sudokuBox1.setSudokuField(3, 9);
            assertNotEquals(sudokuBox1, sudokuBox3);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            throw new CloneSudokuBoardException(SudokuBoard.getLanguageVersion().getString("cloneableErrKey"), e);
        }
    }
}