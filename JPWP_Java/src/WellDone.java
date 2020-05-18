import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WellDone {

    public static Scene finish(Stage window) {

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        Label congratulation = new Label("Gratuluję, skonczyłeś zadania z JavaFX!");
        congratulation.setMinSize(200, 100);
        congratulation.setFont(new Font(18));

        Button close = new Button("Zamknij okienko");

        close.setOnAction(e -> window.close());

        box.getChildren().addAll(congratulation, close);

        return new Scene(box, 400, 300);

    }

}
