package org.sudoku.exceptions;

public class JdbcWriteException extends JdbcException {
    public JdbcWriteException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JdbcWriteException(String errorMessage) {
        super(errorMessage);
    }
}
