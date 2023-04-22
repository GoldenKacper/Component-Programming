package org.sudoku.board;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Sudoku row.
 */
public class SudokuRow extends SudokuElement {
    private final int rowNumber;

    /**
     * Instantiates a new Sudoku row.
     *
     * @param rowNumber the row number
     */
    public SudokuRow(int rowNumber) {
        this.rowNumber = rowNumber;
    }


    /**
     * Gets row number.
     *
     * @return the row number
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * To string method, it converts objects into string.
     *
     * @return converted object into string
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append(super.toString())
                .append(" row_number", rowNumber).toString();
    }

    /**
     * Hash code method, calculate the unique integer for objects.
     *
     * @return calculated unique integer
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37)
                .append(super.hashCode()).append(rowNumber).toHashCode();
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
        if (obj instanceof SudokuRow) {
            return new EqualsBuilder()
                    .append(getSudokuFields(), ((SudokuRow) obj).getSudokuFields())
                    .append(rowNumber, ((SudokuRow) obj).rowNumber).isEquals();
        }
        return false;
    }

    @Override
    public SudokuRow clone() throws CloneNotSupportedException {
        return (SudokuRow) super.clone();
    }
}
