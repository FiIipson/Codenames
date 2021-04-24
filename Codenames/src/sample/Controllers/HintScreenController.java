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

public class HintScreenController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Text time;

    @FXML
    public void toLoadingScreen(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/ChangeScreen.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setTime(int time_limit, int seconds) {
        if (time_limit == 0 || time_limit == 210) {
            time.setText("∞");
        }
        else {
            time.setText(String.valueOf(time_limit / 60 + ":" + seconds + "0"));
        }
    }
}
