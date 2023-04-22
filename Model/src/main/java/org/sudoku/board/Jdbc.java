package org.sudoku.board;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudoku.exceptions.JdbcConnectionException;
import org.sudoku.exceptions.JdbcCreateSchemaException;
import org.sudoku.exceptions.JdbcQueryException;
import org.sudoku.solver.BacktrackingSudokuSolver;

public class Jdbc {
    private String url;
    private Connection conn = null;
    private DatabaseMetaData meta = null;

    private transient Logger logger = LogManager.getLogger(getClass());

    public Jdbc(Boolean inMemory) throws JdbcConnectionException, JdbcCreateSchemaException {
        connect(inMemory);
        if (conn != null) {
            createSchema();
        }
    }

    public Jdbc() throws JdbcConnectionException, JdbcCreateSchemaException {
        this(false);
    }

    public void closeConnection() throws SQLException {
        conn.rollback();
        conn.close();
    }

    public boolean tableNotExists(String tableName) throws JdbcConnectionException {
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});) {
            return !resultSet.next();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard.getLanguageVersion()
                    .getString("connectionErr"), e);
        }
    }

    public Map<Integer, String> getSudokuBoardsList()
            throws JdbcConnectionException, JdbcQueryException {
        Map<Integer, String> list = new HashMap<>();

        try (PreparedStatement statement =
                     conn.prepareStatement("SELECT * FROM BOARDS")) {
            try (ResultSet resultSet = statement.executeQuery();) {
                int id;
                String name;
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                    name = resultSet.getString(2);
                    list.put(id, name);
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new JdbcQueryException(SudokuBoard.getLanguageVersion()
                        .getString("queryErr"), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard.getLanguageVersion()
                    .getString("connectionErr"), e);
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard.getLanguageVersion()
                    .getString("connectionErr"), e);
        }

        return list;
    }

    public void writeSudokuBoard(SudokuBoard board, String boardName)
            throws JdbcConnectionException, JdbcQueryException {
        int boardID;
        int affectedRows;

        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO BOARDS(NAME) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, boardName);
            affectedRows = preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    boardID = generatedKeys.getInt(1);
                } else {
                    throw new JdbcQueryException(SudokuBoard
                            .getLanguageVersion().getString("queryErr"));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new JdbcConnectionException(SudokuBoard
                        .getLanguageVersion().getString("connectionErr"), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }
        if (affectedRows == 0) {
            throw new JdbcQueryException(SudokuBoard.getLanguageVersion().getString("queryErr"));
        }

        try (PreparedStatement statement =
                     conn.prepareStatement("INSERT INTO FIELDS(BOARD, ROW, COL, VALUE) "
                             + "VALUES (?, ?, ?, ?)");) {
            statement.setInt(1, boardID);

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    statement.setInt(2, row);
                    statement.setInt(3, col);
                    statement.setInt(4, board
                            .getBoard(row, col));
                    statement.addBatch();
                }
            }
            int[] numUpdates = statement.executeBatch();
            for (int numUpdate : numUpdates) {
                if (numUpdate < 0) {
                    throw new JdbcQueryException(SudokuBoard
                            .getLanguageVersion().getString("queryErr"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }
    }

    public SudokuBoard readSudokuBoard(String boardName)
            throws JdbcConnectionException, JdbcQueryException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        int boardID;

        try (PreparedStatement statement =
                     conn.prepareStatement("SELECT ID FROM BOARDS WHERE NAME=?")) {
            statement.setString(1, boardName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    boardID = resultSet.getInt(1);
                } else {
                    throw new JdbcQueryException(SudokuBoard
                            .getLanguageVersion().getString("queryErr"));
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new JdbcConnectionException(SudokuBoard
                        .getLanguageVersion().getString("connectionErr"), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }

        try (PreparedStatement statement =
                     conn.prepareStatement("SELECT ROW, COL, VALUE FROM FIELDS WHERE BOARD=?")) {
            statement.setInt(1, boardID);
            try (ResultSet resultSet = statement.executeQuery()) {
                int row;
                int col;
                int value;
                while (resultSet.next()) {
                    row = resultSet.getInt(1);
                    col = resultSet.getInt(2);
                    value = resultSet.getInt(3);
                    board.setBoard(row, col, value);
                }
                conn.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new JdbcQueryException(SudokuBoard
                        .getLanguageVersion().getString("queryErr"), e);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }

        return board;
    }

    public void connect(Boolean inMemory) throws JdbcConnectionException {
        String userDir = System.getProperty("user.dir").replace("\\", "\\\\");
        if (inMemory) {
            url = "jdbc:derby:memory:" + userDir + "\\Database\\SudokuGame" + ";create=true";
        } else {
            url = "jdbc:derby:" + userDir + "\\Database\\SudokuGame" + ";create=true";
        }
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            meta = conn.getMetaData();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }
    }

    private void createSchema() throws JdbcConnectionException, JdbcCreateSchemaException {
        Statement stmt;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }

        if (tableNotExists("BOARDS")) {
            try {
                stmt.executeUpdate(
                        "Create table BOARDS (ID int not null primary key GENERATED ALWAYS "
                                + "AS IDENTITY (START WITH 1, INCREMENT BY 1), NAME varchar(30)"
                );
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new JdbcCreateSchemaException(SudokuBoard
                        .getLanguageVersion().getString("schemaErr"), e);
            }
        }

        if (tableNotExists("FIELDS")) {
            try {
                stmt.executeUpdate(
                        "Create table FIELDS (ID int not null primary key GENERATED ALWAYS AS"
                                + " IDENTITY (START " + "WITH 1, INCREMENT BY 1), "
                                + "BOARD int references BOARDS(ID), ROW int, COL int, VALUE int)"
                );
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new JdbcCreateSchemaException(SudokuBoard
                        .getLanguageVersion().getString("schemaErr"), e);
            }
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new JdbcConnectionException(SudokuBoard
                    .getLanguageVersion().getString("connectionErr"), e);
        }
    }
}
