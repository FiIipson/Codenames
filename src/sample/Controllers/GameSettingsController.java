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

public class GameSettingsController {
    static public int time_limit = 90;
    static public int difficulty = 1;
    static public boolean hints = false;
    static public int seconds = 3;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    public Text id_hints;
    @FXML
    public Text id_time_limit;
    @FXML
    public Text id_difficulty;
    @FXML
    public void initialize() {
        id_time_limit.setText(time_limit / 60 + ":" + seconds + "0");
        id_hints.setText(hints ? "On" : "Off");
        if (difficulty == 0) {
          id_difficulty.setText("Easy");
        } else if (difficulty == 1) {
          id_difficulty.setText("Mid");
        } else {
          id_difficulty.setText("Hard");
        }
    }
    @FXML
    protected void onHints() {
        hints = true;
        id_hints.setText("On");
    }
    @FXML
    protected void offHints() {
        hints = false;
        id_hints.setText("Off");
    }
    @FXML
    protected void addTimeLimit() {
        if (time_limit == 210) return;
        if (time_limit == 180) {
            id_time_limit.setText("∞");
            time_limit += 30;
            return;
        }
        else time_limit += 30;
        seconds = time_limit % 60 / 10;
        id_time_limit.setText(time_limit / 60 + ":" + seconds + "0");
    }
    @FXML
    protected void upDifficulty() {
        if (difficulty < 2) difficulty++;
        if (difficulty == 1) id_difficulty.setText("Mid");
        else id_difficulty.setText("Hard");
    }
    @FXML
    protected void downDifficulty() {
        if (difficulty > 0) difficulty--;
        if (difficulty == 1) id_difficulty.setText("Mid");
        else id_difficulty.setText("Easy");
    }
    @FXML
    protected void minusTimeLimit() {
        if (time_limit == 0) {
            id_time_limit.setText("∞");
            return;
        }
        else time_limit -= 30;
        if (time_limit == 0) {
            id_time_limit.setText("∞");
            return;
        }
        seconds = time_limit % 60 / 10;
        id_time_limit.setText(time_limit / 60 + ":" + seconds + "0");
    }
    @FXML
    protected void toGame(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/EnterName.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    protected void back(ActionEvent e) throws IOException {
        time_limit = 90;
        difficulty = 1;
        hints = false;
        seconds = 3;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Menu.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
