package org.sudoku.exceptions;

public class FormatDaoException extends DaoException {
    public FormatDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public FormatDaoException(String errorMessage) {
        super(errorMessage);
    }
}
