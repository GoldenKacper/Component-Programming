package org.sudoku.board;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * The type Sudoku element.
 */
public class SudokuElement implements Serializable, Cloneable {
    //private SudokuField[] sudokuFields = new SudokuField[9];
    private List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);

    /**
     * Instantiates a new Sudoku element.
     */
    public SudokuElement() {
        for (int i = 0; i < 9; i++) {
            sudokuFields.set(i, new SudokuField());
        }
    }

    protected List<SudokuField> getSudokuFields() {
        return sudokuFields;
    }

    /**
     * Gets current sudoku field value.
     *
     * @param x the number of field
     * @return the sudoku field's value
     */

    public int getSudokuField(int x) {
        return sudokuFields.get(x).getFieldValue();
    }

    /**
     * Sets current sudoku field.
     *
     * @param x     the number of field
     * @param value the value which will be set
     */
    public void setSudokuField(int x, int value) {
        sudokuFields.get(x).setFieldValue(value);
    }

    /**
     * Verify checks the correction of fields.
     *
     * @return the boolean
     */
    public boolean verify() {
        int[] sup = new int[9];

        for (int i = 0; i < 9; i++) {
            if (sudokuFields.get(i).getFieldValue() != 0) {
                sup[sudokuFields.get(i).getFieldValue() - 1]++;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (sup[i] >= 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * To string method, it converts objects into string.
     *
     * @return converted object into string
     */
    @Override
    public String toString() {
        ToStringBuilder controlString = new ToStringBuilder(this);
        for (int i = 0; i < 9; i++) {
            controlString.append(" (i:" + Integer.toString(i) + ") ", getSudokuField(i));
        }
        return controlString.toString();
    }

    /**
     * Hash code method, calculate the unique integer for objects.
     *
     * @return calculated unique integer
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37).append(sudokuFields).toHashCode();
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
        if (obj instanceof SudokuElement) {
            return new EqualsBuilder()
                    .append(sudokuFields, ((SudokuElement) obj).sudokuFields).isEquals();
        }
        return false;
    }

    @Override
    public SudokuElement clone() throws CloneNotSupportedException {
        SudokuElement clone = (SudokuElement) super.clone();
        clone.sudokuFields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < this.getSudokuFields().size(); i++) {
            clone.sudokuFields.set(i, new SudokuField());
            clone.setSudokuField(i, getSudokuField(i));
        }
        return clone;
    }
}
