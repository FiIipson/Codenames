package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

enum HintErrorCode {
    MORE_THAN_ONE_WORD, EMPTY_HINT, NOT_A_WORD, NOT_A_NUMBER, NOT_A_VALID_NUMBER, ALL_GOOD
}

public class HintScreenController {
    public HintErrorCode validate(String word, String number) {
        if (word.contains(" ")) return HintErrorCode.MORE_THAN_ONE_WORD;
        if (word.equals("")) return HintErrorCode.EMPTY_HINT;
        if (!word.matches("[A-z]*")) return HintErrorCode.NOT_A_WORD;
        if (!number.matches("[0-9]*")) return HintErrorCode.NOT_A_NUMBER;
        if (Integer.parseInt(number) < 0 || Integer.parseInt(number) >= 9) return HintErrorCode.NOT_A_VALID_NUMBER;
        return HintErrorCode.ALL_GOOD;
    }

    public String hintErrorMessage(HintErrorCode err) {
        if (err == HintErrorCode.MORE_THAN_ONE_WORD) return "Your hint can only be one word.";
        if (err == HintErrorCode.EMPTY_HINT) return "Your must give a hint.";
        if (err == HintErrorCode.NOT_A_WORD) return "Your hint must be a word.";
        if (err == HintErrorCode.NOT_A_NUMBER) return "Please enter a valid number.";
        if (err == HintErrorCode.NOT_A_VALID_NUMBER) return "The number is too big.";
        return "Everything seems to be fine. This shouldn't have happened.";
    }

    public javafx.scene.control.TextField HintNumberLeader;
    public TextField HintWordLeader;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Text time;

    @FXML
    public void initialize() {
        int time_limit = GlobalVar.time_limit;
        int seconds = GlobalVar.seconds;
        if (time_limit == 0 || time_limit == 210) {
            time.setText("∞");
        }
        else {
            time.setText(time_limit / 60 + ":" + seconds + "0");
        }
    }

    @FXML
    public void toLoadingScreen(ActionEvent e) throws IOException {
        HintErrorCode err = validate(HintWordLeader.getText(), HintNumberLeader.getText());
        if (err == HintErrorCode.ALL_GOOD) {
            GlobalVar.hintString = HintWordLeader.getText();
            GlobalVar.hintNumber = Integer.parseInt(HintNumberLeader.getText());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/ChangeScreen.fxml")));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            GlobalVar.currentError = hintErrorMessage(err);
            Stage alertBox = new Stage();
            alertBox.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
            root = loader.load();
            alertBox.setScene(new Scene(root));
            alertBox.setTitle("An error has occurred");
            alertBox.showAndWait();
        }
    }
}
