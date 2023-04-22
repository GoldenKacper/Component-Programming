package org.sudoku.dao;

import org.sudoku.exceptions.DaoException;

/**
 * The interface Dao.
 *
 * @param <T> the type parameter, for example 'SudokuBoard'
 */
public interface Dao<T> extends AutoCloseable {
    //    /**
    //     * Read t object with the file and load it into class object.
    //     *
    //     * @return the t object
    //     * @throws DaoException the exception
    //     */

    T read() throws DaoException;

    //    /**
    //     * Write object into file.
    //     *
    //     * @param obj the object
    //     */
    void write(T obj);
}
