package org.sudoku.exceptions;

public class DaoException extends SudokuBoardException {
    public DaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public DaoException(String errorMessage) {
        super(errorMessage);
    }
}
