package org.sudoku.levels;

import java.util.Random;
import org.sudoku.board.SudokuBoard;

public class EnumUsage {
    EnumerationType level;

    public EnumUsage(EnumerationType level) {
        this.level = level;
    }

    public void sudokuFieldsRemover(SudokuBoard sb) {
        int row;
        int col;
        int size;
        Random rand = new Random();
        switch (level) {
            case beginer:
                size = 20;
                for (int i = 0; i < size; i++) {
                    row = rand.nextInt(9);
                    col = rand.nextInt(9);
                    sb.setBoard(row, col, 0);
                    sb.getSudokuField(row, col).shouldBeEditable(true);
                }
                break;
            case medium:
                size = 45;
                for (int i = 0; i < size; i++) {
                    row = rand.nextInt(9);
                    col = rand.nextInt(9);
                    sb.setBoard(row, col, 0);
                    sb.getSudokuField(row, col).shouldBeEditable(true);
                }
                break;
            case hard:
                size = 75;
                for (int i = 0; i < size; i++) {
                    row = rand.nextInt(9);
                    col = rand.nextInt(9);
                    sb.setBoard(row, col, 0);
                    sb.getSudokuField(row, col).shouldBeEditable(true);
                }
                break;
            default:
                break;
        }
    }
}

