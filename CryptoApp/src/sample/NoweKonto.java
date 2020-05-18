package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;

public class NoweKonto {

    static HashMap<String, String> newAccount = new HashMap<>();

    public static Scene showNoweKonto(Stage window){
        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        ustawienie.setVgap(6);
        ustawienie.setHgap(6);

        Button exit = Controller.exitButton(window);
        GridPane.setConstraints(exit, 14, 20);


        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        Label info = new Label("Create a new account");
        info.setMinSize(75, 20);
        info.setFont(new Font("Verdana", 16));
        info.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(info, 1, 1, 1, 1);
        Label usernameLabel = new Label("Username:");
        usernameLabel.setMinSize(75, 12);
        usernameLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(usernameLabel, 1, 4);
        TextField usernameData = new TextField();
        usernameData.setPromptText("Username");
        usernameData.setMinSize(100, 20);
        GridPane.setConstraints(usernameData, 2, 4);
        Label nameLabel = new Label("Name:");
        nameLabel.setMinSize(75, 12);
        nameLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(nameLabel, 1, 5);
        TextField nameData = new TextField();
        nameData.setPromptText("Name");
        nameData.setMinSize(100, 20);
        GridPane.setConstraints(nameData, 2, 5);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setMinSize(75, 12);
        passwordLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(passwordLabel, 1, 6);
        PasswordField passwordData = new PasswordField();
        passwordData.setPromptText("Password");
        passwordData.setMinSize(100, 20);
        GridPane.setConstraints(passwordData, 2, 6);
        Label passwordLabelVerify = new Label("Verify password:");
        passwordLabelVerify.setMinSize(75, 20);
        passwordLabelVerify.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(passwordLabelVerify, 1, 7);
        PasswordField passwordDataVerify = new PasswordField();
        passwordDataVerify.setPromptText("Password");
        passwordDataVerify.setMinSize(100, 12);
        GridPane.setConstraints(passwordDataVerify, 2, 7);
        Label secretQuestionLabel = new Label("Secret question:");
        secretQuestionLabel.setMinSize(75, 12);
        secretQuestionLabel.setFont(new Font("Verdana", 12));
        GridPane.setConstraints(secretQuestionLabel, 1, 8);
        TextField secretQuestionData = new TextField();
        secretQuestionData.setPromptText("Childhood hobby");
        secretQuestionData.setMinSize(100, 20);
        GridPane.setConstraints(secretQuestionData, 2, 8);
        Button create = new Button("Create");
        create.setMinSize(75, 25);
        GridPane.setConstraints(create, 1, 9);
        Label authors = new Label();
        GridPane.setConstraints(authors, 1, 20, 15, 1);
        authors.setMinSize(75, 12);
        authors.setText("Patryk Kawa Adam Twardosz");
        authors.setFont(new Font("Arial", 12));
        authors.setTextFill(Color.GREENYELLOW);

        Label error = new Label("Error occured");
        GridPane.setConstraints(error, 2, 9);
        error.setMinSize(75, 12);
        error.setFont(new Font("Verdana", 12));
        error.setTextFill(Color.RED);
        error.setVisible(false);


        Label fact = new Label("Did you know...");
        fact.setMinSize(75, 20);
        fact.setFont(new Font("Verdana", 16));
        fact.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(fact, 7, 1, 1, 1);

        TextArea area = new TextArea();
        GridPane.setConstraints(area, 7, 4, 8, 15);
        area.setWrapText(true);
        area.setDisable(true);
        area.setText("Tu będą tipsy jak stworzyć bezpieczne hasło do twojego konta, można też, " +
                "zadać pare zasad jakie musi spelnic haslo\n" + "(?=.*[0-9]) a digit must occur at least once\n" +
                "(?=.*[a-z]) a lower case letter must occur at least once\n" +
                "(?=.*[A-Z]) an upper case letter must occur at least once\n" +
                "(?=.*[@#$%^&+=]) a special character must occur at least once\n" +
                "(?=\\\\S+$) no whitespace allowed in the entire string\n" +
                ".{8,} at least 8 characters");

        create.setOnAction(e ->{
            boolean check = Controller.isNew(usernameData.getText(), passwordData.getText(), passwordDataVerify.getText());
            boolean validate = Controller.passwordRegex(passwordData.getText());
            if (!check){
                error.setVisible(true);
            }
            else if ((check) && (!validate)){
                Controller.alertWindow("New Account", "Password conditions not satisfied");
            }
            else if ((check) && (validate)){
                newAccount.put("username", usernameData.getText());
                newAccount.put("name", nameData.getText());
                newAccount.put("password", passwordData.getText());
                newAccount.put("hint", secretQuestionData.getText());
                System.out.println(newAccount);
                Controller.alertWindow("New Account", "Account Created");
                window.setScene(Logowanie.showLogowanie(window));
            }
        });

        GridPane.setHalignment(ustawienie, HPos.CENTER);

        ustawienie.getChildren().addAll(info, usernameLabel, usernameData, passwordLabel, passwordData,
                nameData, nameLabel, passwordLabelVerify, passwordDataVerify, authors, create,
                error, area, fact, exit, secretQuestionLabel, secretQuestionData);
        Scene noweKonto = new Scene(ustawienie, 800, 340);
        return noweKonto;
    }

}
