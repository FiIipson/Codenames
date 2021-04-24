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

public class ChangeScreen {
  @FXML
  Parent root;
  @FXML
  Stage stage;

  @FXML
  public void toGame(ActionEvent e) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Game.fxml"));
    root = loader.load();

    GameController gameController = loader.getController();
    gameController.setTime(MenuController.time_limit, MenuController.seconds);

    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
