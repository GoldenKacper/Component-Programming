package org.sudoku.exceptions;

public class ReadDaoException extends DaoException {
    public ReadDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ReadDaoException(String errorMessage) {
        super(errorMessage);
    }
}
