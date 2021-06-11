package sample.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import static sample.Controllers.ErrorCode.*;
import static sample.Controllers.ErrorCode.NAME_MISSING;

enum ErrorCode {
    NAMES_OK, NAME_MISSING, NAME_TOO_LONG, NAMES_NOT_PAIRWISE_DISTINCT
}

public class EnterNameController {

    public static String errorMessage (ErrorCode e) {
        if (e == NAME_MISSING)
            return "Please enter all the names.";
        if (e == NAME_TOO_LONG)
            return  "The names cannot be longer than " + GlobalVar.MAX_NAME_LENGTH + " characters.";
        if (e == NAMES_NOT_PAIRWISE_DISTINCT)
            return "At least two of the names are the same.";
        return "Everything seems to be fine. This shouldn't have happened.";
    }

    public static ErrorCode validate (String ... names) {
        //name missing check, name too long check
        for (String name : names) {
            if (name.equals("")) return NAME_MISSING;
            if (name.length() > GlobalVar.MAX_NAME_LENGTH) return ErrorCode.NAME_TOO_LONG;
        }
        //pairwise distinct check
        for (int i = 0; i < names.length - 1; ++i) {
            for (int j = i + 1; j < names.length; ++j) {
                if (names[i].equals(names[j])) return ErrorCode.NAMES_NOT_PAIRWISE_DISTINCT;
            }
        }

        return ErrorCode.NAMES_OK;
    }

    @FXML
    Stage stage;
    @FXML
    Parent root;
    @FXML
    TextField blueLeaderField;
    @FXML
    public TextField blueOperativeField;
    @FXML
    public TextField redLeaderField;
    @FXML
    public TextField redOperativeField;

    @FXML
    public void toGame(ActionEvent event) throws IOException {
        GlobalVar.blueLeaderName = blueLeaderField.getText();
        GlobalVar.blueOperativeName = blueOperativeField.getText();
        GlobalVar.redLeaderName = redLeaderField.getText();
        GlobalVar.redOperativeName = redOperativeField.getText();
        ErrorCode e = validate(GlobalVar.blueLeaderName, GlobalVar.blueOperativeName, GlobalVar.redLeaderName, GlobalVar.redOperativeName);
        if (e != ErrorCode.NAMES_OK) {
            GlobalVar.currentError = errorMessage(e);
            Stage alertBox = new Stage();
            alertBox.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
            root = loader.load();
            alertBox.setScene(new Scene(root));
            alertBox.setTitle("An error has occurred");
            alertBox.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/NeutralScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    @FXML
    protected void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/GameSettings.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}