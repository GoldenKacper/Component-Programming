package org.sudoku.exceptions;

public class CloneSudokuBoardException extends SudokuBoardException {
    public CloneSudokuBoardException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public CloneSudokuBoardException(String errorMessage) {
        super(errorMessage);
    }
}
