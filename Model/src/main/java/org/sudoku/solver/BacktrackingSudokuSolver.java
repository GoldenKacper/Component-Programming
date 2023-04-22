package org.sudoku.solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.sudoku.board.SudokuBoard;

/**
 * The type Backtracking sudoku solver.
 */
public class BacktrackingSudokuSolver implements SudokuSolver {

    /**
     * Solves the sudoku.
     *
     * @param sb the SudokuBoard object
     */
    @Override
    public void solve(SudokuBoard sb) {
        shuffleFirstRow(sb);
        boolean isPossible;
        for (int row = 1; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                isPossible = false;
                for (int k = sb.getBoard(row, column) + 1; k <= 9; k++) {
                    if (canBeFilled(sb, row, column, k)) {
                        isPossible = true;
                        sb.setBoard(row, column, k);
                        break;
                    }
                }
                if (!isPossible) {
                    sb.setBoard(row, column, 0);
                    if (column == 0) {
                        row--;
                        column = 7;
                    } else {
                        column -= 2;
                    }
                }
            }
        }
    }

    private void shuffleFirstRow(SudokuBoard sb) { //generuje pierszy wiersz tablicy
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(list);
        var array = list.toArray();
        for (int j = 0; j < 9; j++) {
            sb.setBoard(0, j, (int) array[j]);
        }
    }


    //elementy w jednej kolumnie nie moga sie powtarzac
    private boolean columnRule(SudokuBoard sb, int column, int value) {
        for (int i = 0; i < 9; i++) {
            if (sb.getBoard(i, column) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean rowRule(SudokuBoard sb, int row, int value) {  //elementy w jednym
        // wierszu nie moga sie powtarzac
        for (int i = 0; i < 9; i++) {
            if (sb.getBoard(row, i) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean boxRule(SudokuBoard sb, int row, int column, int value) {  //elementy w jednym
        // polu 3*3 nie moga sie powtarzac
        int r = row - row % 3;
        int c = column - column % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sb.getBoard(i + r, j + c) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    //sprawdzenie czy dane miejsce w tabeli moze byc uzupelnione
    private boolean canBeFilled(SudokuBoard sb, int row, int column, int value) {
        return rowRule(sb, row, value) && columnRule(sb, column, value)
                && boxRule(sb, row, column, value);
    }
}
