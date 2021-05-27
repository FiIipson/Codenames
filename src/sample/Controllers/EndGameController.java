package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class EndGameController {
  String blue_win = "Blue team wins by revealing all the cards";
  String red_win = "Red team wins by revealing all the cards";
  String red_bomb = "Red team wins by detonating enemies";
  String blue_bomb = "Blue team wins by detonating enemies";
  @FXML
  Text to_be_shown;
  @FXML
  public void initialize() {
    if (GlobalVar.result_ == GlobalVar.Result.BLUE) {
      to_be_shown.setText(blue_win);
    }
    else if (GlobalVar.result_ == GlobalVar.Result.RED) {
      to_be_shown.setText(red_win);
    }
    else if (GlobalVar.result_ == GlobalVar.Result.RED_BOMB) {
      to_be_shown.setText(blue_bomb);
    }
    else {
      to_be_shown.setText(red_bomb);
    }
  }
  @FXML
  public void toGame(ActionEvent event) throws IOException {
    GlobalVar.setWords(GlobalVar.difficulty);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/NeutralScreen.fxml"));
    Parent root = loader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
  @FXML
  protected void back(ActionEvent e) throws IOException {
    GlobalVar.timeLimit = 90;
    GlobalVar.difficulty = 1;
    GlobalVar.hints = false;
    GlobalVar.seconds = 3;
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Menu.fxml")));
    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
