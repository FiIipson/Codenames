package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.min;

enum HintErrorCode {
    MORE_THAN_ONE_WORD, EMPTY_HINT, NOT_A_WORD, NOT_A_NUMBER, NOT_A_VALID_NUMBER, WORD_FROM_THE_BOARD, ALL_GOOD
}

public class HintScreenController {
    public HintErrorCode validate(String word, String number) {
        if (word.contains(" ")) return HintErrorCode.MORE_THAN_ONE_WORD;
        if (word.equals("")) return HintErrorCode.EMPTY_HINT;
        if (!word.matches("^([a-zA-Z])*$")) return HintErrorCode.NOT_A_WORD;
        if (!number.matches("^[0-9]*$")) return HintErrorCode.NOT_A_NUMBER;
        if (Integer.parseInt(number) < 0) return HintErrorCode.NOT_A_NUMBER;
        else {
            if (GlobalVar.red && Integer.parseInt(number) > GlobalVar.redLeft) return HintErrorCode.NOT_A_VALID_NUMBER;
            if (!GlobalVar.red && Integer.parseInt(number) > GlobalVar.blueLeft) return HintErrorCode.NOT_A_VALID_NUMBER;
        }
        for (GlobalVar.Word w : GlobalVar.word) {
            if (word.equalsIgnoreCase(w.getText()) &&
                    (w.getType() == GlobalVar.WordType.RED || w.getType() == GlobalVar.WordType.BLUE ||
                            w.getType() == GlobalVar.WordType.BOMB || w.getType() == GlobalVar.WordType.NEUTRAL)) return HintErrorCode.WORD_FROM_THE_BOARD;
        }
        return HintErrorCode.ALL_GOOD;
    }

    public String hintErrorMessage(HintErrorCode err) {
        if (err == HintErrorCode.MORE_THAN_ONE_WORD) return "Your hint can only be one word.";
        if (err == HintErrorCode.EMPTY_HINT) return "Your must give a hint.";
        if (err == HintErrorCode.NOT_A_WORD) return "Your hint must be a word.";
        if (err == HintErrorCode.NOT_A_NUMBER) return "Please enter a valid number.";
        if (err == HintErrorCode.NOT_A_VALID_NUMBER) return "The number is too big.";
        if (err == HintErrorCode.WORD_FROM_THE_BOARD) return "You cannot use one of the words from the board.";
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
    Text blueScore;
    @FXML
    Text redScore;
    @FXML
    public Button aa;
    @FXML
    public Button ab;
    @FXML
    public Button ac;
    @FXML
    public Button ad;
    @FXML
    public Button ae;
    @FXML
    public Button ba;
    @FXML
    public Button bb;
    @FXML
    public Button bc;
    @FXML
    public Button bd;
    @FXML
    public Button be;
    @FXML
    public Button ca;
    @FXML
    public Button cb;
    @FXML
    public Button cc;
    @FXML
    public Button cd;
    @FXML
    public Button ce;
    @FXML
    public Button da;
    @FXML
    public Button db;
    @FXML
    public Button dc;
    @FXML
    public Button dd;
    @FXML
    public Button de;
    @FXML
    public Button ea;
    @FXML
    public Button eb;
    @FXML
    public Button ec;
    @FXML
    public Button ed;
    @FXML
    public Button ee;

    @FXML
    public void initialize() {
        int time_limit = GlobalVar.timeLimit;
        int seconds = GlobalVar.seconds;
        if (time_limit == 0 || time_limit == 210) time.setText("∞");
        else time.setText(time_limit / 60 + ":" + seconds + "0");
        redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
        blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));

        Button [] button = new Button[] {aa, ab, ac, ad, ae, ba, bb, bc, bd, be, ca, cb, cc, cd, ce, da, db, dc, dd, de, ea, eb, ec, ed, ee};
        int count = 0;
        for (Button b : button) {
            b.setText(GlobalVar.word[count].getText());
            // YOU MAY WANNA MAKE THESE COLORS PRETTIER
            if (GlobalVar.word[count].getType() == GlobalVar.WordType.BLUE) b.setStyle("-fx-background-color: #00BFFF;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else if (GlobalVar.word[count].getType() == GlobalVar.WordType.RED) b.setStyle("-fx-background-color: red;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else if (GlobalVar.word[count].getType() == GlobalVar.WordType.BOMB) b.setStyle("-fx-text-fill: #FFFFF0;" +
              "-fx-background-color: black;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_RED || GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BLUE ||
                    GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_NEUTRAL || GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BOMB) b.setStyle("-fx-background-color: #00FF7F;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else b.setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px;" +
              "-fx-background-color: #F5F5F5;");
            count++;
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
