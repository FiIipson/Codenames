package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class ChangeScreenController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Text playerName;

    static boolean hint_screen = true;

    @FXML
    public void initialize() {
        if (EnterName_Global.red) {
            if (EnterName_Global.operative) {
                playerName.setText(EnterName_Global.redOperativeName);
                EnterName_Global.red = !EnterName_Global.red;
            }
            else {
                playerName.setText(EnterName_Global.redLeaderName);
            }
            playerName.setFill(Color.RED);
        }
        else {
            if (EnterName_Global.operative) {
                playerName.setText(EnterName_Global.blueOperativeName);
                EnterName_Global.red = !EnterName_Global.red;
            }
            else {
                playerName.setText(EnterName_Global.blueLeaderName);
            }
            playerName.setFill(Color.BLUE);
        }
        EnterName_Global.operative = !EnterName_Global.operative;
    }

    @FXML
    public void toGame(ActionEvent e) throws IOException {
        if (hint_screen) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/HintScreen.fxml"));
            root = loader.load();

            hint_screen = false;
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/Game.fxml"));
            root = loader.load();


            hint_screen = true;
        }
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
