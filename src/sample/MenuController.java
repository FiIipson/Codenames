package sample;

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

public class MenuController {
  static public int time_limit = 90;
  static public int difficulty = 1;
  static public boolean hints = true;
  static public int seconds = 3;
  @FXML
  Parent root;
  @FXML
  Stage stage;
  @FXML
  Scene scene;
  @FXML
  Text id_hints;
  @FXML
  public Text id_time_limit;
  @FXML
  Text id_difficulty;
  @FXML
  protected void onHints(ActionEvent e) {
    hints = true;
    id_hints.setText("On");
  }
  @FXML
  protected void offHints(ActionEvent e) {
    hints = false;
    id_hints.setText("Off");
  }
  @FXML
  protected void addTimeLimit(ActionEvent e) {
    if (time_limit == 210) return;
    if (time_limit == 180) {
      id_time_limit.setText("∞");
      time_limit += 30;
      return;
    }
    else time_limit += 30;
    seconds = time_limit % 60 / 10;
    id_time_limit.setText(String.valueOf(time_limit / 60) + ":" + seconds + "0");
  }
  @FXML
  protected void upDifficulty(ActionEvent e) {
    if (difficulty < 2) difficulty++;
    if (difficulty == 1) id_difficulty.setText("Mid");
    else id_difficulty.setText("Hard");
  }
  @FXML
  protected void downDifficulty(ActionEvent e) {
    if (difficulty > 0) difficulty--;
    if (difficulty == 1) id_difficulty.setText("Mid");
    else id_difficulty.setText("Easy");
  }
  @FXML
  protected void minusTimeLimit(ActionEvent e) {
    if (time_limit == 0) {
      id_time_limit.setText("∞");
      return;
    }
    else time_limit -= 30;
    if (time_limit == 0) {
      id_time_limit.setText("∞");
      return;
    }
    int seconds = time_limit % 60 / 10;
    id_time_limit.setText(String.valueOf(time_limit / 60) + ":" + seconds + "0");
  }
  @FXML
  protected void quitGame(ActionEvent e) {
    System.exit(0);
  }
  @FXML
  public void toGame(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
    root = loader.load();

    GameController gameController = loader.getController();
    gameController.setTime(time_limit, seconds);

    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
