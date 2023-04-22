package org.sudoku.exceptions;

public class BadSudokuFieldValueException extends SudokuFieldException {
    public BadSudokuFieldValueException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public BadSudokuFieldValueException(String errorMessage) {
        super(errorMessage);
    }
}
