package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Notatka {

    public static Scene showNotatka(Stage window){

        GridPane ustawienie = new GridPane();
        ustawienie.setPadding(new Insets(10, 10, 10, 10));
        //ustawienie.setVgap(6);
        Controller.setBackground(ustawienie);
        Controller.movePanel(ustawienie, window);

        ColumnConstraints column1 = new ColumnConstraints(250);
        ustawienie.getColumnConstraints().addAll(column1);

        ColumnConstraints column2 = new ColumnConstraints(600);
        ustawienie.getColumnConstraints().addAll(column2);

        ColumnConstraints column3 = new ColumnConstraints(380);
        ustawienie.getColumnConstraints().add(column3);

        ColumnConstraints column4 = new ColumnConstraints(300);
        ColumnConstraints column5 = new ColumnConstraints(375);

        ustawienie.setVgap(80);

        Label menuSign = new Label("MENU");
        menuSign.setFont(new Font("Verdena", 40));
        menuSign.setTextFill(Color.AQUAMARINE);
        menuSign.setAlignment(Pos.CENTER);

        GridPane.setConstraints(menuSign, 0, 0, 1, 3);



        HBox mainInfo = new HBox(600);


        GridPane.setConstraints(mainInfo, 1,0 , 5, 3);

        //mainInfo.setPrefSize();

        mainInfo.setBorder(new Border(new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));


        Label appName = new Label("QuickPass");
        appName.setFont(new Font("Verdena", 35));
        appName.setTextFill(Color.AQUAMARINE);


        Label hello = new Label("Patryk's notes");
        hello.setFont(new Font("Verdena", 20));
        hello.setMinSize(100, 10);
        hello.setAlignment(Pos.BOTTOM_CENTER);
        hello.setTextFill(Color.AQUAMARINE);

        VBox menu = new VBox(60);
        GridPane.setConstraints(menu, 0, 2, 1, 8);

        Button addNote = new Button("Add note");
        addNote.setMinSize(180, 20);
        addNote.setOnAction(e -> {



        });

        Button eraseNote = new Button("Erase note");
        eraseNote.setMinSize(180, 20);
        eraseNote.setDisable(true);


        Button back = new Button("Back");
        back.setMinSize(180, 20);

        back.setOnAction(e ->{
            window.setScene(OknoGlowne.showOknoGlowne(window));
        });

        Button editNote = new Button("Edit note");
        editNote.setMinSize(180, 20);
        editNote.setDisable(true);

        Button changeProfile = new Button("Change profile");
        changeProfile.setMinSize(180, 20);
        changeProfile.setOnAction(e ->{
            window.setScene(Logowanie.showLogowanie(window));
        });

        Button exit = Controller.exitButton(window);


        Label credits = Controller.authors();

        menu.setBorder(new Border(new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(addNote, eraseNote, editNote, back, changeProfile, exit, credits);

        Border innerBorder = new Border((new BorderStroke(Color.AQUAMARINE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));


        VBox menuSignV = new VBox(20);
        GridPane.setConstraints(menuSignV, 0,0, 1, 3);
        menuSignV.setBorder(innerBorder);

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


        //scrollNotes.pannableProperty().set(true);

        VBox scrollContent = new VBox(30);
        Controller.setBackground(scrollContent);

        int note_number = 19;
        for (int i = 1; i <= note_number; i = i + 2){
            HBox innerHBox = new HBox(150);
            //Controller.setBackground(innerHBox);
            innerHBox.setMaxWidth(600);
            innerHBox.setMinWidth(600);
            innerHBox.setPrefWidth(600);
            innerHBox.setAlignment(Pos.CENTER);
            for (int j = 0; j < 2; j++){
                if (i + j < note_number){
                    Button note_button = new Button("note " + (i + j));
                    note_button.setPrefSize(150, 150);
                    innerHBox.getChildren().add(note_button);
                }
                else if (i + j == note_number){
                    Button note_button = new Button("note " + (i + j));
                    note_button.setPrefSize(150, 150);
                    innerHBox.getChildren().add(note_button);
                }
                else{
                    break;
                }
            }
            scrollContent.getChildren().add(innerHBox);
        }

        scrollNotes.setContent(scrollContent);

        innerScroll.getChildren().addAll(scrollNotes);

        GridPane innerInfo = new GridPane();
        innerInfo.setBorder(innerBorder);
        GridPane.setConstraints(innerInfo, 2, 2, 2, 8);
        innerInfo.getColumnConstraints().add(column5);

        VBox info = new VBox(20);
        GridPane.setConstraints(info, 0, 0, 1, 7);
        info.setAlignment(Pos.CENTER);

        Label noteNumber = new Label("Note number");
        noteNumber.setAlignment(Pos.CENTER);
        noteNumber.setMinSize(200, 75);
        noteNumber.setMaxSize(200, 75);
        noteNumber.setPrefSize(200, 75);
        noteNumber.setFont(new Font("Verdena", 28));
        noteNumber.setTextFill(Color.AQUAMARINE);

        TextArea noteData = new TextArea("Note text");
        noteData.setMinSize(320, 375);
        noteData.setMaxSize(320, 375);
        noteData.setPrefSize(320, 375);
        noteData.setWrapText(true);
        noteData.setDisable(true);

        HBox modified = new HBox(45);
        Label modifiedLabel = new Label("   Modified:");
        modifiedLabel.setAlignment(Pos.CENTER_LEFT);
        modifiedLabel.setMinSize(160, 27);
        modifiedLabel.setMaxSize(160, 27);
        modifiedLabel.setPrefSize(160, 27);
        modifiedLabel.setFont(new Font("Verdena", 20));
        modifiedLabel.setTextFill(Color.AQUAMARINE);

        Label modifiedData = new Label("Modification date");
        modifiedData.setAlignment(Pos.CENTER_LEFT);
        modifiedData.setMinSize(160, 27);
        modifiedData.setMaxSize(160, 27);
        modifiedData.setPrefSize(160, 27);
        modifiedData.setFont(new Font("Verdena", 20));
        modifiedData.setTextFill(Color.AQUAMARINE);

        modified.getChildren().addAll(modifiedLabel, modifiedData);

        HBox created = new HBox(45);
        Label createdLabel = new Label("   Created:");
        createdLabel.setAlignment(Pos.CENTER_LEFT);
        createdLabel.setMinSize(160, 27);
        createdLabel.setMaxSize(160, 27);
        createdLabel.setPrefSize(160, 27);
        createdLabel.setFont(new Font("Verdena", 20));
        createdLabel.setTextFill(Color.AQUAMARINE);

        Label createdData = new Label("Creation date");
        createdData.setAlignment(Pos.CENTER_LEFT);
        createdData.setMinSize(160, 27);
        createdData.setMaxSize(160, 27);
        createdData.setPrefSize(160, 27);
        createdData.setFont(new Font("Verdena", 20));
        createdData.setTextFill(Color.AQUAMARINE);

        created.getChildren().addAll(createdLabel, createdData);

        info.getChildren().addAll(noteNumber, noteData, modified, created);

        innerInfo.getChildren().add(info);


        mainInfo.setAlignment(Pos.CENTER);

        mainInfo.getChildren().addAll(appName, hello);

        GridPane.setHalignment(menuSign, HPos.CENTER);

        ustawienie.getChildren().addAll(menu, menuSign, mainInfo, menuSignV, innerScroll, innerInfo);

        Scene scene = new Scene(ustawienie, 1250, 750);
        return scene;
    }

}
