package org.sudoku.dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sudoku.board.SudokuBoard;
import org.sudoku.exceptions.DaoException;
import org.sudoku.exceptions.ReadDaoException;
import org.sudoku.exceptions.WriteDaoException;
import org.sudoku.solver.BacktrackingSudokuSolver;
import org.sudoku.solver.SudokuSolver;

/**
 * The type File sudoku board dao.
 */
public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;
    private FileReader myReader;
    private FileWriter myWriter;

    private transient Logger logger = LogManager.getLogger(getClass());

    /**
     * Instantiates a new File sudoku board dao.
     *
     * @param fileName the file name
     */
    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() throws DaoException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        try {
            myReader = new FileReader(this.fileName);
            Scanner scanner = new Scanner(myReader);
            String boardInString = scanner.nextLine();
            String editablesInString = scanner.nextLine();
            var delimeter = ",";
            var fields = boardInString.split(delimeter);
            var editables = editablesInString.split(delimeter);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int index = i * 9 + j;
                    var fieldValue = Integer.parseInt(fields[index]);
                    var isEditable = editables[index].equals("1");
                    sudokuBoard.getSudokuField(i, j).shouldBeEditable(isEditable);
                    sudokuBoard.setBoard(i, j, fieldValue);
                }
            }

            logger.info(SudokuBoard.getLanguageVersion().getString("readSusKey"));
            return sudokuBoard;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ReadDaoException(SudokuBoard.getLanguageVersion().getString("loadErrKey"), e);
        }
    }

    @Override
    public void write(SudokuBoard obj) {
        try {
            myWriter = new FileWriter(this.fileName);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    myWriter.write(Integer.toString(obj.getBoard(i, j)));
                    if (i != 8 || j != 8) {
                        myWriter.write(",");
                    }
                }
            }
            myWriter.write("\n");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int flag = 0;
                    if (obj.getSudokuField(i, j).isEditable()) {
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                    myWriter.write(Integer.toString(flag));
                    if (i != 8 || j != 8) {
                        myWriter.write(",");
                    }
                }
            }
            //myWriter.write();
            //myWriter.close();
            logger.info(SudokuBoard.getLanguageVersion().getString("writeSusKey"));
        } catch (IOException e) {
            logger.info(SudokuBoard.getLanguageVersion().getString("errOccurredKey"));
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new WriteDaoException(SudokuBoard
                    .getLanguageVersion().getString("writeErrKey"), e);
        }
    }

    @Override
    public void close() throws Exception {
        if (myWriter != null) {
            myWriter.close();
        }
        if (myReader != null) {
            myReader.close();
        }
        logger.info(SudokuBoard.getLanguageVersion().getString("closeInfoKey"));
    }

}
