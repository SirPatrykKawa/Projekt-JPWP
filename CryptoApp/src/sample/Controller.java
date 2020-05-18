package sample;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.IOException;

public class Controller {



    static boolean answer;
    static boolean logIn;
    static double xOffset = 0;
    static double yOffset = 0;

    public static Button exitButton(Stage window){
        Button exit = new Button("Exit");
        exit.setMinSize(180, 25);
        exit.setOnAction(e ->{
            boolean toExit = checkExit("Exit", "Are you sure?");
            if (toExit){
                alertWindow("Exit", "See you soon!");
                window.close();
            }
        });
        return exit;
    }

    public static boolean checkExit(String title, String message){

        Button yes = new Button("YES");
        Button no = new Button("NO");
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(240);
        window.setHeight(85);
        window.setMinHeight(85);
        window.setMinWidth(200);
        window.setResizable(false);
        Label label = new Label();
        label.setText(message);
        label.setMinSize(200, 20);
        label.setFont(new Font("Verdena", 14));
        label.setTextFill(Color.BLACK);
        label.setAlignment(Pos.CENTER);
        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });
        no.setOnAction(e ->{
            answer = false;
            window.close();
        });
        BorderPane pane = new BorderPane();
        HBox box = new HBox(10);
        box.getChildren().addAll(yes, no);
        box.setAlignment(Pos.CENTER);
        pane.setTop(label);
        pane.setCenter(box);
        pane.setPadding(new Insets(20, 20, 20, 20));
        alertBackground(pane);
        movePanel(pane, window);
        Scene scene = new Scene(pane);
        window.setScene(scene);
        window.showAndWait();

        return answer;

    }

    public static boolean logIn(String username, String password){
        if ((username.equals("PatrykKawa")) &&  (password.equals("QWERTY"))){
            logIn = true;
        }
        else{
            logIn = false;
        }

        return logIn;
    }

    public static void alertWindow(String title, String message){
        Stage window = new Stage();

        window.initStyle(StageStyle.UNDECORATED);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(240);
        window.setHeight(85);
        window.setMinHeight(85);
        window.setMinWidth(200);
        window.setResizable(false);

        Label label = new Label();
        label.setText(message);
        label.setFont(new Font("Verdena", 14));
        label.setTextFill(Color.AQUAMARINE);
        BorderPane pane = new BorderPane();
        pane.setCenter(label);
        pane.setPadding(new Insets(20, 20, 20, 20));
        alertBackground(pane);
        Scene scene = new Scene(pane);
        movePanel(pane, window);
        window.setScene(scene);
        window.setTitle(title);
        window.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> window.close());
        delay.play();
    }

    public static void setBackground(Pane pane){
        pane.setStyle("-fx-background-color: #555555;");
    }

    public static void alertBackground(Pane pane){
        try{
            BackgroundImage background = new BackgroundImage(new Image(new FileInputStream("C:\\Users\\kawap\\Desktop\\Programy\\background2.png")),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            pane.setBackground(new Background(background));
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static boolean isNew(String user, String password, String validate){
        boolean mozna = true;

        return mozna;
    }

    public static Label authors(){
        Label authors = new Label();
        authors.setMinSize(75, 12);
        authors.setText("Patryk Kawa Adam Twardosz");
        authors.setFont(new Font("Arial", 12));
        authors.setTextFill(Color.GREENYELLOW);
        return authors;
    }

    public static boolean isAccount(String userOrEmail, String secret){
        boolean exist = true;
        return exist;
    }

    public static boolean doMatch(String password, String verification){
        boolean doMatch = true;
        return doMatch;
    }
    public static void movePanel(Pane pane, Stage window){
        pane.setOnMousePressed(e ->{
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        pane.setOnMouseDragged(e ->{
            window.setX(e.getScreenX() - xOffset);
            window.setY(e.getScreenY() - yOffset);
        });
    }

    public static boolean passwordRegex(String password){
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&+=])(?=\\S+$).{8,}$";
        boolean fit = password.matches(pattern);
        return fit;
    }
}
