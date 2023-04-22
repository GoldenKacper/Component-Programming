package org.sudoku.board;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sudoku.exceptions.NullPointerSudokuFieldException;

/**
 * The type Sudoku field.
 */

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;

    private boolean shouldBeEditable = false;

    /**
     * Instantiates a new empty Sudoku field.
     */
    public SudokuField() {

    }

    /**
     * Instantiates a new Sudoku field with value.
     *
     * @param value the value
     */
    public SudokuField(int value) {
        this.setFieldValue(value);
    }

    public boolean isEditable() {
        return shouldBeEditable;
    }

    public void shouldBeEditable(boolean shouldBeEditable) {
        this.shouldBeEditable = shouldBeEditable;
    }

    /**
     * Gets field value.
     *
     * @return the field value
     */
    public final int getFieldValue() {
        return value;
    }

    /**
     * Sets field value.
     *
     * @param value the value which will be set
     */
    public final void setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
        }
    }


    /**
     * To string method, it converts objects into string.
     *
     * @return converted object into string
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value: ", value).toString();
    }


    /**
     * Hash code method, calculate the unique integer for objects.
     *
     * @return calculated unique integer
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37).append(value).toHashCode();
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
        if (obj instanceof SudokuField) {
            return new EqualsBuilder().append(value, ((SudokuField) obj).value).isEquals();
        }
        return false;
    }

    @Override
    public int compareTo(SudokuField obj) {
        if (obj == null) {
            throw new NullPointerSudokuFieldException(SudokuBoard
                    .getLanguageVersion().getString("nullObjectKey"));
        }
        return Integer.compare(this.getFieldValue(), obj.getFieldValue());
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
