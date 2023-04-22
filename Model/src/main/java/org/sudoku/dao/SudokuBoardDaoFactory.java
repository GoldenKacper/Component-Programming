package org.sudoku.dao;

//import org.sudoku.board.JdbcSudokuBoardDao;

import org.sudoku.board.JdbcSudokuBoardDao;
import org.sudoku.board.SudokuBoard;

/**
 * The type Sudoku board dao factory.
 */
public class SudokuBoardDaoFactory {
    /**
     * Gets file dao.
     *
     * @param fileName the file name
     * @return the file dao object, type SudokuBoard
     */
    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

        public static Dao<SudokuBoard> getJdbcDao(final String boardName) {
            return getJdbcDao(boardName, false);
        }

        public static Dao<SudokuBoard> getJdbcDao(final String boardName, Boolean inMemory) {
            return new JdbcSudokuBoardDao(boardName, inMemory);
        }
}
