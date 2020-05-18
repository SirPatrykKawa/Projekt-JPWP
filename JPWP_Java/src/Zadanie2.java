import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Zadanie2 extends Application {

    Stage window;
    Scene look;
    BorderPane setting;
    Button bottomButton, topButton;
    ProgressIndicator progressCircle;
    // Elementy rozwiązania
    double i = 0;
    Button GGWP = new Button();


    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;
        window.setTitle("Zadania JavaFX JPWP");
        window.setResizable(false);

        // Inicjalizacja siatki

        setting = new BorderPane();

        // Dolny przycisk

        bottomButton = new Button("Bottom button");

        // Ustawienie wielkosci przycisku

        bottomButton.setMinSize(400, 100);
        bottomButton.setMaxSize(400, 100);

        // Dodanie przycisku do dolnej czesci siatki

        setting.setBottom(bottomButton);

        // Górny przycisk

        topButton = new Button("Top button");
        topButton.setMinSize(400, 100);
        topButton.setMaxSize(400, 100);
        topButton.setVisible(false);
        setting.setTop(topButton);

        // Koło progresu

        progressCircle = new ProgressIndicator();
        progressCircle.setMinSize(250, 250);
        progressCircle.setMaxSize(250, 250);
        setting.setCenter(progressCircle);


        // Zadanie 2: Nałóż opóźnienie wynoszące trzy sekundy, które uruchomi się po kliknięciu dolnego przycisku.
        // Po upływie trzech sekund dolny przycisk zniknie, a pojawi sie górny.
        // Aby nałożyć opóźnienie należy stworzyć obiekt klasy PauseTransitionm który w konstruktorze jako argument
        // Przyjmie czas trzy sekund (obiekt klasy Duration)

        // Rozwiązanie!!!


        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            bottomButton.setVisible(false);
            topButton.setVisible(true);
        });

        bottomButton.setOnAction(e -> delay.play());

        ///////////////////////////////////


        // Zadanie 3: W centralnym punkcie siatki znajduje sie koło progresu.
        // Spraw, aby po czterech kliknięciach górnego przycisku pasek był w pełni naładowany.
        // Po naładowaniu należy sprawić, że w centralnej części siatki pojawi sie przycisk z napisem "GG WP"
        // A górny przycisk zniknie
        // UWAGA: Po każdym kliknięciu w górny przycisk powinno być widać rosnący progres!

        // Rozwiązanie!!!

        topButton.setOnAction(e -> {

            i = i + 0.25;
            progressCircle.setProgress(i);

            if (progressCircle.getProgress() == 1) {

                topButton.setVisible(false);
                GGWP.setText("GG WP");
                setting.setCenter(GGWP);

            }
        });

        ///////////////////////////////////

        // Zadanie 4: Spraw, aby po kliknięcie w przycisk GG WP scena zmieniła sie na taką, którą zwraca metoda
        // Finish z klasy WellDone

        // Rozwiązanie!!!

        GGWP.setOnAction(e ->{

            Scene finish = WellDone.finish(window);
            window.setScene(finish);

        });

        ///////////////////////

        // Inicjalizacja nowej sceny o siatce setting oraz wymiarach 400 na 600

        look = new Scene(setting, 400, 600);

        // Zmiana widoku (sceny) w oknie programu

        window.setScene(look);

        // Wyświetlenie okna programu

        window.show();


    }

    public static void main(String[] args) {


        launch(args);

    }

}
