package org.sudoku.solver;

import org.sudoku.board.SudokuBoard;
import org.sudoku.board.SudokuBox;
import org.sudoku.board.SudokuColumn;
import org.sudoku.board.SudokuRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    private SudokuBoard s1;
    private SudokuSolver solver;

    @BeforeEach
    public void setUp() {
        solver = new BacktrackingSudokuSolver();
        s1 = new SudokuBoard(solver);
        s1.solveGame();
    }

    @Test
    public void differentBoards() {
        int[][] board = new int[9][9];

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                board[i][j] = s1.getBoard(i, j);

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                s1.setBoard(i, j, 0);

        s1.solveGame();

        assertTrue(differentBoardsCheck(s1, board));
    }

    @Test
    public void sudokuRules() {
        for(int i=0; i<9; i++){
            SudokuRow row = s1.getRow(i);
            SudokuColumn col = s1.getColumn(i);
            assertTrue(row.verify() && col.verify());
            for(int j=0; j<9; j++){
                SudokuBox box = s1.getBox(i, j);
                assertTrue(box.verify());
            }
        }
    }

    private boolean differentBoardsCheck(SudokuBoard s1, int[][] board) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (s1.getBoard(i, j) != board[i][j]) return true;
        return false;
    }
}