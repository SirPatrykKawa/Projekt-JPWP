package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;

public class NoweHaslo {

    static Label username, email, site, describtion, password, verification, directory, expireDate;
    static TextField usernameData, emailData, siteData;
    static TextArea describtionData;
    static PasswordField passwordData, verificationData;
    static ChoiceBox<String> directoryData;
    static DatePicker expireDateData;
    static Button resign, submit;
    static String date;
    static HashMap<String, String> newRecord = new HashMap<>();

    public static Scene showNoweHaslo(Stage window){
        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        ustawienie.setVgap(10);

        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        ColumnConstraints column = new ColumnConstraints(140);
        ustawienie.getColumnConstraints().addAll(column, column);

        Label newPassword = new Label("Create a new password");
        newPassword.setFont(new Font("Verdena", 15));
        newPassword.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(newPassword, 0, 0, 2, 1);

        username = new Label("  Username:");
        username.setFont(new Font("Verdena", 12));
        username.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(username, 0, 1);

        usernameData = new TextField();
        usernameData.setPromptText("Username");
        GridPane.setConstraints(usernameData, 1, 1);

        email = new Label("  Email:");
        email.setFont(new Font("Verdena", 12));
        email.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(email, 0, 2);

        emailData = new TextField();
        emailData.setPromptText("Email");
        GridPane.setConstraints(emailData, 1, 2);

        site = new Label("  Website:");
        site.setFont(new Font("Verdena", 12));
        site.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(site, 0, 3);

        siteData = new TextField();
        siteData.setPromptText("Website");
        GridPane.setConstraints(siteData, 1, 3);


        password = new Label("  Password:");
        password.setFont(new Font("Verdena", 12));
        password.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(password, 0, 4);

        passwordData = new PasswordField();
        passwordData.setPromptText("Password");
        GridPane.setConstraints(passwordData, 1, 4);


        verification = new Label("  Verify password:");
        verification.setFont(new Font("Verdena", 12));
        verification.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(verification, 0, 5);

        verificationData = new PasswordField();
        verificationData.setPromptText("Verify password");
        GridPane.setConstraints(verificationData, 1, 5);


        describtion = new Label("  Desctibtion:");
        describtion.setFont(new Font("Verdena", 12));
        describtion.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(describtion, 0, 6, 1, 7);

        describtionData = new TextArea();
        describtionData.setPromptText("Describtion");
        describtionData.setWrapText(true);
        describtionData.setFont(new Font("Verdena", 10));
        describtionData.setMinSize(140, 60);
        describtionData.setMaxSize(140, 60);
        describtionData.setPrefSize(140, 60);
        GridPane.setConstraints(describtionData, 1, 6, 1, 7);

        expireDate = new Label("  Expire date");
        expireDate.setFont(new Font("Verdena", 12));
        expireDate.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(expireDate, 0, 13);

        expireDateData = new DatePicker();
        expireDateData.setMinSize(140, 60);
        expireDateData.setMaxSize(140, 60);
        expireDateData.setPrefSize(140, 60);
        GridPane.setConstraints(expireDateData, 1, 13);

        expireDateData.setOnAction(e ->{
            LocalDate expire = expireDateData.getValue();
            date = expire.toString();
        });

        directory = new Label("  Choose directory");
        directory.setFont(new Font("Verdena", 12));
        directory.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(directory, 0, 14);	

        directoryData = new ChoiceBox<>();
        directoryData.getItems().addAll("None", "Work", "Social Media", "Culture");
        directoryData.setPrefSize(140, 20);
        directoryData.setValue("None");
        GridPane.setConstraints(directoryData, 1, 14);

        submit = new Button("Submit");
        submit.setMinSize(280, 30);
        submit.setMaxSize(280, 30);
        submit.setPrefSize(280, 30);
        GridPane.setConstraints(submit, 0, 15, 2, 1);

        resign = new Button("Resign");
        resign.setMinSize(280, 30);
        resign.setMaxSize(280, 30);
        resign.setPrefSize(280, 30);
        GridPane.setConstraints(resign, 0, 16, 2, 1);

        ustawienie.getChildren().addAll(newPassword, username, usernameData, email, emailData, site, siteData,
                describtion, describtionData, password, passwordData, verification, verificationData, directory,
                directoryData, expireDateData, expireDate, submit, resign);

        resign.setOnAction(e -> window.setScene(OknoGlowne.showOknoGlowne(window)));

        submit.setOnAction(e -> {
            if ( (passwordData.getText().equals(verificationData.getText())) && (passwordData.getText().length() > 0) && (Controller.passwordRegex(passwordData.getText())) ){

                newRecord.put("login", usernameData.getText());
                newRecord.put("email", emailData.getText());
                newRecord.put("password", passwordData.getText());
                newRecord.put("url", siteData.getText());
                newRecord.put("describtion", describtionData.getText());
                newRecord.put("directory", directoryData.getValue());
                newRecord.put("expires_when", date);
                String expires;
                if (date == null){
                    expires = "No";
                }
                else{
                    expires = "Yes";
                }
                LocalDate whenAdd = LocalDate.now();
                newRecord.put("expires", expires);
                newRecord.put("created_when", whenAdd.toString());
                newRecord.put("last_modified", whenAdd.toString());
                System.out.println(newRecord);
                Controller.alertWindow("Password created", "Password added to the base");
                window.setScene(OknoGlowne.showOknoGlowne(window));

            } else {

                Controller.alertWindow("Password error", "Password not correct");

            }
        });


        GridPane.setHalignment(newPassword, HPos.CENTER);

        Scene noweHaslo = new Scene(ustawienie, 300, 470);
        return noweHaslo;
    }

    static HashMap<String, String> dropPassword() {

        System.out.println("QQQ");
        HashMap<String, String> drop = newRecord;
        return drop;

    }
}
