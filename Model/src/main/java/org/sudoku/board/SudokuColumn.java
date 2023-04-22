package org.sudoku.board;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Sudoku column.
 */
public class SudokuColumn extends SudokuElement {
    private final int columnNumber;

    /**
     * Instantiates a new Sudoku column.
     *
     * @param columnNumber the column number
     */
    public SudokuColumn(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * Gets column number.
     *
     * @return the column number
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * To string method, it converts objects into string.
     *
     * @return converted object into string
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append(super.toString())
                .append(" column_number", columnNumber).toString();
    }

    /**
     * Hash code method, calculate the unique integer for objects.
     *
     * @return calculated unique integer
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37)
                .append(super.hashCode()).append(columnNumber).toHashCode();
    }

    /**
     * Equals method, compare two object and return if that they are the same or not.
     *
     * @param obj the object which will be comparing
     * @return the boolean value of this comparison
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SudokuColumn) {
            return new EqualsBuilder()
                    .append(getSudokuFields(), ((SudokuColumn) obj).getSudokuFields())
                    .append(columnNumber, ((SudokuColumn) obj).columnNumber).isEquals();
        }
        return false;
    }

    @Override
    public SudokuColumn clone() throws CloneNotSupportedException {
        return (SudokuColumn) super.clone();
    }
}
