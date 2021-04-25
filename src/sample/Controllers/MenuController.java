package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
  @FXML
  Parent root;
  @FXML
  Stage stage;

  @FXML
  protected void quitGame() {
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
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/GameSettings.fxml")));
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
  @FXML
  public void howToPlay(ActionEvent e) throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/HowToPlay/HowToPlay1.fxml")));
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
  @FXML
  public void credits(ActionEvent e) throws IOException {
    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Credits.fxml")));
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
}
