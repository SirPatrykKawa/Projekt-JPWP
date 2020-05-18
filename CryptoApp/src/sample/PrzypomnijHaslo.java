package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PrzypomnijHaslo {

    public static Scene showPrzypomnijHaslo(Stage window){

        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        ustawienie.setVgap(6);
        ustawienie.setHgap(6);

        Button exit = Controller.exitButton(window);
        GridPane.setConstraints(exit, 14, 20);

        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        Label info = new Label("Renew your password");
        info.setMinSize(75, 20);
        info.setFont(new Font("Verdana", 16));
        info.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(info, 1, 1, 1, 1);

        ChoiceBox<String> emailOrusernameChoice = new ChoiceBox<>();
        emailOrusernameChoice.getItems().add("Username");
        emailOrusernameChoice.getItems().add("Email");
        emailOrusernameChoice.setValue("Username");
        emailOrusernameChoice.setMinSize(225, 25);
        GridPane.setConstraints(emailOrusernameChoice, 1, 4);
        TextField emailOrusernameData = new TextField();
        emailOrusernameData.setPromptText(emailOrusernameChoice.getValue());
        emailOrusernameChoice.setOnAction(e -> emailOrusernameData.setPromptText(emailOrusernameChoice.getValue()));
        emailOrusernameData.setMinSize(100, 12);
        GridPane.setConstraints(emailOrusernameData, 2, 4);
        Label secretQuestionLabel = new Label("Secret question: Childhood hobby");
        secretQuestionLabel.setTextFill(Color.AQUAMARINE);
        secretQuestionLabel.setMinSize(200, 25);
        secretQuestionLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(secretQuestionLabel, 1, 6);
        TextField secretQuestionData = new TextField();
        secretQuestionData.setPromptText("Childhood hobby");
        secretQuestionData.setMinSize(100, 25);
        GridPane.setConstraints(secretQuestionData, 2, 6);
        Button check = new Button("Check");
        check.setMinSize(75, 25);
        GridPane.setConstraints(check, 1, 7);
        Label error = new Label("Error occured");
        GridPane.setConstraints(error, 2, 7);
        error.setMinSize(75, 12);
        error.setFont(new Font("Verdana", 12));
        error.setTextFill(Color.RED);
        error.setVisible(false);
        Label passwordLabel = new Label("New password:");
        passwordLabel.setVisible(false);
        passwordLabel.setTextFill(Color.AQUAMARINE);
        passwordLabel.setMinSize(75, 25);
        passwordLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(passwordLabel, 1, 10);
        PasswordField passwordData = new PasswordField();
        passwordData.setVisible(false);
        passwordData.setPromptText("Password");
        passwordData.setMinSize(100, 25);
        GridPane.setConstraints(passwordData, 2, 10);
        Label passwordLabelVerify = new Label("Verify password:");
        passwordLabelVerify.setTextFill(Color.AQUAMARINE);
        passwordLabelVerify.setVisible(false);
        passwordLabelVerify.setMinSize(75, 25);
        passwordLabelVerify.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(passwordLabelVerify, 1, 11);
        PasswordField passwordDataVerify = new PasswordField();
        passwordDataVerify.setVisible(false);
        passwordDataVerify.setPromptText("Password");
        passwordDataVerify.setMinSize(100, 25);
        GridPane.setConstraints(passwordDataVerify, 2, 11);
        Button change = new Button("Change");
        change.setVisible(false);
        change.setMinSize(75, 25);
        GridPane.setConstraints(change, 1, 12);
        Label passwordError = new Label("Error occured");
        passwordError.setMinSize(75, 25);
        passwordError.setFont(new Font("Verdana", 12));
        passwordError.setTextFill(Color.RED);
        passwordError.setVisible(false);
        GridPane.setConstraints(passwordError, 2, 12);


        TextArea area = new TextArea();
        GridPane.setConstraints(area, 7, 4, 8, 15);
        area.setWrapText(true);
        area.setDisable(true);
        area.setText("Tu będą tipsy jak stworzyć bezpieczne hasło do twojego konta, można też, " +
                "zadać pare zasad jakie musi spelnic haslo");


        Label authors = Controller.authors();
        GridPane.setConstraints(authors, 1, 20, 15, 1);


        check.setOnAction(e ->{
            boolean isAccount = Controller.isAccount(emailOrusernameData.getText(), secretQuestionData.getText());
            if (isAccount){
                error.setVisible(false);
                passwordData.setVisible(true);
                passwordDataVerify.setVisible(true);
                passwordLabel.setVisible(true);
                passwordLabelVerify.setVisible(true);
                change.setVisible(true);
            }
            else{
                error.setVisible(true);
            }
        });

        change.setOnAction(e ->{
            boolean match = Controller.doMatch(passwordData.getText(), passwordDataVerify.getText());
            if(match){
                Controller.alertWindow("New Password", "Password changed");
                window.setScene(Logowanie.showLogowanie(window));
            }
            else{
                passwordError.setVisible(true);
            }
        });

        Label fact = new Label("Did you know...");
        fact.setMinSize(75, 20);
        fact.setFont(new Font("Verdana", 16));
        fact.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(fact, 7, 1);

        ustawienie.getChildren().addAll(exit, info, emailOrusernameChoice, emailOrusernameData, authors,
                secretQuestionLabel, secretQuestionData, check, error, passwordData, passwordLabel,
                passwordDataVerify, passwordLabelVerify, change, area, fact, passwordError);
        Scene scene = new Scene(ustawienie, 800, 340);

        return scene;

    }
}
