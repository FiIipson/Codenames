package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlertBox {
    public Button closeButton;

    @FXML
    Text errorMessage;
    @FXML
    protected void initialize() {
        errorMessage.setText(Global.errorMessage(Global.validate(Global.blueLeaderName, Global.blueOperativeName, Global.redLeaderName, Global.redOperativeName)));
    }
    @FXML
    public void closeButton() {
        Stage alertBox = (Stage) closeButton.getScene().getWindow();
        alertBox.close();
    }
}
