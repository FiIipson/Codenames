package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

enum ErrorCode {
    NAMES_OK, NAME_MISSING, NAME_TOO_LONG, NAMES_NOT_PAIRWISE_DISTINCT
}

public class EnterNamesController {
    @FXML
    Parent root;
    @FXML
    Button okButton;
    @FXML
    TextField blueLeaderField;
    @FXML
    public TextField blueOperativeField;
    @FXML
    public TextField redLeaderField;
    @FXML
    public TextField redOperativeField;

    @FXML
    public void toGame() throws IOException {
        Global.blueLeaderName = blueLeaderField.getText();
        Global.blueOperativeName = blueOperativeField.getText();
        Global.redLeaderName = redLeaderField.getText();
        Global.redOperativeName = redOperativeField.getText();
        ErrorCode e = Global.validate(Global.blueLeaderName, Global.blueOperativeName, Global.redLeaderName, Global.redOperativeName);
        if (e != ErrorCode.NAMES_OK) {
            Stage alertBox = new Stage();
            alertBox.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("alertbox.fxml"));
            root = loader.load();
            alertBox.setScene(new Scene(root));
            alertBox.setTitle("An error has occurred");
            alertBox.showAndWait();
        } else {
            Stage s = (Stage) okButton.getScene().getWindow();
            s.close();
        }
    }
}