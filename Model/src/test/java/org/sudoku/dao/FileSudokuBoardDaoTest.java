package org.sudoku.dao;

import org.sudoku.board.SudokuBoard;
import org.sudoku.board.SudokuField;
import org.sudoku.exceptions.*;
import org.sudoku.solver.BacktrackingSudokuSolver;
import org.sudoku.solver.SudokuSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    private SudokuBoardDaoFactory factory;
    private SudokuBoard sudokuBoard;
    private SudokuBoard sudokuBoard2;

    @BeforeEach
    void setUp() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        factory = new SudokuBoardDaoFactory();
        sudokuBoard = new SudokuBoard(solver);
        sudokuBoard2 = new SudokuBoard(solver);
    }

    @Test
    void writeReadTest() {
        sudokuBoard.solveGame();
        try (Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("test_1")) {
            fileSudokuBoardDao.write(sudokuBoard);
            File file = new File("test_1.txt");
            file.delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new WriteDaoException(SudokuBoard.getLanguageVersion().getString("writeErrKey") ,e);

        }
        try (Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("test_1")) {
            sudokuBoard2 = fileSudokuBoardDao.read();
            File file = new File("test_1.txt");
            file.delete();
        } catch (Exception e) {
            throw new ReadDaoException(SudokuBoard.getLanguageVersion().getString("loadErrKey"), e);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudokuBoard.getBoard(i, j), sudokuBoard2.getBoard(i, j));
            }
        }

        sudokuBoard2.setBoard(1, 0, 0);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.getBoard(i, j) != sudokuBoard2.getBoard(i, j)){
                    assertNotEquals(sudokuBoard.getBoard(i, j), sudokuBoard2.getBoard(i, j));
                } else {
                    assertEquals(sudokuBoard.getBoard(i, j), sudokuBoard2.getBoard(i, j));
                }
            }
        }
    }

    @Test
    void readIOExceptionTest() {
        sudokuBoard.solveGame();
        assertThrows(ReadDaoException.class, () -> {
            Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("test_2");
            sudokuBoard2 = fileSudokuBoardDao.read();
        });
    }

    @Test
    void writeIOExceptionTest() {
        sudokuBoard.solveGame();
        assertThrows(WriteDaoException.class, () -> {
            Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("?");
            fileSudokuBoardDao.write(sudokuBoard);
        });
    }

    @Test
    void numberBadFormatTest() {
        sudokuBoard.solveGame();
        assertThrows(FormatDaoException.class, () -> {
            Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("test_3");
            fileSudokuBoardDao.write(sudokuBoard);
            PrintWriter write = new PrintWriter("test_3.txt");
            write.println("kot");
            write.close();
            sudokuBoard2 = fileSudokuBoardDao.read();

            File file = new File("test_3.txt");
        });

//        assertThrows(FormatDaoException.class, () -> {
//            File file = new File("test_3.txt");
//        });
    }

    @Test
    void boardsEqual() throws Exception{
        sudokuBoard.solveGame();
        sudokuBoard.getSudokuField(1,1).shouldBeEditable(true);
        try(var dao = new FileSudokuBoardDao("some_file")){
            dao.write(sudokuBoard);
        }
        SudokuBoard board;
        try(var dao = new FileSudokuBoardDao("some_file")){
            board = dao.read();
        }
        try(var dao = new FileSudokuBoardDao("some_file2")){
            dao.write(board);
        }
        assertNotEquals(board, null);
        assertTrue(board.getSudokuField(1,1).isEditable());
    }
}