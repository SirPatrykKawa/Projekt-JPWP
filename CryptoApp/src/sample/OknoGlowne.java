package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OknoGlowne {

        static ArrayList<Label> passwordServiceLabel = new ArrayList<>();
        static ArrayList<Label> passwordUserEmailLabel = new ArrayList<>();
        static ArrayList<Label> dataAnswer = new ArrayList<>();
        static HashMap<String, String> test = new HashMap<>();
        static Label serviceName, passInfo, emailInfo, siteInfo, describtion, lastModification, created, nameInfo;

    public static Scene showOknoGlowne(Stage window){


        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        ustawienie.setVgap(6);
        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        ColumnConstraints column1 = new ColumnConstraints(250);
        ustawienie.getColumnConstraints().addAll(column1);

        ColumnConstraints column4 = new ColumnConstraints(300);
        ustawienie.getColumnConstraints().addAll(column4, column4);

        ColumnConstraints column2 = new ColumnConstraints(140);
        ustawienie.getColumnConstraints().add(column2);

        ColumnConstraints column3 = new ColumnConstraints(100);
        ustawienie.getColumnConstraints().addAll(column3, column2);


        ustawienie.setVgap(80);



        Label menuSign = new Label("MENU");
        menuSign.setFont(new Font("Verdena", 40));
        menuSign.setTextFill(Color.AQUAMARINE);
        menuSign.setAlignment(Pos.CENTER);

        GridPane.setConstraints(menuSign, 0, 0, 1, 3);



        HBox mainInfo = new HBox(130);


        GridPane.setConstraints(mainInfo, 1,0 , 5, 3);

        //mainInfo.setPrefSize();

        mainInfo.setBorder(new Border(new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));


        Label appName = new Label("QuickPass");
        appName.setFont(new Font("Verdena", 35));
        appName.setTextFill(Color.AQUAMARINE);

        TextField search = new TextField();
        search.setPromptText("Search");
        search.setFont(new Font("Verdena", 15));
        search.setMinSize(330, 15);
        search.setAlignment(Pos.BASELINE_CENTER);

        Label hello = new Label("Hi Patryk!");
        hello.setFont(new Font("Verdena", 20));
        hello.setMinSize(100, 10);
        hello.setAlignment(Pos.BOTTOM_CENTER);
        hello.setTextFill(Color.AQUAMARINE);

        mainInfo.setAlignment(Pos.CENTER);

        mainInfo.getChildren().addAll(appName, search, hello);

        VBox menu = new VBox(30);
        GridPane.setConstraints(menu, 0, 2, 1, 8);

        Button addPassword = new Button("Add password");
        addPassword.setMinSize(180, 20);

        Button erasePassword = new Button("Erase password");
        erasePassword.setMinSize(180, 20);
        erasePassword.setDisable(true);


        Button work = new Button("Work");
        work.setMinSize(180, 20);

        Button socialMedia = new Button("Social Media");
        socialMedia.setMinSize(180, 20);

        Button culture = new Button("Culture");
        culture.setMinSize(180, 20);

        Button notes = new Button("Notes");
        notes.setMinSize(180, 20);

        notes.setOnAction(e ->{
            window.setScene(Notatka.showNotatka(window));
        });

        Button editPassword = new Button("Edit password");
        editPassword.setMinSize(180, 20);
        editPassword.setDisable(true);

        Button changeProfile = new Button("Change profile");
        changeProfile.setMinSize(180, 20);

        Button exit = Controller.exitButton(window);


        Label credits = Controller.authors();

        menu.setBorder(new Border(new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(addPassword, erasePassword, editPassword, work, socialMedia, culture, notes, changeProfile, exit, credits);

        addPassword.setOnAction(e -> {
            Scene newPassword = NoweHaslo.showNoweHaslo(window);
            window.setScene(newPassword);
        });


        changeProfile.setOnAction(e ->{
            Scene logIn = Logowanie.showLogowanie(window);
            window.setScene(logIn);
        });



        Border innerBorder = new Border((new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));


        VBox menuSignV = new VBox(20);
        GridPane.setConstraints(menuSignV, 0,0, 1, 3);
        menuSignV.setBorder(innerBorder);


        GridPane innerGridPassword = new GridPane();
        innerGridPassword.setPadding(new Insets(10, 0, 0, 0));
        //innerGridPassword.setVgap(80);
        //innerGridPassword.setGridLinesVisible(true);
        innerGridPassword.setBorder(innerBorder);
        GridPane.setConstraints(innerGridPassword, 1, 2, 2, 8);

        innerGridPassword.getColumnConstraints().addAll(column4, column4);




        GridPane innerGridInfo = new GridPane();
        innerGridInfo.setPadding(new Insets(10, 0, 0, 0));
        //innerGridInfo.setGridLinesVisible(true);
        innerGridInfo.setBorder(innerBorder);
        GridPane.setConstraints(innerGridInfo, 3, 2, 3, 8);

        innerGridInfo.getColumnConstraints().addAll(column2, column3, column2);

        try{
            FileInputStream icon = new FileInputStream("C:\\Users\\kawap\\Desktop\\Informatyka\\CleanVersion\\Icons\\A.png");
            Image image = new Image(icon);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            GridPane.setConstraints(imageView, 1, 0);

            innerGridInfo.getChildren().addAll(imageView);
        } catch (IOException e){
            e.printStackTrace();
        }

        GridPane innerScroll = new GridPane();
        //innerGridPassword.setVgap(80);
        //innerGridPassword.setGridLinesVisible(true);
        innerScroll.setBorder(innerBorder);
        GridPane.setConstraints(innerScroll, 1, 2, 2, 8);

        innerScroll.getColumnConstraints().addAll(column4, column4);

        ScrollPane scrollNotes = new ScrollPane();
        scrollNotes.setPadding(new Insets(10, 0, 10, 0));
        GridPane.setConstraints(scrollNotes, 0, 0, 2, 8);
        scrollNotes.setMinSize(600, 565);
        scrollNotes.setMaxSize(600, 565);
        scrollNotes.setPrefSize(600, 565);
        scrollNotes.setStyle("-fx-background-color: transparent;");


        VBox scrollContent = new VBox(30);
        Controller.setBackground(scrollContent);

        int password_number = 40;
        for (int i = 1; i <= password_number; i++){
            HBox innerHBox = new HBox(150);
            innerHBox.setMaxWidth(600);
            innerHBox.setMinWidth(600);
            innerHBox.setPrefWidth(600);
            innerHBox.setAlignment(Pos.CENTER);

            Button password_button = new Button("Password " + i);
            password_button.setMinSize(550, 75);
            boolean password_expired = true;
            int index = i;
            if (password_expired) {

                password_button.setTextFill(Color.RED);


            }
            innerHBox.getChildren().add(password_button);
            scrollContent.getChildren().add(innerHBox);

            password_button.setOnAction(e -> {
                test = NoweHaslo.dropPassword();
                erasePassword.setDisable(false);
                editPassword.setDisable(false);
                serviceName.setText("Siema siema " + index);
                // And so on

                erasePassword.setOnAction(event -> {

                    boolean deletePassowrd = Controller.checkExit("Erase password", "Are you sure?");
                    if (deletePassowrd) {

                        password_button.setVisible(false);

                    }

                });

                editPassword.setOnAction(event -> {

                        window.setScene(EdycjaHasla.showEdycjaHasla(window, test));

                });

            });
        }

        scrollNotes.setContent(scrollContent);

        innerScroll.getChildren().addAll(scrollNotes);


        serviceName = new Label("   Site name");
        //Label serviceName = new Label(passwordServiceLabel.get(0).getText());
        GridPane.setConstraints(serviceName, 1, 1);
        serviceName.setAlignment(Pos.TOP_CENTER);
        serviceName.setFont(new Font("Verdena", 15));
        serviceName.setTextFill(Color.AQUAMARINE);
        serviceName.setPrefSize(100, 25);
        serviceName.setMinSize(100, 25);

        nameInfo = new Label("   Username:");
        GridPane.setConstraints(nameInfo, 0, 2);
        nameInfo.setAlignment(Pos.CENTER_LEFT);
        nameInfo.setFont(new Font("Verdena", 15));
        nameInfo.setTextFill(Color.AQUAMARINE);
        nameInfo.setPrefSize(140, 50);
        nameInfo.setMinSize(140, 50);

        passInfo = new Label("   Password:");
        GridPane.setConstraints(passInfo, 0, 3);
        passInfo.setAlignment(Pos.CENTER_LEFT);
        passInfo.setFont(new Font("Verdena", 15));
        passInfo.setTextFill(Color.AQUAMARINE);
        passInfo.setPrefSize(140, 50);
        passInfo.setMinSize(140, 50);

        emailInfo = new Label("   Email:");
        GridPane.setConstraints(emailInfo, 0, 4);
        emailInfo.setAlignment(Pos.CENTER_LEFT);
        emailInfo.setFont(new Font("Verdena", 15));
        emailInfo.setTextFill(Color.AQUAMARINE);
        emailInfo.setPrefSize(140, 50);
        emailInfo.setMinSize(140, 50);

        siteInfo = new Label("   Website/Service:");
        GridPane.setConstraints(siteInfo, 0, 5);
        siteInfo.setAlignment(Pos.CENTER_LEFT);
        siteInfo.setFont(new Font("Verdena", 15));
        siteInfo.setTextFill(Color.AQUAMARINE);
        siteInfo.setPrefSize(140, 50);
        siteInfo.setMinSize(140, 50);

        describtion = new Label("   Describtion:");
        GridPane.setConstraints(describtion, 0, 6);
        describtion.setAlignment(Pos.CENTER_LEFT);
        describtion.setFont(new Font("Verdena", 15));
        describtion.setTextFill(Color.AQUAMARINE);
        describtion.setPrefSize(140, 125);
        describtion.setMinSize(140, 125);

        lastModification = new Label("   Last modification:");
        GridPane.setConstraints(lastModification, 0, 7);
        lastModification.setAlignment(Pos.CENTER_LEFT);
        lastModification.setFont(new Font("Verdena", 15));
        lastModification.setTextFill(Color.AQUAMARINE);
        lastModification.setPrefSize(140, 50);
        lastModification.setMinSize(140, 50);

        created = new Label("   Created:");
        GridPane.setConstraints(created, 0, 8);
        created.setAlignment(Pos.CENTER_LEFT);
        created.setFont(new Font("Verdena", 15));
        created.setTextFill(Color.AQUAMARINE);
        created.setPrefSize(140, 50);
        created.setMinSize(140, 50);


        innerGridInfo.getChildren().addAll(serviceName, nameInfo, passInfo, emailInfo, siteInfo, describtion,
                lastModification, created);

        for (int i = 0; i < 4; i++){
            HBox addBox = new HBox(15);
            if (i == 1){
                Button hide = new Button("Hide");
                Label shownPassword = new Label("password");
                dataAnswer.add(shownPassword);
                Label addAnswer = new Label();
                addBox.setAlignment(Pos.CENTER);
                int current_iteration = i;
                addAnswer.setText("Answer " + (i + 1));
                addAnswer.setText("*********");
                addAnswer.setTextFill(Color.AQUAMARINE);
                addAnswer.setFont(new Font("Verdena", 15));
                addAnswer.setPrefSize(100, 30);
                addAnswer.setMinSize(100, 30);
                Button show = new Button("Show");
                show.setFont(new Font("Verdena", 15));
                show.setPrefSize(100, 30);
                show.setMinSize(100, 30);
                addBox.getChildren().addAll(addAnswer, show);
                GridPane.setConstraints(addBox, 1, (2 + i), 2, 1);
                innerGridInfo.getChildren().addAll(addBox);
                show.setOnAction(e ->{
                    addBox.getChildren().removeAll(addAnswer, show);
                    shownPassword.setTextFill(Color.AQUAMARINE);
                    shownPassword.setFont(new Font("Verdena", 15));
                    shownPassword.setPrefSize(100, 30);
                    shownPassword.setMinSize(100, 30);
                    hide.setFont(new Font("Verdena", 15));
                    hide.setPrefSize(100, 30);
                    hide.setMinSize(100, 30);
                    addBox.getChildren().addAll(shownPassword, hide);
                    GridPane.setConstraints(addBox, 1, (2 + current_iteration), 2, 1);
                });
                hide.setOnAction(e ->{
                    addBox.getChildren().removeAll(shownPassword, hide);
                    addAnswer.setText("Answer " + (current_iteration + 1));
                    addAnswer.setText("*********");
                    addAnswer.setTextFill(Color.AQUAMARINE);
                    addAnswer.setFont(new Font("Verdena", 15));
                    addAnswer.setPrefSize(100, 30);
                    addAnswer.setMinSize(100, 30);
                    show.setFont(new Font("Verdena", 15));
                    show.setPrefSize(100, 30);
                    show.setMinSize(100, 30);
                    addBox.getChildren().addAll(addAnswer, show);
                    GridPane.setConstraints(addBox, 1, (2 + current_iteration), 2, 1);
                });
            }
            else{
                Label addAnswer = new Label("Answer " + (i + 1));
                dataAnswer.add(addAnswer);
                addBox.getChildren().add(addAnswer);
                GridPane.setConstraints(addBox, 1, (2 + i), 2, 1);
                addBox.setAlignment(Pos.CENTER);
                addAnswer.setFont(new Font("Verdena", 15));
                addAnswer.setTextFill(Color.AQUAMARINE);
                addAnswer.setPrefSize(220, 50);
                addAnswer.setMinSize(220, 50);
                innerGridInfo.getChildren().add(addBox);
            }
        }



        HBox descBox = new HBox(15);
        Label describtionAnswer = new Label("Answer " + 5);
        dataAnswer.add(describtionAnswer);
        descBox.getChildren().add(describtionAnswer);
        describtionAnswer.setWrapText(true);
        GridPane.setConstraints(descBox, 1, 6, 111, 1);
        descBox.setAlignment(Pos.CENTER);
        describtionAnswer.setFont(new Font("Verdena", 15));
        describtionAnswer.setTextFill(Color.AQUAMARINE);
        describtionAnswer.setPrefSize(220, 125);
        describtionAnswer.setMinSize(220, 125);
        innerGridInfo.getChildren().add(descBox);

        for (int i = 0; i < 2; i++){
            HBox addBox = new HBox(15);
            Label addAnswer = new Label("Answer " + (i + 6));
            dataAnswer.add(addAnswer);
            addBox.getChildren().add(addAnswer);
            GridPane.setConstraints(addBox, 1, (7 + i), 2, 1);
            addBox.setAlignment(Pos.CENTER);
            addAnswer.setFont(new Font("Verdena", 15));
            addAnswer.setTextFill(Color.AQUAMARINE);
            addAnswer.setPrefSize(220, 50);
            addAnswer.setMinSize(220, 50);
            innerGridInfo.getChildren().add(addBox);
        }
        work.setOnAction(e ->{
            for (int i = 0; i < dataAnswer.size(); i++){
                System.out.println(dataAnswer.get(i).getText());
            }
        });

        GridPane.setHalignment(menuSign, HPos.CENTER);

        ustawienie.getChildren().addAll(menu, menuSign, menuSignV, mainInfo, innerGridPassword, innerGridInfo, innerScroll);

        Scene scene = new Scene(ustawienie, 1250, 750);
        return scene;
    }
}
