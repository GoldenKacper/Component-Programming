package org.sudoku.solver;

import org.sudoku.board.SudokuBoard;

/**
 * The interface Sudoku solver.
 */
public interface SudokuSolver {

    /**
     * Solves the sudoku.
     *
     * @param sb the SudokuBoard object
     */
    void solve(SudokuBoard sb);
}
