package org.sudoku.exceptions;

public class JdbcCreateSchemaException extends JdbcException {
    public JdbcCreateSchemaException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JdbcCreateSchemaException(String errorMessage) {
        super(errorMessage);
    }
}
