package org.sudoku.exceptions;

public class WriteDaoException extends DaoException {
    public WriteDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public WriteDaoException(String errorMessage) {
        super(errorMessage);
    }
}
