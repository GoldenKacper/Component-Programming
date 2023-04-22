//package org.sudoku.dao;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.sudoku.board.JdbcSudokuBoardDao;
//import org.sudoku.board.SudokuBoard;
//import org.sudoku.dao.Dao;
//import org.sudoku.dao.SudokuBoardDaoFactory;
//import org.sudoku.exceptions.JdbcLoadException;
//import org.sudoku.exceptions.JdbcWriteException;
//import org.sudoku.exceptions.ReadDaoException;
//import org.sudoku.exceptions.WriteDaoException;
//import org.sudoku.solver.BacktrackingSudokuSolver;
//import org.sudoku.solver.SudokuSolver;
//
//import java.io.File;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//public class JdbcTest {
//    private SudokuBoardDaoFactory factory;
//    private SudokuBoard sudokuBoard;
//    private SudokuBoard sudokuBoard2;
//
//    @BeforeEach
//    void setUp() {
//        SudokuSolver solver = new BacktrackingSudokuSolver();
//        factory = new SudokuBoardDaoFactory();
//        sudokuBoard = new SudokuBoard(solver);
//        sudokuBoard2 = new SudokuBoard(solver);
//    }
//
//    @Test
//    void writeReadTest() throws JdbcWriteException, JdbcLoadException {
//        sudokuBoard.solveGame();
//        try (JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("newTable")) {
//            jdbc.write(sudokuBoard);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new JdbcWriteException(SudokuBoard
//                    .getLanguageVersion().getString("writeDbErr"), e);
//
//        }
//        try (JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("newTable")) {
//            sudokuBoard2 = jdbc.read();
//        } catch (Exception e) {
//            throw new JdbcLoadException(SudokuBoard
//                    .getLanguageVersion().getString("loadDbErr"), e);        }
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                assertEquals(sudokuBoard.getBoard(i, j), sudokuBoard2.getBoard(i, j));
//            }
//        }
//
//        sudokuBoard2.setBoard(1, 0, 0);
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                if (sudokuBoard.getBoard(i, j) != sudokuBoard2.getBoard(i, j)){
//                    assertNotEquals(sudokuBoard.getBoard(i, j), sudokuBoard2.getBoard(i, j));
//                } else {
//                    assertEquals(sudokuBoard.getBoard(i, j), sudokuBoard2.getBoard(i, j));
//                }
//            }
//        }
//    }
//}
