package org.sudoku.levels;

import org.junit.jupiter.api.Test;
import org.sudoku.board.SudokuBoard;
import org.sudoku.solver.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.*;

class EnumUsageTest {

    @Test
    void sudokuFieldsRemover() {
        EnumUsage enumUsageEasy = new EnumUsage(EnumerationType.beginer);
        EnumUsage enumUsageMedium = new EnumUsage(EnumerationType.medium);
        EnumUsage enumUsageHard = new EnumUsage(EnumerationType.hard);

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();

        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        sudokuBoard1.solveGame();

        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);
        sudokuBoard2.solveGame();

        SudokuBoard sudokuBoard3 = new SudokuBoard(solver);
        sudokuBoard3.solveGame();

        enumUsageEasy.sudokuFieldsRemover(sudokuBoard1);
        enumUsageMedium.sudokuFieldsRemover(sudokuBoard2);
        enumUsageHard.sudokuFieldsRemover(sudokuBoard3);

        assertNotEquals(sudokuBoard1, sudokuBoard2);
        assertNotEquals(sudokuBoard1, sudokuBoard3);
        assertNotEquals(sudokuBoard2, sudokuBoard3);
    }
}