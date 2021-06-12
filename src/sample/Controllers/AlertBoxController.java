package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AlertBoxController {
    public Button closeButton;

    @FXML
    Text errorMessage;
    @FXML
    protected void initialize() {
        errorMessage.setText(GlobalVar.currentError);
    }
    @FXML
    public void closeButton() {
        Stage alertBox = (Stage) closeButton.getScene().getWindow();
        alertBox.close();
    }
}
