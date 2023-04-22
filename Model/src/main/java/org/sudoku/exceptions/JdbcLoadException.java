package org.sudoku.exceptions;

public class JdbcLoadException extends JdbcException {
    public JdbcLoadException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JdbcLoadException(String errorMessage) {
        super(errorMessage);
    }
}
