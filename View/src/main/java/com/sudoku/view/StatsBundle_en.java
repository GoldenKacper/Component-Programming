package com.sudoku.view;

import java.util.ListResourceBundle;

public class StatsBundle_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = new Object[3][3];

        contents[0][0] = "Author 1";
        contents[0][1] = "Kuba Kowalik";

        contents[1][0] = "Author 2";
        contents[1][1] = "Kacper Jagodzinski";

        contents[2][0] = "Authors";
        contents[2][1] = "Authors:";

        return contents;
    }
}
