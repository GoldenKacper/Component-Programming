package org.sudoku.board;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.sudoku.dao.Dao;
import org.sudoku.exceptions.JdbcConnectionException;
import org.sudoku.exceptions.JdbcCreateSchemaException;
import org.sudoku.exceptions.JdbcLoadException;
import org.sudoku.exceptions.JdbcQueryException;
import org.sudoku.exceptions.JdbcWriteException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private Jdbc jdbc;
    private final String tableName;

    public JdbcSudokuBoardDao(String tableName) {
        this(tableName, false);
    }

    public JdbcSudokuBoardDao(String tableName, Boolean inMemory) {
        try {
            this.jdbc = new Jdbc(inMemory);
        } catch (JdbcConnectionException | JdbcCreateSchemaException e) {
            e.printStackTrace();
        }
        this.tableName = tableName;
//        try {
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Map<Integer, String> getBoards() {
        try {
            return jdbc.getSudokuBoardsList();
        } catch (JdbcConnectionException | JdbcQueryException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard;
        try {
            jdbc.connect(true);
            sudokuBoard = jdbc.readSudokuBoard(tableName);
        } catch (JdbcConnectionException | JdbcQueryException e) {
            try {
                throw new JdbcLoadException(SudokuBoard
                .getLanguageVersion().getString("loadDbErr"), e);
            } catch (JdbcLoadException ex) {
                throw new RuntimeException(ex);
            }
        }

        return sudokuBoard;
    }

    @Override
    public void write(final SudokuBoard sudokuBoard)  {
        try {
            jdbc.connect(true);
            jdbc.writeSudokuBoard(sudokuBoard, tableName);
        } catch (JdbcConnectionException | JdbcQueryException e) {
            try {
                throw new JdbcWriteException(SudokuBoard
                        .getLanguageVersion().getString("writeDbErr"), e);
            } catch (JdbcWriteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //    @Override
    //    public void finalize() throws IOException {
    //        try {
    //              .
    //        } catch (Exception e) {
    //             .
    //        }
    //    }

    @Override
    public void close() throws SQLException {
        jdbc.closeConnection();
    }
}
