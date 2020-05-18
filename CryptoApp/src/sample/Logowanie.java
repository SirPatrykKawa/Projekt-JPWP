package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Logowanie {

    public static Scene showLogowanie(Stage window){

        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        ustawienie.setVgap(6);
        ustawienie.setHgap(6);

        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        Button exit = Controller.exitButton(window);
        GridPane.setConstraints(exit, 18, 30);


        Label appName = new Label("QuickPass");
        GridPane.setHalignment(appName, HPos.CENTER);
        appName.setMinSize(150, 40);
        appName.setFont(new Font("Verdana", 18));
        appName.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(appName, 0,0, 20, 10);
        //GridPane.setConstraints(banner, 0, 0, 20, 10);
        //banner.setMinSize(775, 40);
        Label nameLabel = new Label("Username:");
        nameLabel.setMinSize(75, 20);
        nameLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(nameLabel, 11, 13);
        TextField nameInput = new TextField("");
        nameInput.setPromptText("Username");
        nameInput.setMinSize(100, 20);
        GridPane.setConstraints(nameInput, 12, 13);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setMinSize(75, 20);
        passwordLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(passwordLabel, 11, 14);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setMinSize(100, 20);
        GridPane.setConstraints(passwordInput, 12, 14);
        Hyperlink toNewAccount = new Hyperlink("Don't have an account? ");
        toNewAccount.setOnAction(e -> {
            window.setScene(NoweKonto.showNoweKonto(window));
        });
        TextFlow newAccount = new TextFlow(toNewAccount);

        Label localPathLabel = new Label("Local bd path:");
        localPathLabel.setMinSize(75, 20);
        localPathLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(localPathLabel, 11, 15);
        TextField localPathInput = new TextField("");
        localPathInput.setPromptText("Local db path");
        localPathInput.setMinSize(100, 20);
        GridPane.setConstraints(localPathInput, 12, 15);

        Label serverAddressLabel = new Label("Server address:");
        serverAddressLabel.setMinSize(75, 20);
        serverAddressLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(serverAddressLabel, 11, 16);
        TextField serverAddressInput = new TextField("");
        serverAddressInput.setPromptText("Server address");
        serverAddressInput.setMinSize(100, 20);
        GridPane.setConstraints(serverAddressInput, 12, 16);

        Label aesKeyPathLabel = new Label("AES key path:");
        aesKeyPathLabel.setMinSize(75, 20);
        aesKeyPathLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(aesKeyPathLabel, 11, 17);
        TextField aesKeyPathInput = new TextField("");
        aesKeyPathInput.setPromptText("AES key path");
        aesKeyPathInput.setMinSize(100, 20);
        GridPane.setConstraints(aesKeyPathInput, 12, 17);

        GridPane.setConstraints(newAccount, 11, 18);
        newAccount.setMinSize(100, 8);
        Hyperlink toForgot = new Hyperlink("Forgot your password? ");
        toForgot.setOnAction(e -> {
            window.setScene(PrzypomnijHaslo.showPrzypomnijHaslo(window));
        });
        TextFlow forgot = new TextFlow(toForgot);
        GridPane.setConstraints(forgot, 11, 19);
        forgot.setMinSize(100, 8);
        Label authors = new Label();
        GridPane.setConstraints(authors, 1, 30);
        authors.setPrefSize(160, 15);
        authors.setText("Patryk Kawa Adam Twardosz");
        authors.setFont(new Font("Arial", 12));
        authors.setTextFill(Color.GREENYELLOW);
        authors.setMinSize(160, 15);
        //button2.setMinSize(100, 20);


        Button button7 = new Button("Log in");

        button7.setOnAction(e -> {
            boolean logIn = Controller.logIn(nameInput.getText(), passwordInput.getText());
            if (logIn){
                Controller.alertWindow("Logging", "Congrats, you logged in!");
                window.setScene(OknoGlowne.showOknoGlowne(window));
            }
            else{
                Controller.alertWindow("Logging", "Unable to log you in!");
            }
            /*

            try {
                boolean logIn = ApiConnector.logIn(nameInput.getText(), passwordInput.getText());
                if (logIn){
                    boolean isSynchronised = ApiConnector.synchronise();
                    Controller.alertWindow("Logging", "Congrats, you logged in!");
                    window.setScene(OknoGlowne.showOknoGlowne(window));
                }
                else{
                    Controller.alertWindow("Logging", "Unable to log you in!");
                }
            } catch (IOException ex1) {
                ex1.printStackTrace();
            } catch (URISyntaxException ex2) {
                ex2.printStackTrace();
            }

             */
        });
        GridPane.setConstraints(button7, 13,17);
        button7.setMinSize(60, 25);

        ustawienie.getChildren().addAll(button7, nameInput, nameLabel, passwordInput, passwordLabel, authors,
                newAccount, forgot, exit, aesKeyPathInput, aesKeyPathLabel, localPathInput, localPathLabel, appName,
                serverAddressInput, serverAddressLabel);

        Scene logowanie = new Scene(ustawienie, 800, 340);


        return logowanie;
    }
}
