package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage window;
    Scene logowanie;
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("QuickPass");
        window.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        logowanie = Logowanie.showLogowanie(window);
        window.setScene(logowanie);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
