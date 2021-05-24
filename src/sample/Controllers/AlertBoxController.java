package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
