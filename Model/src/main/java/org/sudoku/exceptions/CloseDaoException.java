package org.sudoku.exceptions;

public class CloseDaoException extends DaoException {
    public CloseDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CloseDaoException(String errorMessage) {
        super(errorMessage);
    }
}
