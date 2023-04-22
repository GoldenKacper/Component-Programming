package org.sudoku.exceptions;

public class NullPointerSudokuFieldException extends SudokuFieldException {
    public NullPointerSudokuFieldException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public NullPointerSudokuFieldException(String errorMessage) {
        super(errorMessage);
    }
}
