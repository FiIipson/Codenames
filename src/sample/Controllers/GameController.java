package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.min;

public class GameController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Text time;
    @FXML
    Text hintWord;
    @FXML
    Text hintNumber;
    @FXML
    Text redScore;
    @FXML
    Text blueScore;
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

    boolean didTheyTry;
    boolean hintNumberZero;
    public static int _timeLimit = GlobalVar.timeLimit;
    static Timer timer;

    @FXML
    public void initialize() throws IOException {
        didTheyTry = false;
        hintWord.setText(GlobalVar.hintString);
        hintNumber.setText(String.valueOf(GlobalVar.hintNumber));
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
            if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BLUE) b.setStyle("-fx-background-color: #00BFFF;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_RED) b.setStyle("-fx-background-color: red;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BOMB) b.setStyle("-fx-text-fill: #FFFFF0;" +
              "-fx-background-color: black;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
            else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_NEUTRAL) b.setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px;" +
              "-fx-background-color: #00FF7F;");
            else b.setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px;" +
              "-fx-background-color: #F5F5F5;");
            count++;
        }
        hintNumberZero = GlobalVar.hintNumber == 0;
        if (hintNumberZero && !GlobalVar.hintString.equals("No hint")) {
            showAlert("Your leader gave you a hint with number 0. According to the rules" +
                    " you are allowed to guess as many words as you want (at least one).");
        }
        if (GlobalVar.hintString.equals("No hint")) {
            showAlert("Your leader gave you no hint. You're on your own now ;(");
        }

        timer = new Timer();
        _timeLimit = GlobalVar.timeLimit;
        TimerTask task = new TimerTask() {
            final boolean infinity = _timeLimit == 0 || _timeLimit == 210;
            @Override
            public void run() {
                if (_timeLimit - (60 * (_timeLimit / 60)) < 10) {
                    time.setText(_timeLimit / 60 + ":0" + (_timeLimit - (60 * (_timeLimit / 60))));
                } else {
                    time.setText(_timeLimit / 60 + ":" + (_timeLimit - (60 * (_timeLimit / 60))));
                }
                if (infinity) {
                    time.setText("∞");
                    timer.cancel();
                    timer.purge();
                } else {
                    _timeLimit--;
                }
                if (_timeLimit < 0) {
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    @FXML
    public void toLoadingScreen(ActionEvent e) throws IOException {
        if (!didTheyTry) {
            showAlert("You must try to guess at least one word.");
            return;
        }
        timer.cancel();
        timer.purge();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/ChangeScreen.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void clickedButton(ActionEvent e, int i) throws IOException {
        if (_timeLimit < 0) {
            showAlert("Your time's up!");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/ChangeScreen.fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            return;
        }
        Button [] button = new Button[] {aa, ab, ac, ad, ae, ba, bb, bc, bd, be, ca, cb, cc, cd, ce, da, db, dc, dd, de, ea, eb, ec, ed, ee};
        didTheyTry = true;
        if (GlobalVar.word[i].getType() == GlobalVar.WordType.RED) {
            button[i].setStyle("-fx-background-color: red;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px");
            GlobalVar.redLeft--;
            if (GlobalVar.redLeft == 0) {
                timer.cancel();
                timer.purge();

                GlobalVar.result_ = GlobalVar.Result.RED;
                showAlert("Red wins!");
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/EndGameScreen.fxml")));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                return;
            }
            redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
            blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_RED);
            if (GlobalVar.red) {
                showAlert("Oops... You clicked a red word. It's their turn now.");
                toLoadingScreen(e);
            }
            else {
                if (!hintNumberZero) GlobalVar.hintNumber--;
                if (GlobalVar.hintNumber >= 0) hintNumber.setText(String.valueOf(GlobalVar.hintNumber));
                endOfWords(e);
            }
        }
        else if (GlobalVar.word[i].getType() == GlobalVar.WordType.BLUE) {
            button[i].setStyle("-fx-background-color: #00BFFF;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px");
            GlobalVar.blueLeft--;
            redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
            blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_BLUE);
            if (GlobalVar.blueLeft == 0) {
                timer.cancel();
                timer.purge();

                GlobalVar.result_ = GlobalVar.Result.BLUE;
                showAlert("Blue wins!");
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/EndGameScreen.fxml")));
                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                return;
            }
            if (!GlobalVar.red) {
                showAlert("Oops... You clicked a blue word. It's their turn now.");
                toLoadingScreen(e);
            }
            else {
                if (!hintNumberZero) GlobalVar.hintNumber--;
                if (GlobalVar.hintNumber >= 0) hintNumber.setText(String.valueOf(GlobalVar.hintNumber));
                endOfWords(e);
            }
        }
        else if (GlobalVar.word[i].getType() == GlobalVar.WordType.BOMB) {
            timer.cancel();
            timer.purge();

            GlobalVar.result_ = GlobalVar.red ? GlobalVar.Result.RED_BOMB : GlobalVar.Result.BLUE_BOM;
            button[i].setStyle("-fx-text-fill: #FFFFF0;" +
              "-fx-background-color: black;" +
              "-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px");
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_BOMB);
            showAlert("Uh-oh... That was a bomb. You lose.");
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/EndGameScreen.fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else if (GlobalVar.word[i].getType() == GlobalVar.WordType.NEUTRAL) {
            button[i].setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px;" +
              "-fx-background-color: #00FF7F");
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_NEUTRAL);
            showAlert("You clicked a neutral word. It's your opponent's turn.");
            toLoadingScreen(e);
        }
    }

    void endOfWords(ActionEvent e) throws IOException {
        if (GlobalVar.hintNumber == 0) {
            GlobalVar.currentError = "You can try to guess one more word, but you don't have to.";
            Stage alertBox = new Stage();
            alertBox.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
            root = loader.load();
            alertBox.setScene(new Scene(root));
            alertBox.setTitle("Info");
            alertBox.showAndWait();
        }
        if (GlobalVar.hintNumber == -1) {
            GlobalVar.currentError = "Congratulations! You guessed it all.";
            Stage alertBox = new Stage();
            alertBox.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
            root = loader.load();
            alertBox.setScene(new Scene(root));
            alertBox.setTitle("");
            alertBox.showAndWait();
            toLoadingScreen(e);
        }
    }

    void showAlert(String s) throws IOException {
        GlobalVar.currentError = s;
        Stage alertBox = new Stage();
        alertBox.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
        root = loader.load();
        alertBox.setScene(new Scene(root));
        alertBox.setTitle("Info");
        alertBox.showAndWait();
    }

    @FXML
    public void clickedAa(ActionEvent e) throws IOException { clickedButton(e, 0); }
    @FXML
    public void clickedAb(ActionEvent e) throws IOException { clickedButton(e, 1); }
    @FXML
    public void clickedAc(ActionEvent e) throws IOException { clickedButton(e, 2); }
    @FXML
    public void clickedAd(ActionEvent e) throws IOException { clickedButton(e, 3); }
    @FXML
    public void clickedAe(ActionEvent e) throws IOException { clickedButton(e, 4); }
    @FXML
    public void clickedBa(ActionEvent e) throws IOException { clickedButton(e, 5); }
    @FXML
    public void clickedBb(ActionEvent e) throws IOException { clickedButton(e, 6); }
    @FXML
    public void clickedBc(ActionEvent e) throws IOException { clickedButton(e, 7); }
    @FXML
    public void clickedBd(ActionEvent e) throws IOException { clickedButton(e, 8); }
    @FXML
    public void clickedBe(ActionEvent e) throws IOException { clickedButton(e, 9); }
    @FXML
    public void clickedCa(ActionEvent e) throws IOException { clickedButton(e, 10); }
    @FXML
    public void clickedCb(ActionEvent e) throws IOException { clickedButton(e, 11); }
    @FXML
    public void clickedCc(ActionEvent e) throws IOException { clickedButton(e, 12); }
    @FXML
    public void clickedCd(ActionEvent e) throws IOException { clickedButton(e, 13); }
    @FXML
    public void clickedCe(ActionEvent e) throws IOException { clickedButton(e, 14); }
    @FXML
    public void clickedDa(ActionEvent e) throws IOException { clickedButton(e, 15); }
    @FXML
    public void clickedDb(ActionEvent e) throws IOException { clickedButton(e, 16); }
    @FXML
    public void clickedDc(ActionEvent e) throws IOException { clickedButton(e, 17); }
    @FXML
    public void clickedDd(ActionEvent e) throws IOException { clickedButton(e, 18); }
    @FXML
    public void clickedDe(ActionEvent e) throws IOException { clickedButton(e, 19); }
    @FXML
    public void clickedEa(ActionEvent e) throws IOException { clickedButton(e, 20); }
    @FXML
    public void clickedEb(ActionEvent e) throws IOException { clickedButton(e, 21); }
    @FXML
    public void clickedEc(ActionEvent e) throws IOException { clickedButton(e, 22); }
    @FXML
    public void clickedEd(ActionEvent e) throws IOException { clickedButton(e, 23); }
    @FXML
    public void clickedEe(ActionEvent e) throws IOException { clickedButton(e, 24); }


}