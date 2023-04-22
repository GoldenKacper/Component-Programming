package org.sudoku.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sudoku.exceptions.DaoException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    private SudokuBoardDaoFactory factory;

    @BeforeEach
    void setUp() {
        factory = new SudokuBoardDaoFactory();
    }

    @Test
    void getFileDao() throws DaoException {
        assertNotNull(factory.getFileDao("test"));
    }
}