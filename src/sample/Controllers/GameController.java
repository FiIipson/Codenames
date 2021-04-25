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

public class GameController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Text time;
    @FXML
    Text hintWord;
    @FXML
    Text hintNumber;

    @FXML
    public void initialize() {
        hintWord.setText(EnterName_Global.hintString);
        hintNumber.setText(String.valueOf(EnterName_Global.hintNumber));
        int time_limit = GameSettingsController.time_limit;
        int seconds = GameSettingsController.seconds;
        if (time_limit == 0 || time_limit == 210) {
            time.setText("∞");
        }
        else {
            time.setText(String.valueOf(time_limit / 60 + ":" + seconds + "0"));
        }
    }

    @FXML
    public void toLoadingScreen(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/ChangeScreen.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}