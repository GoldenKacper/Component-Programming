module com.sudoku.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.sql;
    opens com.sudoku.view to javafx.fxml;
    exports com.sudoku.view;
}