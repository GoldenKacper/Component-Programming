package org.sudoku.exceptions;

public class JdbcQueryException extends JdbcException {
    public JdbcQueryException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JdbcQueryException(String errorMessage) {
        super(errorMessage);
    }
}
