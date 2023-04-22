package org.sudoku.exceptions;

import java.sql.SQLException;

public class JdbcException extends SQLException {
    public JdbcException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public JdbcException(String errorMessage) {
        super(errorMessage);
    }
}
