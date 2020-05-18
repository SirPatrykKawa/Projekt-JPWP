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

public class EdycjaHasla {


    static Label username, email, site, describtion, password, directory, expireDate;
    static TextField usernameData, emailData, siteData;
    static TextArea describtionData;
    static PasswordField passwordData, verificationData;
    static ChoiceBox<String> directoryData;
    static DatePicker expireDateData;
    static Button resign, submit;
    static String date;
    static HashMap<String, String> recordMap;


    public static Scene showEdycjaHasla(Stage window, HashMap<String, String> record){
        recordMap = record;
        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        ustawienie.setVgap(10);

        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        ColumnConstraints column = new ColumnConstraints(140);
        ustawienie.getColumnConstraints().addAll(column, column);

        Label newPassword = new Label("Edit password");
        newPassword.setFont(new Font("Verdena", 15));
        newPassword.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(newPassword, 0, 0, 2, 1);

        username = new Label("  Username:");
        username.setFont(new Font("Verdena", 12));
        username.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(username, 0, 1);

        usernameData = new TextField();
        usernameData.setText(recordMap.get("login"));
        GridPane.setConstraints(usernameData, 1, 1);

        email = new Label("  Email:");
        email.setFont(new Font("Verdena", 12));
        email.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(email, 0, 2);

        emailData = new TextField();
        emailData.setText(recordMap.get("email"));
        GridPane.setConstraints(emailData, 1, 2);

        site = new Label("  Website:");
        site.setFont(new Font("Verdena", 12));
        site.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(site, 0, 3);

        siteData = new TextField();
        siteData.setText(recordMap.get("url"));
        GridPane.setConstraints(siteData, 1, 3);


        password = new Label("  Password:");
        password.setFont(new Font("Verdena", 12));
        password.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(password, 0, 4);

        passwordData = new PasswordField();
        passwordData.setText(recordMap.get("password"));
        GridPane.setConstraints(passwordData, 1, 4);


        describtion = new Label("  Desctibtion:");
        describtion.setFont(new Font("Verdena", 12));
        describtion.setTextFill(Color.AQUAMARINE);
        GridPane.setConstraints(describtion, 0, 6, 1, 7);

        describtionData = new TextArea();
        describtionData.setText(recordMap.get("describtion"));
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
        expireDateData.setPromptText(recordMap.get("expires_when"));
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
        directoryData.setValue(recordMap.get("directory"));
        GridPane.setConstraints(directoryData, 1, 14);

        submit = new Button("Submit");
        submit.setMinSize(280, 30);
        submit.setMaxSize(280, 30);
        submit.setPrefSize(280, 30);
        GridPane.setConstraints(submit, 0, 17, 2, 1);

        resign = new Button("Resign");
        resign.setMinSize(280, 30);
        resign.setMaxSize(280, 30);
        resign.setPrefSize(280, 30);
        GridPane.setConstraints(resign, 0, 18, 2, 1);

        ustawienie.getChildren().addAll(newPassword, username, usernameData, email, emailData, site, siteData,
                describtion, describtionData, password, passwordData, directory,
                directoryData, expireDateData, expireDate, submit, resign);

        resign.setOnAction(e -> window.setScene(OknoGlowne.showOknoGlowne(window)));

        submit.setOnAction(e -> {
            recordMap.put("login", usernameData.getText());
            recordMap.put("email", emailData.getText());
            recordMap.put("password", passwordData.getText());
            recordMap.put("url", siteData.getText());
            recordMap.put("describtion", describtionData.getText());
            recordMap.put("directory", directoryData.getValue());
            recordMap.put("expires_when", date);
            String expires;
            if (date == null){
                expires = "No";
            }
            else{
                expires = "Yes";
            }
            LocalDate whenAdd = LocalDate.now();
            recordMap.put("expires", expires);
            recordMap.put("created_when", whenAdd.toString());
            recordMap.put("last_modified", whenAdd.toString());
            System.out.println(recordMap);
            Controller.alertWindow("Password edited", "Password edited");
            window.setScene(OknoGlowne.showOknoGlowne(window));
        });


        GridPane.setHalignment(newPassword, HPos.CENTER);

        Scene edycjaHasla = new Scene(ustawienie, 300, 470);
        return edycjaHasla;
    }
}
