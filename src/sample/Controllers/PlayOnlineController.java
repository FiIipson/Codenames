package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayOnlineController {
    @FXML
    Parent root;
    @FXML
    Stage stage;

    @FXML
    protected void back(ActionEvent e) throws IOException {
        GlobalVar.timeLimit = 90;
        GlobalVar.difficulty = 1;
        GlobalVar.hints = false;
        GlobalVar.seconds = 3;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Menu.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
