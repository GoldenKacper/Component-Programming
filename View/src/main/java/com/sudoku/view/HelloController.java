package com.sudoku.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.sudoku.board.*;
import org.sudoku.exceptions.JdbcLoadException;
import org.sudoku.exceptions.JdbcWriteException;
import org.sudoku.levels.*;
import org.sudoku.dao.*;
import org.sudoku.solver.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class HelloController {

    public void beginer(ActionEvent a) throws CloneNotSupportedException {
//        sb = new SudokuBoard(solver);
//        sb1 = new SudokuBoard(solver);
//        sb.solveGame();
//        sb1 = sb.clone();
//        EnumUsage level = new EnumUsage(EnumerationType.beginer);
//        level.sudokuFieldsRemover(sb1);
          difficultyLevel(EnumerationType.beginer);
    }
    public void medium(ActionEvent a) throws CloneNotSupportedException {
//        sb = new SudokuBoard(solver);
//        sb1 = new SudokuBoard(solver);
//        sb.solveGame();
//        sb1 = sb.clone();
//        EnumUsage level = new EnumUsage(EnumerationType.medium);
//        level.sudokuFieldsRemover(sb1);
        difficultyLevel(EnumerationType.medium);
    }
    public void hard(ActionEvent a) throws CloneNotSupportedException{
//        sb = new SudokuBoard(solver);
//        sb1 = new SudokuBoard(solver);
//        sb.solveGame();
//        sb1 = sb.clone();
//        EnumUsage level = new EnumUsage(EnumerationType.hard);
//        level.sudokuFieldsRemover(sb1);
        difficultyLevel(EnumerationType.hard);
    }

    @FXML
    public void difficultyLevel(EnumerationType l) throws CloneNotSupportedException{
        sb = new SudokuBoard(solver);
        sb1 = new SudokuBoard(solver);
        sb.solveGame();
        sb1 = sb.clone();
        EnumUsage level = new EnumUsage(l);
        level.sudokuFieldsRemover(sb1);
    }

    @FXML
    private SudokuSolver solver = new BacktrackingSudokuSolver();

    private GridPane grid = new GridPane();
    private TextField [][] textFields = new TextField[9][9];
    private static SudokuBoard sb;
    private static SudokuBoard sb1;
    private ResourceBundle mybundle = HelloApplication.languageVersion;
    private ResourceBundle authors = HelloApplication.languageVersion1;
    @FXML
    protected void newWindow() throws IOException {
        for (int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                textFields[i][j] = null;
            }
        }
        Button button = new Button(mybundle.getString("sprawdzKlucz"));
        Button button1 = new Button(mybundle.getString("zapiszKlucz"));
        Button button2 = new Button(mybundle.getString("wrocKlucz"));
        Button button3 = new Button(mybundle.getString("zapiszDoBazyKlucz"));
        button.setPrefHeight(70);
        button.setPrefWidth(70);
        button1.setPrefHeight(70);
        button1.setPrefWidth(70);
        button3.setPrefHeight(70);
        button3.setPrefWidth(70);
        Stage stage = new Stage();
        Scene scene = new Scene(grid, 624, 700);
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                TextField textField;
                if (sb1.getSudokuField(i, j).isEditable()) {
                    if (sb1.getBoard(i, j) == 0) {
                        textField = new TextField();
                    } else {
                        textField = new TextField(Integer.toString(sb1.getBoard(i, j)));
                    }
                    textField.setStyle("-fx-text-fill: black ; -fx-background-color: #ADD8E6; -fx-border-color: black; -fx-opacity: 0.8; -fx-alignment: center;");
                    numbers(textField);
                } else {
                    textField = new TextField(Integer.toString(sb1.getBoard(i, j)));
                    textField.setStyle("-fx-text-fill: red ; -fx-background-color: #DCDCDC; -fx-border-color: black;  -fx-opacity: 0.8; -fx-alignment: center;");
                }
                textField.setFont(Font.font("Verdana", 22));
                textField.setPrefHeight(70);
                textField.setPrefWidth(70);
                grid.add(textField, j, i);
                textFields[i][j] = textField;
                textField.setEditable(sb1.getSudokuField(i, j).isEditable());
            }
        }
        grid.add(button, 3, 10);
        grid.add(button1, 4, 10);
        grid.add(button2, 0, 10);
        grid.add(button3, 5, 10);
        button.setOnAction(this::check);
        button1.setOnAction(this::write);
        button2.setOnAction(e -> {
            stage.close();
        });
        button3.setOnAction(this::saveToDatabase);
        stage.setTitle("SudokuGameProject");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void afterSolve() {
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                int val;
                if(textFields[i][j].getText() != "") {
                    val = Integer.parseInt(textFields[i][j].getText());
                    sb1.setBoard(i, j, val);
                }
            }
        }
    }
    @FXML
    public void check(ActionEvent a) {
        afterSolve();
        if(sb.equals(sb1)) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea(mybundle.getString("dobrzeKlucz"));
            Scene scene = new Scene(textArea, 300, 100);
            stage.setScene(scene);
            stage.show();
        } else {
            Stage stage = new Stage();
            TextArea textArea = new TextArea(mybundle.getString("zleKlucz"));
            Scene scene = new Scene(textArea, 300, 100);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void write(ActionEvent a) {
        afterSolve();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try (Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("sudoku")) {
            fileSudokuBoardDao.write(sb1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void read(ActionEvent a) {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try (Dao<SudokuBoard> fileSudokuBoardDao = factory.getFileDao("sudoku")) {
            sb1 = fileSudokuBoardDao.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void getAuthors() {
        TextArea textArea = new TextArea(authors.getString("Authors"));
        textArea.appendText("\n");
        textArea.appendText(authors.getString("Author 1"));
        textArea.appendText("\n");
        textArea.appendText(authors.getString("Author 2"));
        textArea.setWrapText(true);
        Stage stage = new Stage();
        //stage.setTitle(authors.getString("Authors"));
        Scene scene = new Scene(textArea, 100, 105);
        stage.setScene(scene);
        stage.show();
    }

    public static void numbers(final TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String value, String value1) {
                if(!value1.matches("\\d*")) {
                    tf.setText(value1.replaceAll("[^\\d]",""));
                }
                if(tf.getText().length()>1) {
                    String s = tf.getText().substring(0, 1);
                    tf.setText(s);
                }
            }
        });
    }

    public void saveToDatabase(ActionEvent a) {
        afterSolve();
        JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("table");
        jdbc.write(sb1);
    }

    public void readFromDatabase(ActionEvent a) {
        JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("table");
        jdbc.read();
    }

}