package org.sudoku.exceptions;

public class SudokuFieldException extends SudokuBoardException {
    public SudokuFieldException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public SudokuFieldException(String errorMessage) {
        super(errorMessage);
    }
}
