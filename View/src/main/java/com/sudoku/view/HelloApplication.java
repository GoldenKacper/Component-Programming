package com.sudoku.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.sudoku.board.SudokuBoard;

import java.io.IOException;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    HBox languageVersions = new HBox();
    private int buttonSize = 100;
    private int buttonSize1 = 150;
    public static ResourceBundle languageVersion;
    public static ResourceBundle languageVersion1;
    public static String language;
    @Override
    public void start(Stage stage) throws IOException {
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, 600, 400);
        languageVersions.setSpacing(buttonSize);
        renderLanguageVersionButton("pl", "-fx-background-image: url(polska.png); -fx-background-size: 160%; -fx-background-position: 25%");
        renderLanguageVersionButton("en", "-fx-background-image: url(United_Kingdom.png); -fx-background-size: 160%; -fx-background-position: center;");
        stackPane.getChildren().add(languageVersions);
        StackPane.setAlignment(languageVersions, Pos.CENTER);
        stage.setTitle("SudokuGameProject");
        stage.setScene(scene);
        stage.show();
    }

    private void renderLanguageVersionButton(String language, String style){
        var button = new Button();
        HelloApplication.language = language;
        button.setStyle(style);
        button.setMinSize(buttonSize1,buttonSize);
        button.setOnAction(e->{
            languageVersion = ResourceBundle.getBundle("MyResources_"+language);
            languageVersion1 = ResourceBundle.getBundle("com.sudoku.view.StatsBundle_"+language);
            Window(e);
            //SudokuBoard.setLen(language);
        });
        languageVersions.getChildren().add(button);
        languageVersions.setAlignment(Pos.CENTER);
    }

    public void Window(ActionEvent a)  {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"), languageVersion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}