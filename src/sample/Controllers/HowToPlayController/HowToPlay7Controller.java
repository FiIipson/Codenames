package sample.Controllers.HowToPlayController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HowToPlay7Controller {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    protected void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Menu.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    protected void prev(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/HowToPlay/HowToPlay6.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
