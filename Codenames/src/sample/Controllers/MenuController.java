package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

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
    seconds = time_limit % 60 / 10;
    id_time_limit.setText(String.valueOf(time_limit / 60) + ":" + seconds + "0");
  }
  @FXML
  protected void quitGame(ActionEvent e) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit");
    alert.setHeaderText("");
    alert.setContentText("Are you sure you want to quit the game?");

    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");

    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    if (alert.showAndWait().get() == buttonTypeYes) {
      System.exit(0);
    }
  }
  @FXML
  public void toGame(ActionEvent e) throws IOException {
    root = FXMLLoader.load(getClass().getResource("/Scenes/EnterName.fxml"));
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
