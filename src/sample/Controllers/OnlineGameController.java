package sample.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Server.ClientSideConnection;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;

import static java.lang.Math.min;
import static sample.Controllers.GlobalVar.csc;

public class OnlineGameController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    public Button greenButton;
    @FXML
    public Button redButton;
    @FXML
    public Text _hintWord_;
    @FXML
    public Text _hintNumber_;
    @FXML
    public Text _yourHint_;
    @FXML
    public Text _yourHintsLeft_;
    @FXML
    public TextField _hintWordField_;
    @FXML
    public TextField _hintNumberField_;
    @FXML
    public Text _yourHintProp_;
    @FXML
    public Text _yourHintNumberProp_;
    @FXML
    Text redScore;
    @FXML
    Text blueScore;
    @FXML
    TextArea _gameHistory_;
    @FXML
    Text _yourRole_;
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
    public Board board;

    public void startReceivingBoards(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        System.out.println("Start of receiving boards");
                        board = csc.receiveBoard();
                        if(board==null){
                            board= GlobalVar.receivedBoard;
                            csc.socket.close();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // END GAME
                                }
                            });
                            break;
                        }
                    } catch (Exception e) {
                        try {
                            csc.socket.close();
                        } catch (IOException ioException) {
                            //e.printStackTrace();
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
//                                // END GAME
                            }
                        });
                        break;
                    }
                    GlobalVar.receivedBoard = board;
                    updateBoard();
                    if (GlobalVar.receivedBoard.endGame)
                        break;
                }
//                Thread.currentThread().interrupt();
            }
        });
        t.start();
    }

    @FXML
    public void initialize() throws IOException {
        GlobalVar.word = GlobalVar.receivedBoard.words;
        System.out.println(GlobalVar.yourRole);
        didTheyTry = false;
        if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_LEADER) || GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_OPERATIVE)) {
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_LEADER)) {
               _yourRole_.setText("You are a blue leader");
            } else {
                _yourRole_.setText("You are a blue operative");
            }
            _yourRole_.setStyle("-fx-fill: #0227f5;");
        } else {
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.RED_LEADER)) {
                _yourRole_.setText("You are a red leader");
            } else {
                _yourRole_.setText("You are a red operative");
            }
            _yourRole_.setStyle("-fx-fill: #ff0000;");
        }
        redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
        blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));
        updateBoard();
        startReceivingBoards();
    }

    @FXML
    public void toLoadingScreen(ActionEvent e) throws IOException {
        System.out.println("[FUNCTION] toLoadingScreen");
        if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.RED_OPERATIVE) || GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_OPERATIVE)) {
            if (!didTheyTry) {
                showAlert("You must try to guess at least one word.");
                return;
            }
            GlobalVar.receivedBoard.hint.first_hint = false;
            endOfRound(e);
        } else {
            HintErrorCode err = validate(_hintWordField_.getText(), _hintNumberField_.getText());
            if (err == HintErrorCode.ALL_GOOD) {
                GlobalVar.hintString = _hintWordField_.getText();
                GlobalVar.hintNumber = Integer.parseInt(_hintNumberField_.getText());
                GlobalVar.receivedBoard.hint.first_hint = true;
                GlobalVar.receivedBoard.hint.text = GlobalVar.hintString;
                GlobalVar.receivedBoard.hint.number = GlobalVar.hintNumber;
                GlobalVar.receivedBoard.hintingPlayer = GlobalVar.your_name;
                GlobalVar.receivedBoard.hintingPlayerRole = GlobalVar.yourRole;
                endOfRound(e);
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

    public void clickedButton(ActionEvent e, int i) throws IOException {
        System.out.println("[FUNCTION] clickedButton");
        if (!yourTurn() || GlobalVar.yourRole.equals(GlobalVar.PlayerRole.RED_LEADER)
                || GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_LEADER))
            return;
        Button [] button = new Button[] {aa, ab, ac, ad, ae, ba, bb, bc, bd, be, ca, cb, cc, cd, ce, da, db, dc, dd, de, ea, eb, ec, ed, ee};
        didTheyTry = true;
        if (GlobalVar.word[i].getType() == GlobalVar.WordType.RED) {
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_RED);
            button[i].setStyle("-fx-background-color: red;" +
                    "-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px");
            GlobalVar.redLeft--;
            if (GlobalVar.redLeft == 0) {
                GlobalVar.receivedBoard.endGame = true;
                GlobalVar.receivedBoard.gameResult = GlobalVar.Result.RED;
                endOfGame(e);
                return;
            }
            redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
            blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_RED);
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_OPERATIVE)) {
                showAlert("Oops... You clicked a red word. It's their turn now.");
                GlobalVar.receivedBoard.hint.first_hint = false;
                endOfRound(e);
                return;
            }
            else {
                GlobalVar.hintNumber--;
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
                else if (GlobalVar.hintNumber == -1) {
                    GlobalVar.currentError = "Congratulations! You guessed it all.";
                    Stage alertBox = new Stage();
                    alertBox.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
                    root = loader.load();
                    alertBox.setScene(new Scene(root));
                    alertBox.setTitle("");
                    alertBox.showAndWait();
                    GlobalVar.receivedBoard.hint.first_hint = false;
                    endOfRound(e);
                    return;
                }
                if (GlobalVar.hintNumber >= 0) _hintNumber_.setText(String.valueOf(GlobalVar.hintNumber)) ;
                else _hintNumber_.setText("0");
            }
        }
        else if (GlobalVar.word[i].getType() == GlobalVar.WordType.BLUE) {
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_BLUE);
            button[i].setStyle("-fx-background-color: #00BFFF;" +
                    "-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px");
            GlobalVar.blueLeft--;
            if (GlobalVar.blueLeft == 0) {
                GlobalVar.receivedBoard.endGame = true;
                GlobalVar.receivedBoard.gameResult = GlobalVar.Result.BLUE;
                endOfGame(e);
                return;
            }
            redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
            blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.RED_OPERATIVE)) {
                showAlert("Oops... You clicked a blue word. It's their turn now.");
                GlobalVar.receivedBoard.hint.first_hint = false;
                endOfRound(e);
                return;
            }
            else {
                GlobalVar.hintNumber--;
                if (GlobalVar.hintNumber== 0) {
                    GlobalVar.currentError = "You can try to guess one more word, but you don't have to.";
                    Stage alertBox = new Stage();
                    alertBox.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
                    root = loader.load();
                    alertBox.setScene(new Scene(root));
                    alertBox.setTitle("Info");
                    alertBox.showAndWait();
                }
                else if (GlobalVar.hintNumber== -1) {
                    GlobalVar.currentError = "Congratulations! You guessed it all.";
                    Stage alertBox = new Stage();
                    alertBox.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
                    root = loader.load();
                    alertBox.setScene(new Scene(root));
                    alertBox.setTitle("");
                    alertBox.showAndWait();
                    GlobalVar.receivedBoard.hint.first_hint = false;
                    endOfRound(e);
                    return;
                }
                if (GlobalVar.hintNumber >= 0) _hintNumber_.setText(String.valueOf(GlobalVar.hintNumber)) ;
                else _hintNumber_.setText("0");
            }
        }
        else if (GlobalVar.word[i].getType() == GlobalVar.WordType.BOMB) {
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_BOMB);
            GlobalVar.result_ = GlobalVar.red ? GlobalVar.Result.RED_BOMB : GlobalVar.Result.BLUE_BOMB;
            button[i].setStyle("-fx-text-fill: #FFFFF0;" +
                    "-fx-background-color: black;" +
                    "-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px");
            showAlert("Uh-oh... That was a bomb. You lose.");
            GlobalVar.receivedBoard.endGame = true;
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_OPERATIVE)) {
                GlobalVar.receivedBoard.gameResult = GlobalVar.Result.BLUE_BOMB;
            } else {
                GlobalVar.receivedBoard.gameResult = GlobalVar.Result.RED_BOMB;
            }
            endOfGame(e);
            return;
        }
        else if (GlobalVar.word[i].getType() == GlobalVar.WordType.NEUTRAL) {
            GlobalVar.word[i].setType(GlobalVar.WordType.GUESSED_NEUTRAL);
            button[i].setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[i].getText().length()) + "px;" +
                    "-fx-background-color: #00FF7F");
            showAlert("You clicked a neutral word. It's your opponent's turn.");
            GlobalVar.receivedBoard.hint.first_hint = false;
            endOfRound(e);
            return;
        }
        GlobalVar.receivedBoard.words = GlobalVar.word;
        GlobalVar.receivedBoard.hint.number = GlobalVar.hintNumber;
        GlobalVar.receivedBoard.hint.text = GlobalVar.hintString;
        GlobalVar.receivedBoard.hint.first_hint = false;
        ClientSideConnection.sendBoard();
    }

    void endOfRound(ActionEvent e) throws IOException {
        if (GlobalVar.receivedBoard.turn.equals(GlobalVar.PlayerRole.BLUE_LEADER)) {
            GlobalVar.receivedBoard.turn = GlobalVar.PlayerRole.BLUE_OPERATIVE;
        } else if (GlobalVar.receivedBoard.turn.equals(GlobalVar.PlayerRole.BLUE_OPERATIVE)) {
            GlobalVar.receivedBoard.turn = GlobalVar.PlayerRole.RED_LEADER;
        } else if (GlobalVar.receivedBoard.turn.equals(GlobalVar.PlayerRole.RED_LEADER)) {
            GlobalVar.receivedBoard.turn = GlobalVar.PlayerRole.RED_OPERATIVE;
        } else {
            GlobalVar.receivedBoard.turn = GlobalVar.PlayerRole.BLUE_LEADER;
        }
        GlobalVar.receivedBoard.words = GlobalVar.word;
        ClientSideConnection.sendBoard();
    }

    void endOfGame(ActionEvent e) {
        GlobalVar.receivedBoard.words = GlobalVar.word;
        GlobalVar.receivedBoard.hint.number = GlobalVar.hintNumber;
        GlobalVar.receivedBoard.hint.text = GlobalVar.hintString;
        GlobalVar.receivedBoard.hint.first_hint = false;
        ClientSideConnection.sendBoard();
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

    public void updateBoard() {
        if (GlobalVar.receivedBoard.endGame) {
//            try {
//                csc.in.close();
//                ClientSideConnection.out.close();
//                csc.socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //GlobalVar.mainStage.close();
                    try {
//                        csc.in.close();
//                        ClientSideConnection.out.close();
//                        csc.socket.close();
                        MultiEndGameController.run();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }
        GlobalVar.word = GlobalVar.receivedBoard.words;
        GlobalVar.hintNumber = GlobalVar.receivedBoard.hint.number;
        GlobalVar.hintString = GlobalVar.receivedBoard.hint.text;
        if (GlobalVar.receivedBoard.hint.first_hint) {
            String temp;
            if (GlobalVar.receivedBoard.hintingPlayerRole.equals(GlobalVar.PlayerRole.BLUE_LEADER)) {
                temp = "[BLUE] ";
            } else {
                temp = "[RED] ";
            }
            GlobalVar.gameHistory = temp + GlobalVar.receivedBoard.hintingPlayer + " hinted "
                    + GlobalVar.receivedBoard.hint.text + " " + GlobalVar.receivedBoard.hint.number + ".\n" + GlobalVar.gameHistory;
            //GlobalVar.gameHistory += "[BLUE/RED] [NAME] hinted [WORD] [NUMBER].";
        }
        int countBlueLeft = 0;
        int countRedLeft = 0;
        if (GlobalVar.yourRole == GlobalVar.PlayerRole.BLUE_LEADER || GlobalVar.yourRole == GlobalVar.PlayerRole.RED_LEADER) {
            Button[] button = new Button[]{aa, ab, ac, ad, ae, ba, bb, bc, bd, be, ca, cb, cc, cd, ce, da, db, dc, dd, de, ea, eb, ec, ed, ee};
            int count = 0;
            for (Button b : button) {
                b.setText(GlobalVar.word[count].getText());
                // YOU MAY WANNA MAKE THESE COLORS PRETTIER
                if (GlobalVar.word[count].getType() == GlobalVar.WordType.BLUE) {
                    b.setStyle("-fx-background-color: #00BFFF;" +
                            "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                    countBlueLeft++;
                }
                else if (GlobalVar.word[count].getType() == GlobalVar.WordType.RED) {
                    b.setStyle("-fx-background-color: red;" +
                            "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                    countRedLeft++;
                }
                else if (GlobalVar.word[count].getType() == GlobalVar.WordType.BOMB)
                    b.setStyle("-fx-text-fill: #FFFFF0;" +
                            "-fx-background-color: black;" +
                            "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_RED || GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BLUE ||
                        GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_NEUTRAL || GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BOMB)
                    b.setStyle("-fx-background-color: #00FF7F;" +
                            "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                else b.setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px;" +
                            "-fx-background-color: #F5F5F5;");
                count++;
            }
        } else {
            Button [] button = new Button[] {aa, ab, ac, ad, ae, ba, bb, bc, bd, be, ca, cb, cc, cd, ce, da, db, dc, dd, de, ea, eb, ec, ed, ee};
            int count = 0;
            for (Button b : button) {
                b.setText(GlobalVar.word[count].getText());
                // YOU MAY WANNA MAKE THESE COLORS PRETTIER
                if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BLUE) {
                    b.setStyle("-fx-background-color: #00BFFF;" +
                            "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                    countBlueLeft++;
                }
                else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_RED) {
                    b.setStyle("-fx-background-color: red;" +
                            "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                    countRedLeft++;
                }
                else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_BOMB) b.setStyle("-fx-text-fill: #FFFFF0;" +
                        "-fx-background-color: black;" +
                        "-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px");
                else if (GlobalVar.word[count].getType() == GlobalVar.WordType.GUESSED_NEUTRAL) b.setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px;" +
                        "-fx-background-color: #00FF7F;");
                else b.setStyle("-fx-font-size: " + min(23, 160 / GlobalVar.word[count].getText().length()) + "px;" +
                            "-fx-background-color: #F5F5F5;");
                count++;
            }
            countBlueLeft = GlobalVar.blueTotal - countBlueLeft;
            countRedLeft = GlobalVar.redTotal - countRedLeft;
        }
        GlobalVar.redLeft = countRedLeft;
        GlobalVar.blueLeft = countBlueLeft;
        redScore.setText(String.valueOf(GlobalVar.redTotal - GlobalVar.redLeft));
        blueScore.setText(String.valueOf(GlobalVar.blueTotal - GlobalVar.blueLeft));
        _hintWord_.setVisible(true);
        _hintNumber_.setVisible(true);
        _yourHint_.setVisible(true);
        _yourHintsLeft_.setVisible(true);
        _hintWordField_.setVisible(true);
        _hintNumberField_.setVisible(true);
        _yourHintProp_.setVisible(true);
        _yourHintNumberProp_.setVisible(true);
        greenButton.setVisible(true);
        redButton.setVisible(true);
        if (GlobalVar.yourRole == GlobalVar.PlayerRole.BLUE_LEADER || GlobalVar.yourRole == GlobalVar.PlayerRole.RED_LEADER) {
            _hintWord_.setVisible(false);
            _hintNumber_.setVisible(false);
            _yourHint_.setVisible(false);
            _yourHintsLeft_.setVisible(false);
            redButton.setVisible(false);
        } else {
            _hintWordField_.setVisible(false);
            _hintNumberField_.setVisible(false);
            _yourHintProp_.setVisible(false);
            _yourHintNumberProp_.setVisible(false);
            greenButton.setVisible(false);
            if (yourTurn()) {
                _hintNumber_.setText(String.valueOf(GlobalVar.receivedBoard.hint.number));
                _hintWord_.setText(String.valueOf(GlobalVar.receivedBoard.hint.text));
            }
        }
        if (!yourTurn()) {
            _hintWord_.setVisible(false);
            _hintNumber_.setVisible(false);
            _yourHint_.setVisible(false);
            _yourHintsLeft_.setVisible(false);
            _hintWordField_.setVisible(false);
            _hintNumberField_.setVisible(false);
            _yourHintProp_.setVisible(false);
            _yourHintNumberProp_.setVisible(false);
            greenButton.setVisible(false);
            redButton.setVisible(false);
        }
        _gameHistory_.setText(GlobalVar.gameHistory);
    }

    enum HintErrorCode {
        MORE_THAN_ONE_WORD, EMPTY_HINT, NOT_A_WORD, NOT_A_NUMBER, NOT_A_VALID_NUMBER, WORD_FROM_THE_BOARD, ALL_GOOD, NO_TIME
    }

    public HintErrorCode validate(String word, String number) {
        if (_timeLimit < 0) return HintErrorCode.NO_TIME;
        if (word.contains(" ")) return HintErrorCode.MORE_THAN_ONE_WORD;
        if (word.equals("")) return HintErrorCode.EMPTY_HINT;
        if (!number.matches("^[0-9]*$")) return HintErrorCode.NOT_A_NUMBER;
        if (number.equals("")) return HintErrorCode.NOT_A_NUMBER;
        if (Integer.parseInt(number) < 0) return HintErrorCode.NOT_A_NUMBER;
        else {
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.RED_LEADER) && Integer.parseInt(number) > GlobalVar.redLeft) return HintErrorCode.NOT_A_VALID_NUMBER;
            if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_LEADER) && Integer.parseInt(number) > GlobalVar.blueLeft) return HintErrorCode.NOT_A_VALID_NUMBER;
        }
        for (GlobalVar.Word w : GlobalVar.word) {
            if (word.equalsIgnoreCase(w.getText()) &&
                    (w.getType() == GlobalVar.WordType.RED || w.getType() == GlobalVar.WordType.BLUE ||
                            w.getType() == GlobalVar.WordType.BOMB || w.getType() == GlobalVar.WordType.NEUTRAL)) return HintErrorCode.WORD_FROM_THE_BOARD;
        }
        if (!findWord(word)) return HintErrorCode.NOT_A_WORD;
        return HintErrorCode.ALL_GOOD;
    }

    boolean findWord(String word) {
        int L = 0;
        int P = GlobalVar.DICTIONARY_SIZE - 1;
        int M;
        while (L <= P) {
            M = (L + P)/2;
            if (GlobalVar.Dictionary[M].compareToIgnoreCase(word) < 0) L = M + 1;
            else if (GlobalVar.Dictionary[M].compareToIgnoreCase(word) > 0) P = M - 1;
            else return true;
        }
        return false;
    }

    public String hintErrorMessage(HintErrorCode err) {
        if (err == HintErrorCode.MORE_THAN_ONE_WORD) return "Your hint can only be one word.";
        if (err == HintErrorCode.EMPTY_HINT) return "Your must give a hint.";
        if (err == HintErrorCode.NOT_A_WORD) return "There's no such word.";
        if (err == HintErrorCode.NOT_A_NUMBER) return "Please enter a valid number.";
        if (err == HintErrorCode.NOT_A_VALID_NUMBER) return "The number is too big.";
        if (err == HintErrorCode.WORD_FROM_THE_BOARD) return "You cannot use one of the words from the board.";
        if (err == HintErrorCode.NO_TIME) return "Your time's up!";
        return "Everything seems to be fine. This shouldn't have happened.";
    }

    public boolean yourTurn() {
        return (GlobalVar.yourRole.equals(GlobalVar.receivedBoard.turn));
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