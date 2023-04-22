package org.sudoku.exceptions;

public class SudokuException extends RuntimeException {
    public SudokuException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public SudokuException(String errorMessage) {
        super(errorMessage);
    }
}
