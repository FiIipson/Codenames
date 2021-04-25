package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ChangeScreenController {
    @FXML
    Parent root;
    @FXML
    Stage stage;

    static boolean hint_screen = true;

    @FXML
    public void toGame(ActionEvent e) throws IOException {
        if (hint_screen) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/HintScreen.fxml"));
            root = loader.load();

            HintScreenController hintScreenController = loader.getController();
            hintScreenController.setTime(GameSettingsController.time_limit, GameSettingsController.seconds);

            hint_screen = false;
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Game.fxml"));
            root = loader.load();

            GameController gameController = loader.getController();
            gameController.setTime(GameSettingsController.time_limit, GameSettingsController.seconds);

            hint_screen = true;
        }
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
