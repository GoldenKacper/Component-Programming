package org.sudoku.exceptions;

public class JdbcConnectionException extends JdbcException {
    public JdbcConnectionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JdbcConnectionException(String errorMessage) {
        super(errorMessage);
    }
}
