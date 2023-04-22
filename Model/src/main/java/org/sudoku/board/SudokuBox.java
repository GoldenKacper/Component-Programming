package org.sudoku.board;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Sudoku box.
 */
public class SudokuBox extends SudokuElement {
    // x_BoxNumber, y_BoxNumber are first coordinates of current box
    private final int xboxNumber;
    private final int yboxNumber;

    /**
     * Instantiates a new Sudoku box.
     * xboxnumber, yboxnumber are coordinates left top corner of the box
     *
     * @param xboxNumber the x coordinate
     * @param yboxNumber the y coordinate
     */
    public SudokuBox(int xboxNumber, int yboxNumber) {
        this.xboxNumber = xboxNumber - xboxNumber % 3;
        this.yboxNumber = yboxNumber - yboxNumber % 3;
    }

    /**
     * Gets x box number.
     *
     * @return the x box number
     */
    public int getXboxNumber() {
        return xboxNumber;
    }

    /**
     * Gets y box number.
     *
     * @return the y box number
     */
    public int getYboxNumber() {
        return yboxNumber;
    }

    /**
     * To string method, it converts objects into string.
     *
     * @return converted object into string
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append(super.toString())
                .append(" x_box_number", xboxNumber)
                .append(" y_box_number", yboxNumber).toString();
    }

    /**
     * Hash code method, calculate the unique integer for objects.
     *
     * @return calculated unique integer
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37)
                .append(super.hashCode()).append(xboxNumber).append(yboxNumber).toHashCode();
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
        if (obj instanceof SudokuBox) {
            return new EqualsBuilder()
                    .append(getSudokuFields(), ((SudokuBox) obj).getSudokuFields())
                    .append(xboxNumber, ((SudokuBox) obj).xboxNumber)
                    .append(yboxNumber, ((SudokuBox) obj).yboxNumber).isEquals();
        }
        return false;
    }

    @Override
    public SudokuBox clone() throws CloneNotSupportedException {
        return  (SudokuBox) super.clone();
    }
}
