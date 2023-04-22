package org.sudoku.exceptions;

public class SudokuBoardException extends SudokuException {
    public SudokuBoardException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public SudokuBoardException(String errorMessage) {
        super(errorMessage);
    }
}
